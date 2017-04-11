package Service;

import Domain.CategorieVarsta;

import Exceptions.ServiceException;
import java.util.List;

/**
 * Created by Utilizator on 03-Apr-17.
 */
public interface ICategoriiVarstaService {


    /*
    get all age groups
     */
    public List<CategorieVarsta> getAll() throws ServiceException;


    /*
    get all age groups corresponding to challenge id

     */
    public List<CategorieVarsta> getAllByProbaID(Integer probaID) throws ServiceException;

}
