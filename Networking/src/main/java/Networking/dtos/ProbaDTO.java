package Networking.dtos;

import java.io.Serializable;

/**
 * Created by Utilizator on 04-Apr-17.
 */
public class ProbaDTO  implements Serializable {
    Integer proba_id;
    String nume;

    public ProbaDTO(Integer proba_id, String nume) {
        this.proba_id = proba_id;
        this.nume = nume;
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
}
