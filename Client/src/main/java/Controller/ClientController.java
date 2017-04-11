package Controller;

import Domain.CategorieVarsta;
import Domain.Inscriere;
import Domain.Proba;
import Domain.User;
import Exceptions.ServiceException;
import Service.IClient;
import Service.IServer;
import Utils.Observable;
import Utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 11-Apr-17.
 */
public class ClientController implements IClient , Observable {

        private IServer server;
    private User user;

    public ClientController(IServer server) {
        this.server = server;
        observerList=new ArrayList<Observer>();
    }

    public void login(String username, String password) throws ServiceException {
        User userL = new User(username, password);
        server.login(userL, this);
        user = userL;
    }

    public void logout() throws ServiceException{
        server.logout(user, this);
        user = null;
    }



    public List<CategorieVarsta> getAllCategorieVarstaForProba(Integer probaID) throws ServiceException {
        return server.getAllCategorieVarstaForProba(probaID);
    }

    public List<Proba> getAllProbe(){
        try {
            return server.getAllProbe();
        } catch (ServiceException e) {
            return new ArrayList<Proba>();
        }
    }

    public List<Inscriere> getInscriereByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException{
        return server.getInscriereByProbaCategorie(proba, categorieVarsta);
    }

    public Integer countInscrieriByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException {
        return server.countInscrieriByProbaCategorie(proba, categorieVarsta);
    }

    public void addInscriere(Proba proba, String participant_nume, Integer participant_varsta) throws Exception {
        server.addInscriere(proba, participant_nume, participant_varsta);
    }


    public User getCurrentUser(){
        return this.user;
    }




    @Override
    public void invalidate() throws ServiceException {
        notifyObservers();
    }


    List<Observer> observerList;

    @Override
    public void addObserver(Observer obs) {
        observerList.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observerList.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for(Observer obs:observerList){
            obs.update();
        }
    }
}
