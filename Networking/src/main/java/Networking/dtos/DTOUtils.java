package Networking.dtos;

import Domain.CategorieVarsta;
import Domain.Inscriere;
import Domain.Proba;
import Domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilizator on 04-Apr-17.
 */
public class DTOUtils {

    public static ProbaDTO getProbaDTOFromProba(Proba proba) {
        ProbaDTO probaDTO = new ProbaDTO(proba.getProba_id(), proba.getNume());
        return probaDTO;

    }

    public static List<ProbaDTO> getProbaDTOListFromProbaList(List<Proba> probaList) {
        List<ProbaDTO> probaDTOList = new ArrayList<>();
        for (Proba proba:probaList ) {
            probaDTOList.add(getProbaDTOFromProba(proba));
        }
        return probaDTOList;
    }

    public static List<Proba> getProbaListFromProbaListDTO(List<ProbaDTO> probaListDTO) {
        List<Proba> probaList = new ArrayList<>();

        for (ProbaDTO proba:probaListDTO ) { probaList.add(getProbaFromProbaDTO(proba));};

        return probaList;
    }


    public static Proba getProbaFromProbaDTO(ProbaDTO probaDTO) {
        return new Proba(probaDTO.getProba_id(), probaDTO.getNume());
    }

    public static CategorieVarsta getCategorieFromCategorieDTO(CategorieVarstaDTO categorieVarstaDTO) {

        return new CategorieVarsta(categorieVarstaDTO.getCategorie_id(), categorieVarstaDTO.getMinim_varsta(), categorieVarstaDTO.getMaxim_varsta(), categorieVarstaDTO.getProba_id());
    }

    public static InscriereDTO getIscriereDTOFromInscriere(Inscriere inscriere) {
        return new InscriereDTO(inscriere.getInscriere_id(), inscriere.getProba_id(), inscriere.getParticipant_varsta(), inscriere.getParticipant_nume(), inscriere.getCategorie_varsta_id());
    }


    public static List<InscriereDTO> getInscriereDTOListFromInscriereList(List<Inscriere> inscrieri) {
        List<InscriereDTO> inscriereDTOList = new ArrayList<>();
        for(Inscriere inscriere:inscrieri){
            inscriereDTOList.add(getIscriereDTOFromInscriere(inscriere));
        }
        return inscriereDTOList;
    }

    public static UserDTO getUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getPassword());
    }

    public static ProbaCategorieDTO getProbaCategorieDTOFromProbaCategorie(Proba proba, CategorieVarsta categorieVarsta) {
        return new ProbaCategorieDTO(getProbaDTOFromProba(proba), getCategorieVarstaDTOFromCategorieVarsta(categorieVarsta));

    }

    private static CategorieVarstaDTO getCategorieVarstaDTOFromCategorieVarsta(CategorieVarsta categorieVarsta) {
        return new CategorieVarstaDTO(categorieVarsta.getCategorie_id(), categorieVarsta.getProba_id(), categorieVarsta.getMinim_varsta(), categorieVarsta.getMaxim_varsta());

    }

    public static List<Inscriere> getInscriereListFromInscriereDTOList(List<InscriereDTO> inscriereDTOList) {
        List<Inscriere> inscriereList = new ArrayList<>();
        for(InscriereDTO inscriere : inscriereDTOList) {
            inscriereList.add(getIscriereFromInscriereDTO(inscriere));
        }
        return inscriereList;

    }

    private static Inscriere getIscriereFromInscriereDTO(InscriereDTO inscriere) {
        return new Inscriere(inscriere.getInscriere_id(), inscriere.getProba_id(), inscriere.getParticipant_varsta(), inscriere.getParticipant_nume(), inscriere.getCategorie_varsta_id());
    }

    public static List<CategorieVarstaDTO> getCategorieDTOListFromCategorieList(List<CategorieVarsta> categorieVarstaList) {
        List<CategorieVarstaDTO> categorieVarstaDTOList = new ArrayList<>();
        for(CategorieVarsta categorie: categorieVarstaList) {
            categorieVarstaDTOList.add(getCategorieVarstaDTOFromCategorieVarsta(categorie));
        }
        return categorieVarstaDTOList;
    }

    public static List<CategorieVarsta> getCategorieListFromCategorieDTOList(List<CategorieVarstaDTO> categorieVarstaDTOList) {
        List<CategorieVarsta> categorieVarstaList = new ArrayList<>();
        for(CategorieVarstaDTO categorie: categorieVarstaDTOList) {
            categorieVarstaList.add(getCategorieVarstaFromCategorieVarstaDTO(categorie));
        }
        return categorieVarstaList;
    }

    private static CategorieVarsta getCategorieVarstaFromCategorieVarstaDTO(CategorieVarstaDTO categorieVarsta) {
        return new CategorieVarsta(categorieVarsta.getCategorie_id(), categorieVarsta.getMinim_varsta(), categorieVarsta.getMaxim_varsta(), categorieVarsta.getProba_id());
    }

    private static InscriereProbaDTO  get(ProbaDTO proba, String part_nume, Integer part_varsta){
        return new InscriereProbaDTO(part_nume, part_varsta, proba);
    }

    public static User getUserFromUserDTO(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword());
    }
}
