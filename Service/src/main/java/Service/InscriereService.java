package Service;

import Domain.CategorieVarsta;
import Domain.Inscriere;
import Domain.Proba;
import Repository.CategoriiVarstaDBRepository;
import Repository.InscrieriDBRepository;
import Exceptions.ServiceException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Utilizator on 20-Mar-17.
 */
public class InscriereService implements IInscriereService{

    private InscrieriDBRepository inscrieriDBRepository;
    private CategoriiVarstaDBRepository categoriiVarstaDBRepository;

    public InscriereService(InscrieriDBRepository inscrieriDBRepository, CategoriiVarstaDBRepository categoriiVarstaDBRepository) {
        this.inscrieriDBRepository=inscrieriDBRepository;
        this.categoriiVarstaDBRepository =categoriiVarstaDBRepository;
    }

    public List<Inscriere> getAll() throws ServiceException {
        try {
            return inscrieriDBRepository.getAll();
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    public List<Inscriere> getByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException {
        try {
            return inscrieriDBRepository.getAll().stream().filter(inscriere -> inscriere.getProba_id().equals( proba.getProba_id()) && inscriere.getCategorie_varsta_id().equals( categorieVarsta.getCategorie_id())).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    public Integer countByProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) throws ServiceException {
        try {
            return Math.toIntExact(inscrieriDBRepository.getAll().stream().filter(inscriere -> inscriere.getProba_id().equals( proba.getProba_id()) && inscriere.getCategorie_varsta_id().equals( categorieVarsta.getCategorie_id())).count());
        } catch (SQLException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void add(Proba proba, String participant_nume, Integer participant_varsta) throws ServiceException {
        try {
            if (inscrieriDBRepository.getAll().stream().filter(inscriere -> inscriere.getParticipant_nume().equals(participant_nume) && inscriere.getProba_id().equals(proba.getProba_id())).count() >= 1)
                throw new ServiceException("Participantul " + participant_nume + " e deja inscris la " + proba.getNume());
            if (inscrieriDBRepository.getAll().stream().filter(inscriere -> inscriere.getParticipant_nume().equals(participant_nume)).count() >= 2)
                throw new ServiceException("S-a depasit numarul maxim de inscrieri pentru participantul " + participant_nume);

            Optional<CategorieVarsta> categorieVarstaOptional = categoriiVarstaDBRepository.getAll().stream().filter(categorieVarsta -> categorieVarsta.getProba_id().equals( proba.getProba_id()) && categorieVarsta.getMinim_varsta() <= participant_varsta && categorieVarsta.getMaxim_varsta() >= participant_varsta).findFirst();
            if (!categorieVarstaOptional.isPresent())
                throw new ServiceException("Nu exista categorie de varsta potrivita!");
            else {
                inscrieriDBRepository.add(new Inscriere(1, proba.getProba_id(), participant_varsta, participant_nume, categorieVarstaOptional.get().getCategorie_id()));
            }
        }
        catch (SQLException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
