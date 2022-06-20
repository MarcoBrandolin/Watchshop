package ch.bzz.watchshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.List;

public class Hersteller {

    @FormParam("bookUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String herstellerUUID;

    @FormParam("herstellerName")
    @NotEmpty
    @Size(min=2, max=15)
    private String herstellerName;

    @FormParam("herkunft")
    @NotEmpty
    @Size(min=2, max=15)
    private String herkunft;
    @JsonIgnore
    private List<Uhr> uhrenliste;

    public String getHerstellerUUID() {
        return herstellerUUID;
    }

    public void setHerstellerUUID(String herstellerUUID) {
        this.herstellerUUID = herstellerUUID;
    }

    public String getHerstellerName() {
        return herstellerName;
    }

    public void setHerstellerName(String herstellerName) {
        this.herstellerName = herstellerName;
    }

    public String getHerkunft() {
        return herkunft;
    }

    public void setHerkunft(String herkunft) {
        this.herkunft = herkunft;
    }

    public List<Uhr> getUhrenliste() {
        return uhrenliste;
    }

    public void setUhrenliste(List<Uhr> uhrenliste) {
        this.uhrenliste = uhrenliste;
    }
}
