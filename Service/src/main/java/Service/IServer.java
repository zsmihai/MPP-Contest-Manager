package Service;

import Domain.CategorieVarsta;
import Domain.Inscriere;
import Domain.Proba;
import Domain.User;
import Exceptions.ServiceException;

import java.util.List;

/**
 * Created by Utilizator on 03-Apr-17.
 */
public interface IServer {


    List<CategorieVarsta> getAllCategorieVarstaForProba(Integer probaID) throws ServiceException, ServiceException;

    boolean validUser(String username, String password) throws ServiceException;

    List<Proba> getAllProbe() throws ServiceException;
    public List<Inscriere> getAllInscrieri() throws ServiceException;
    public List<Inscriere> getInscriereByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException;
    public Integer countInscrieriByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException;
    public void addInscriere(Proba proba, String participant_nume, Integer participant_varsta) throws Exception;

    public void login(User user, IClient client) throws ServiceException;
    public void logout(User user, IClient client) throws ServiceException;

}
