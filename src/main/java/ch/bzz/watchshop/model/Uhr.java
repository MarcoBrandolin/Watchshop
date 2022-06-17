package ch.bzz.watchshop.model;

import ch.bzz.watchshop.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class Uhr {
    private String uhrUUID;
    private String modelName;
    private double preis;
    private String material;
    @JsonIgnore
    private Hersteller hersteller;

    public String getUhrUUID() {
        return uhrUUID;
    }

    public void setUhrUUID(String uhrUUID) {
        this.uhrUUID = uhrUUID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Hersteller getHersteller() {
        return hersteller;
    }

    public void setHersteller(Hersteller hersteller) {
        this.hersteller = hersteller;
    }

    public String getHerstellerUUID() {
        if (getHersteller()== null) return null;
        return getHersteller().getHerstellerUUID();
    }

    /**
     * creates a Hersteller-object without the uhrlist
     * @param herstellerUUID the key
     */
    public void setHerstellerbyUUID(String herstellerUUID) {
        setHersteller(new Hersteller());
        Hersteller hersteller = DataHandler.readHerstellerByUUID(herstellerUUID);
        getHersteller().setHerstellerUUID(herstellerUUID);
        getHersteller().setHerstellerName(hersteller.getHerstellerName());
        getHersteller().setHerkunft(hersteller.getHerkunft());
    }
}
