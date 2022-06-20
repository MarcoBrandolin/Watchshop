package ch.bzz.watchshop.model;

import ch.bzz.watchshop.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;


import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;


public class Uhr {
    @FormParam("uhrUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String uhrUUID;

    @FormParam("modelName")
    @NotEmpty
    @Size(min=2, max=10)
    private String modelName;

    @FormParam("preis")
    @NotNull
    @DecimalMax(value="1000000.00")
    @DecimalMin(value="0.05")
    private BigDecimal preis;

    @FormParam("material")
    @NotEmpty
    @Size(min=2, max=10)
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

    public BigDecimal getPreis() {
        return preis;
    }

    public void setPreis(BigDecimal preis) {
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
