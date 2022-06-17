package ch.bzz.watchshop.service;


import ch.bzz.watchshop.data.DataHandler;
import ch.bzz.watchshop.model.Uhr;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;


/**
 * Kontoservice for uhr
 *
 * @return a Response
 */

@Path("uhr")
public class UhrService {


    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response uhrList() {
        List<Uhr> uhrList = DataHandler.readAllUhren();
        return Response
                .status(200)
                .entity(uhrList)
                .build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readUhr(@QueryParam("UUID") String UUID) {
        int httpStatus = 200;
        Uhr uhr = DataHandler.readUhrByUUID(UUID);
        if (uhr == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(uhr)
                .build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertUhr(
            @FormParam("modelName") String modelName,
            @FormParam("preis") double preis,
            @FormParam("material") String material,
            @FormParam("herstellerUUID") String herstellerUUID
    ) {
        Uhr uhr = new Uhr();
        uhr.setUhrUUID(UUID.randomUUID().toString());
        uhr.setModelName(modelName);
        uhr.setPreis(preis);
        uhr.setMaterial(material);
        uhr.setHerstellerbyUUID(herstellerUUID);

        DataHandler.insertUhr(uhr);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUhr(
            @FormParam("uhrUUID") String uhrUUID,
            @FormParam("modelName") String modelName,
            @FormParam("preis") Double preis,
            @FormParam("material") String material,
            @FormParam("herstellerUUID") String herstellerUUID
    ) {
        int httpStatus = 200;
        Uhr uhr = DataHandler.readUhrByUUID(uhrUUID);
        if (uhr != null) {
            uhr.setUhrUUID(uhrUUID);
            uhr.setModelName(modelName);
            uhr.setPreis(preis);
            uhr.setMaterial(material);
            uhr.setHerstellerbyUUID(herstellerUUID);

            DataHandler.updateUhr();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUhr(
            @QueryParam("uuid") String uhrUUID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteUhr(uhrUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
    private void setAttributes(
            Uhr uhr,
            String modelName,
            double preis,
            String material,
            String herstellerUUID

    ) {
        uhr.setModelName(modelName);
        uhr.setPreis(preis);
        uhr.setMaterial(material);
        uhr.setHerstellerbyUUID(herstellerUUID);

    }

}

