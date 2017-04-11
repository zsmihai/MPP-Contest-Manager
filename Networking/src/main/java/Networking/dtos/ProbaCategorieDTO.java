package Networking.dtos;


import java.io.Serializable;

/**
 * Created by Utilizator on 04-Apr-17.
 */
public class ProbaCategorieDTO implements Serializable {

    private ProbaDTO probaDTO;
    private CategorieVarstaDTO categorieVarstaDTO;


    public ProbaCategorieDTO(ProbaDTO probaDTO, CategorieVarstaDTO categorieVarstaDTO) {
        this.probaDTO = probaDTO;
        this.categorieVarstaDTO = categorieVarstaDTO;
    }

    public ProbaDTO getProbaDTO() {
        return probaDTO;
    }

    public void setProbaDTO(ProbaDTO probaDTO) {
        this.probaDTO = probaDTO;
    }

    public CategorieVarstaDTO getCategorieVarstaDTO() {
        return categorieVarstaDTO;
    }

    public void setCategorieVarstaDTO(CategorieVarstaDTO categorieVarstaDTO) {
        this.categorieVarstaDTO = categorieVarstaDTO;
    }
}
