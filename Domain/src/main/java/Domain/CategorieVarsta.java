package Domain;

/**
 * Created by Utilizator on 12-Mar-17.
 */
public class CategorieVarsta implements HasID<Integer> {
    Integer categorie_id;
    Integer proba_id;
    Integer minim_varsta;
    Integer maxim_varsta;

    public CategorieVarsta(Integer categorie_id, Integer minim_varsta, Integer maxim_vasrta, Integer proba_id) {
        this.categorie_id = categorie_id;
        this.minim_varsta = minim_varsta;
        this.maxim_varsta = maxim_vasrta;
        this.proba_id = proba_id;
    }

    public CategorieVarsta() {
    }

    public Integer getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(Integer categorie_id) {
        this.categorie_id = categorie_id;
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

    @Override
    public Integer getID() {
        return categorie_id;
    }

    @Override
    public void setID(Integer id) {
        categorie_id = id;
    }


    @Override
    public boolean equals(Object other){

        if(!(other instanceof CategorieVarsta))
            return false;
        return this.categorie_id == ((CategorieVarsta)other).getCategorie_id();

    }

    public Integer getProba_id() {
        return proba_id;
    }

    public void setProba_id(Integer proba_id) {
        this.proba_id = proba_id;
    }
}
