package Domain;

/**
 * Created by Utilizator on 12-Mar-17.
 */
public class Proba implements HasID<Integer>{

    Integer proba_id;
    String nume;

    public Proba(Integer proba_id, String nume) {
        this.proba_id = proba_id;
        this.nume = nume;
    }

    public Proba() {
    }

    public Integer getProba_id() {
        return proba_id;
    }

    public void setProba_id(Integer proba_id) {
        this.proba_id = proba_id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public Integer getID() {
        return proba_id;
    }

    @Override
    public void setID(Integer id) {
        proba_id = id;
    }

    @Override
    public boolean equals(Object other){

        if(!(other instanceof Proba))
            return false;
        return this.proba_id == ((Proba)other).getProba_id();

    }
}
