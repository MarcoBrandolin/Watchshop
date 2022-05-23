package ch.bzz.watchshop.model;

import java.util.List;

public class Hersteller {
    private String herstellerUUID;
    private String herstellerName;
    private String herkunft;
    private List<Uhr> uhrenliste;

    public String getHerstellerUUID() {
        return herstellerUUID;
    }

    public void setHerstellerUUID(String herstellerUUID) {
        this.herstellerUUID = herstellerUUID;
    }

    public List<Uhr> getUhrenliste() {
        return uhrenliste;
    }

    public void setUhrenliste(List<Uhr> uhrenliste) {
        this.uhrenliste = uhrenliste;
    }
}
