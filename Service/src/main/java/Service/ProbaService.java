package Service;

import Domain.Proba;
import Repository.ProbeDBRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 20-Mar-17.
 */
public class ProbaService implements IProbaService {
    private ProbeDBRepository probeDBRepository;


    public ProbaService(ProbeDBRepository probeDBRepository) {
        this.probeDBRepository = probeDBRepository;
    }

    public List<Proba> getAll(){
        try {
            return  probeDBRepository.getAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

}

