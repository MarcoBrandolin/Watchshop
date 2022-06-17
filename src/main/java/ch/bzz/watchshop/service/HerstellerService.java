package ch.bzz.watchshop.service;


import ch.bzz.watchshop.data.DataHandler;
import ch.bzz.watchshop.model.Hersteller;
import ch.bzz.watchshop.model.Uhr;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;


/**
 * Kontoservice for hersteller
 *
 * @return a Response
 */

@Path("hersteller")
public class HerstellerService {


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

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readHersteller(@QueryParam("UUID") String UUID) {
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

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertHersteller(
            @FormParam("herstellerName") String herstellerName,
            @FormParam("herkunft") String herkunft

    ) {
        Hersteller hersteller = new Hersteller();
        hersteller.setHerstellerUUID(UUID.randomUUID().toString());
        hersteller.setHerstellerName(herstellerName);
        hersteller.setHerkunft(herkunft);


        DataHandler.insertHersteller(hersteller);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateHersteller(
            @FormParam("herstellerUUID") String herstellerUUID,
            @FormParam("herstellerName") String herstellerName,
            @FormParam("herkunft") String herkunft

    ) {
        int httpStatus = 200;
        Hersteller hersteller = DataHandler.readHerstellerByUUID(herstellerUUID);
        if (hersteller != null) {
            hersteller.setHerstellerName(herstellerName);
            hersteller.setHerkunft(herkunft);

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
    public Response deleteHersteller(
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
