package ch.bzz.watchshop.service;


import ch.bzz.watchshop.data.DataHandler;
import ch.bzz.watchshop.model.Uhr;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;



/**
 * Kontoservice for uhr
 *
 * @return a Response
 */

@Path("uhr")
public class UhrService {


    /**
     * reads a list of all uhren
     * @return  uhren as JSON
     */

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

    /**
     * reads a uhr identified by the uuid
     * @return uhr
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readUhr(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("UUID") String UUID) {
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

    /**
     * inserts a new uhr
     * @param herstellerUUID the uuid of the hersteller
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertUhr(
            @Valid @BeanParam Uhr uhr,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("herstellerUUID") String herstellerUUID
    ) {

        uhr.setHerstellerbyUUID(herstellerUUID);

        DataHandler.insertUhr(uhr);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a new uhr
     * @param herstellerUUID the uuid of the hersteller
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUhr(
            @Valid @BeanParam Uhr uhr,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("herstellerUUID") String herstellerUUID
    ) {
        int httpStatus = 200;
        Uhr alteuhr = DataHandler.readUhrByUUID(uhr.getUhrUUID());
        if (alteuhr != null) {
            uhr.setModelName(uhr.getModelName());
            uhr.setPreis(uhr.getPreis());
            uhr.setMaterial(uhr.getMaterial());
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

    /**
     * deletes a uhr identified by its uuid
     * @param uhrUUID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUhr(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
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

}

