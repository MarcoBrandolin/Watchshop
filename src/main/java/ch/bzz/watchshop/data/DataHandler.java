package ch.bzz.watchshop.data;

import ch.bzz.watchshop.model.Uhr;
import ch.bzz.watchshop.model.Hersteller;
import ch.bzz.watchshop.service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Uhr> uhrList;
    private List<Hersteller> herstellerList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setHerstellerList(new ArrayList<>());
        readHerstellerJSON();
        setUhrList(new ArrayList<>());
        readUhrJSON();
    }

    /**
     * gets the only instance of this class
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all books
     * @return list of books
     */
    public List<Uhr> readAllUhren() {
        return getUhrList();
    }

    /**
     * reads a book by its uuid
     * @param uhrUUID
     * @return the Book (null=not found)
     */
    public Uhr readUhrByUUID(String uhrUUID) {
        Uhr uhr = null;
        for (Uhr entry : getUhrList()) {
            if (entry.getUhrUUID().equals(uhrUUID)) {
                uhr = entry;
            }
        }
        return uhr;
    }

    /**
     * reads all Publishers
     * @return list of publishers
     */
    public List<Hersteller> readAllHersteller() {

        return getHerstellerList();
    }

    public Boolean deleteUhr(String uhrUUID) {
        Uhr uhr = readUhrByUUID(uhrUUID);
        if (uhr != null) {
            getUhrList().remove(uhr);
            writeUhrJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads a publisher by its uuid
     * @param herstellerUUID
     * @return the Publisher (null=not found)
     */
    public Hersteller readHerstellerByUUID(String herstellerUUID) {
        Hersteller hersteller = null;
        for (Hersteller entry : getHerstellerList()) {
            if (entry.getHerstellerUUID().equals(herstellerUUID)) {
                hersteller = entry;
            }
        }
        return hersteller;
    }

    /**
     * reads the books from the JSON-file
     */
    private void readUhrJSON() {
        try {
            String path = Config.getProperty("UhrJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Uhr[] uhren = objectMapper.readValue(jsonData, Uhr[].class);
            for (Uhr uhr : uhren) {
                getUhrList().add(uhr);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the publishers from the JSON-file
     */
    private void readHerstellerJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("HerstellerJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Hersteller[] herstellers = objectMapper.readValue(jsonData, Hersteller[].class);
            for (Hersteller hersteller : herstellers) {
                getHerstellerList().add(hersteller);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * gets bookList
     *
     * @return value of bookList
     */
    private List<Uhr> getUhrList() {
        return uhrList;
    }

    /**
     * sets bookList
     *
     * @param uhrList the value to set
     */
    private void setUhrList(List<Uhr> uhrList) {
        this.uhrList = uhrList;
    }

    /**
     * gets publisherList
     *
     * @return value of publisherList
     */
    private List<Hersteller> getHerstellerList() {
        return herstellerList;
    }

    /**
     * sets publisherList
     *
     * @param herstellerList the value to set
     */
    private void setHerstellerList(List<Hersteller> herstellerList) {
        this.herstellerList = herstellerList;
    }


}