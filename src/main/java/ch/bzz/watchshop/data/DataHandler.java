package ch.bzz.watchshop.data;

import ch.bzz.watchshop.model.Uhr;
import ch.bzz.watchshop.model.Hersteller;
import ch.bzz.watchshop.service.Config;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public final class DataHandler {
    private static List<Uhr> uhrList;
    private static List<Hersteller> herstellerList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
    }

    /**
     * initializes the lists
     */
    public static void initLists() {
        DataHandler.setUhrList(null);
        DataHandler.setHerstellerList(null);
    }
    /**
     * reads all books
     * @return list of books
     */
    public static List<Uhr> readAllUhren() {
        return getUhrList();
    }

    /**
     * reads a book by its uuid
     * @param uhrUUID
     * @return the Book (null=not found)
     */
    public static Uhr readUhrByUUID(String uhrUUID) {
        Uhr uhr = null;
        for (Uhr entry : getUhrList()) {
            if (entry.getUhrUUID().equals(uhrUUID)) {
                uhr = entry;
            }
        }
        return uhr;
    }

    /**
     * inserts a new book into the bookList
     *
     * @param uhr the book to be saved
     */
    public static void insertUhr(Uhr uhr) {
        getUhrList().add(uhr);
        writeUhrJSON();
    }

    /**
     * updates the bookList
     */
    public static void updateUhr() {
        writeUhrJSON();
    }

    /**
     * deletes a book identified by the bookUUID
     * @param uhrUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteUhr(String uhrUUID) {
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
     * reads all publishers
     * @return list of books
     */
    public static List<Hersteller> readAllHerstellers() {
        return getHerstellerList();
    }

    /**
     * reads a publisher by its uuid
     * @param herstellerUUID
     * @return the Publisher (null=not found)
     */
    public static Hersteller readHerstellerByUUID(String herstellerUUID) {
        Hersteller hersteller = null;
        for (Hersteller entry : getHerstellerList()) {
            if (entry.getHerstellerUUID().equals(herstellerUUID)) {
                hersteller = entry;
            }
        }
        return hersteller;
    }

    /**
     * inserts a new publisher into the bookList
     *
     * @param hersteller the publisher to be saved
     */
    public static void insertHersteller(Hersteller hersteller) {
        getHerstellerList().add(hersteller);
        writeHerstellerJSON();
    }

    /**
     * updates the publisherList
     */
    public static void updateHersteller() {
        writeHerstellerJSON();
    }

    /**
     * deletes a publisher identified by the publisherUUID
     * @param herstellerUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteHersteller(String herstellerUUID) {
        Hersteller hersteller = readHerstellerByUUID(herstellerUUID);
        if (hersteller != null) {
            getHerstellerList().remove(hersteller);
            writeHerstellerJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads the books from the JSON-file
     */
    private static void readUhrJSON() {
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
     * writes the bookList to the JSON-file
     */
    private static void writeUhrJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String uhrPath = Config.getProperty("UhrJSON");
        try {
            fileOutputStream = new FileOutputStream(uhrPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getUhrList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the publishers from the JSON-file
     */
    private static void readHerstellerJSON() {
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
     * writes the publisherList to the JSON-file
     */
    private static void writeHerstellerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String uhrPath = Config.getProperty("HerstellerJSON");
        try {
            fileOutputStream = new FileOutputStream(uhrPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getHerstellerList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets bookList
     *
     * @return value of bookList
     */

    private static List<Uhr> getUhrList() {

        if (uhrList == null) {
            setUhrList(new ArrayList<>());
            readUhrJSON();
        }
        return uhrList;
    }

    /**
     * sets bookList
     *
     * @param uhrList the value to set
     */

    private static void setUhrList(List<Uhr> uhrList) {
        DataHandler.uhrList = uhrList;
    }

    /**
     * gets publisherList
     *
     * @return value of publisherList
     */

    private static List<Hersteller> getHerstellerList() {
        if (herstellerList == null) {
            setHerstellerList(new ArrayList<>());
            readHerstellerJSON();
        }

        return herstellerList;
    }

    /**
     * sets publisherList
     *
     * @param publisherList the value to set
     */

    private static void setHerstellerList(List<Hersteller> publisherList) {
        DataHandler.herstellerList = publisherList;
    }


}