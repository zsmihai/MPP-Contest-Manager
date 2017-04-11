package Networking.dtos;

import Domain.Proba;

import java.io.Serializable;

/**
 * Created by Utilizator on 04-Apr-17.
 */
public class InscriereProbaDTO implements Serializable{

    private String participant_nume;
    private Integer participant_varsta;
    private ProbaDTO proba;

    public InscriereProbaDTO(String participant_nume, Integer participant_varsta, ProbaDTO probaDTO) {
        this.participant_nume = participant_nume;
        this.participant_varsta = participant_varsta;
        this.proba = probaDTO;
    }


    public ProbaDTO getProbaDTO() {
        return proba;
    }

    public void setProbaDTO(ProbaDTO probaDTO) {
        this.proba = probaDTO;
    }

    public String getParticipant_nume() {
        return participant_nume;
    }

    public void setParticipant_nume(String participant_nume) {
        this.participant_nume = participant_nume;
    }

    public Integer getParticipant_varsta() {
        return participant_varsta;
    }

    public void setParticipant_varsta(Integer participant_varsta) {
        this.participant_varsta = participant_varsta;
    }
}
