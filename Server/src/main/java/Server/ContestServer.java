package Server;

import Domain.CategorieVarsta;
import Domain.Inscriere;
import Domain.Proba;
import Domain.User;
import Service.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Exceptions.ServiceException;
/**
 * Created by Utilizator on 04-Apr-17.
 */
public class ContestServer implements IServer {

    private ICategoriiVarstaService categoriiVarstaService;
    private IInscriereService inscriereService;
    private IUsersService usersService;
    private IProbaService probaService;
    private Map<String, IClient> loggedClients;
    private int defaultThreadsNumber=5;


    public ContestServer(ICategoriiVarstaService categoriiVarstaService, IInscriereService inscriereService, IUsersService usersService, IProbaService probaService) {
        this.categoriiVarstaService = categoriiVarstaService;
        this.inscriereService = inscriereService;
        this.usersService = usersService;
        this.probaService = probaService;
        loggedClients = new ConcurrentHashMap<>();
    }


    @Override
    public synchronized List<CategorieVarsta> getAllCategorieVarstaForProba(Integer probaID) throws ServiceException {
        return categoriiVarstaService.getAllByProbaID(probaID);
    }

    @Override
    public synchronized boolean validUser(String username, String password) throws ServiceException {
        return usersService.find(username, password);
    }

    @Override
    public synchronized List<Proba> getAllProbe() {
        return probaService.getAll();
    }

    @Override
    public synchronized List<Inscriere> getAllInscrieri() throws ServiceException {
        return inscriereService.getAll();
    }

    @Override
    public synchronized List<Inscriere> getInscriereByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException {
        return inscriereService.getByProbaCategorie(proba, categorieVarsta);
    }

    @Override
    public synchronized Integer countInscrieriByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException {
        return inscriereService.countByProbaCategorie(proba, categorieVarsta);
    }

    @Override
    public synchronized void addInscriere(Proba proba, String participant_nume, Integer participant_varsta) throws Exception {
        inscriereService.add(proba, participant_nume, participant_varsta);
        notifyClients();
    }

    @Override
    public synchronized void login(User user, IClient client) throws ServiceException {
        User userR = usersService.getForNamePass(user.getUsername(), user.getPassword());

        //invalid user
        if (userR == null){
            throw new ServiceException("Invalid username/password");
        }

        //already logged in
        if (loggedClients.get(user.getUsername()) != null){
            throw new ServiceException("User is already logged in");
        }

        //saves the user
        loggedClients.put(user.getUsername(), client);
    }

    @Override
    public void logout(User user, IClient client) throws ServiceException {
        IClient Client = loggedClients.remove(user.getUsername());
        if (Client == null){
            throw new ServiceException("User " + user.getUsername() + " is not logged in");
        }
    }

    private synchronized void notifyClients() throws ServiceException {

        Iterable<String> loggedUsers = loggedClients.keySet();


        ExecutorService executorService = Executors.newFixedThreadPool(defaultThreadsNumber);

        //notifies all clients
        for (String username : loggedUsers) {
            executorService.execute(() -> {
                try {

                    IClient client = loggedClients.get(username);
                    client.invalidate();
                } catch (ServiceException e) {
                    System.err.println("Error notifying user");
                }
            });
        }
    }
}
