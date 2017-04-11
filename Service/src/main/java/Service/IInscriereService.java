package Service;

import Domain.CategorieVarsta;
import Domain.Inscriere;
import Domain.Proba;
import Exceptions.ServiceException;
import java.util.List;

/**
 * Created by Utilizator on 03-Apr-17.
 */
public interface IInscriereService {
    public List<Inscriere> getAll() throws ServiceException;

    public List<Inscriere> getByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException;

    public Integer countByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException;

    public void add(Proba proba, String participant_nume, Integer participant_varsta) throws Exception;
}
