package ch.bzz.watchshop.service;


import ch.bzz.watchshop.data.DataHandler;
import ch.bzz.watchshop.model.Hersteller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
        List<Hersteller> herstellerList = DataHandler.getInstance().readAllHersteller();
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
        Hersteller hersteller = DataHandler.getInstance().readHerstellerByUUID(UUID);
        if (hersteller == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(hersteller)
                .build();
    }

}
