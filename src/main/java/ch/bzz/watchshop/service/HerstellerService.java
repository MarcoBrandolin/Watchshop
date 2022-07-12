package ch.bzz.watchshop.service;


import ch.bzz.watchshop.data.DataHandler;
import ch.bzz.watchshop.model.Hersteller;
import ch.bzz.watchshop.model.Uhr;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.RequestWrapper;
import java.util.List;
import java.util.UUID;


/**
 * Kontoservice for hersteller
 *
 * @return a Response
 */

@Path("hersteller")
public class HerstellerService {


    /**
     * reads a list of all herstellers
     * @return  hersteller as JSON
     */
    @RolesAllowed({"admin", "user"})
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response herstellerList() {
        List<Hersteller> herstellerList = DataHandler.readAllHerstellers();
        return Response
                .status(200)
                .entity(herstellerList)
                .build();
    }

    /**
     * reads a hersteller identified by the uuid
     *  the key
     * @return hersteller
     */
    @RolesAllowed({"admin", "user"})
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readHersteller(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("UUID") String UUID) {
        int httpStatus = 200;
        Hersteller hersteller = DataHandler.readHerstellerByUUID(UUID);
        if (hersteller == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(hersteller)
                .build();
    }

    /**
     * inserts a new hersteller
     * @param hersteller the name of the hersteller
     * @return Response
     */
    @RolesAllowed({"admin", "user"})
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertHersteller(
            @Valid @BeanParam Hersteller hersteller

    ) {
        hersteller.setHerstellerUUID(UUID.randomUUID().toString());

        DataHandler.insertHersteller(hersteller);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a hersteller
     *  the key
     * @param hersteller the name of the hersteller
     * @return Response
     */
    @RolesAllowed({"admin", "user"})
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateHersteller(
            @Valid @BeanParam Hersteller hersteller

    ) {
        int httpStatus = 200;
        Hersteller alterhersteller = DataHandler.readHerstellerByUUID(hersteller.getHerstellerUUID());
        if (alterhersteller != null) {
            alterhersteller.setHerstellerName(hersteller.getHerstellerName());
            alterhersteller.setHerkunft(hersteller.getHerkunft());

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
     * deletes a hersteller identified by its uuid
     *  the key
     * @return  Response
     */
    @RolesAllowed({"admin"})
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteHersteller(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String herstellerUUID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteHersteller(herstellerUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
