package Service;

import Domain.CategorieVarsta;
import Repository.CategoriiVarstaDBRepository;
import Repository.IRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import Exceptions.ServiceException;
/**
 * Created by Utilizator on 20-Mar-17.
 */
public class CategoriiVarstaService implements ICategoriiVarstaService {

    private IRepository<CategorieVarsta, Integer> categoriiVarstaRepo;

    public CategoriiVarstaService(CategoriiVarstaDBRepository categoriiVarstaDBRepository) {
        this.categoriiVarstaRepo = categoriiVarstaDBRepository;
    }

    public List<CategorieVarsta> getAll() throws ServiceException {
        try {
            return categoriiVarstaRepo.getAll();
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<CategorieVarsta> getAllByProbaID(Integer probaID) throws ServiceException {
        try {
            return categoriiVarstaRepo.getAll().stream().filter(x->x.getProba_id().equals(probaID)).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }


}
