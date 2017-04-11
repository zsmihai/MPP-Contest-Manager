package Networking.dtos;

import java.io.Serializable;

/**
 * Created by Utilizator on 04-Apr-17.
 */
public class CategorieVarstaDTO implements Serializable{

    Integer categorie_id;
    Integer proba_id;
    Integer minim_varsta;
    Integer maxim_varsta;

    public CategorieVarstaDTO(Integer categorie_id, Integer proba_id, Integer minim_varsta, Integer maxim_varsta) {
        this.categorie_id = categorie_id;
        this.proba_id = proba_id;
        this.minim_varsta = minim_varsta;
        this.maxim_varsta = maxim_varsta;
    }

    public Integer getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(Integer categorie_id) {
        this.categorie_id = categorie_id;
    }

    public Integer getProba_id() {
        return proba_id;
    }

    public void setProba_id(Integer proba_id) {
        this.proba_id = proba_id;
    }

    public Integer getMinim_varsta() {
        return minim_varsta;
    }

    public void setMinim_varsta(Integer minim_varsta) {
        this.minim_varsta = minim_varsta;
    }

    public Integer getMaxim_varsta() {
        return maxim_varsta;
    }

    public void setMaxim_varsta(Integer maxim_varsta) {
        this.maxim_varsta = maxim_varsta;
    }
}
