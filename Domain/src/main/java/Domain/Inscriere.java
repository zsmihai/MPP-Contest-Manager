package Domain;

/**
 * Created by Utilizator on 12-Mar-17.
 */
public class Inscriere implements HasID<Integer>{

    Integer inscriere_id;
    Integer proba_id;
    Integer participant_varsta;
    String participant_nume;
    Integer categorie_varsta_id;

    public Inscriere(Integer inscriere_id, Integer proba_id, Integer participant_varsta, String participant_nume, Integer categorie_varsta_id) {
        this.inscriere_id = inscriere_id;
        this.proba_id = proba_id;
        this.participant_varsta = participant_varsta;
        this.participant_nume=participant_nume;
        this.categorie_varsta_id = categorie_varsta_id;
    }

    public Inscriere() {
    }

    public Integer getInscriere_id() {
        return inscriere_id;
    }

    public void setInscriere_id(Integer inscriere_id) {
        this.inscriere_id = inscriere_id;
    }

    public Integer getProba_id() {
        return proba_id;
    }

    public void setProba_id(Integer proba_id) {
        this.proba_id = proba_id;
    }

    @Override
    public Integer getID() {
        return inscriere_id;
    }

    @Override
    public void setID(Integer id) {
        inscriere_id=id;
    }

    public Integer getParticipant_varsta() {
        return participant_varsta;
    }

    public void setParticipant_varsta(Integer participant_varsta) {
        this.participant_varsta = participant_varsta;
    }

    public String getParticipant_nume() {
        return participant_nume;
    }

    public void setParticipant_nume(String participant_nume) {
        this.participant_nume = participant_nume;
    }

    public Integer getCategorie_varsta_id() {
        return categorie_varsta_id;
    }

    public void setCategorie_varsta_id(Integer categorie_varsta_id) {
        this.categorie_varsta_id = categorie_varsta_id;
    }
}
