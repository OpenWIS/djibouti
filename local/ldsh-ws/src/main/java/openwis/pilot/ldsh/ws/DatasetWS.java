package openwis.pilot.ldsh.ws;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import openwis.pilot.ldsh.dto.DatasetDTO;
import openwis.pilot.ldsh.manager.service.DatasetService;

import org.apache.cxf.rs.security.cors.CorsHeaderConstants;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.apache.cxf.rs.security.cors.LocalPreflight;
import org.ops4j.pax.cdi.api.OsgiService;

//  @CrossOriginResourceSharing(
//          allowOrigins = {
//             "*"
//          },
//          allowCredentials = true 
//          )
@Singleton
public class DatasetWS {

    private static final Logger logger = Logger.getLogger(DatasetWS.class.getName());

    @Context
    private HttpHeaders headers;

    @Inject
    @OsgiService
    private DatasetService datasetService;

    @POST
    @Path("/register")
    @CrossOriginResourceSharing(allowAllOrigins = true, allowCredentials = true, maxAge = 1, allowHeaders = {
            "X-custom-1", "X-custom-2" }, exposeHeaders = { "X-custom-3", "X-custom-4" })
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registration(DatasetDTO dataset) {

        return Response.ok((datasetService.saveDataset(dataset)))
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "DELETE PUT POST")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "true")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_HEADERS,
                        "origin, content-type, accept, authorization, Origin, Content-type, Accept, Authorization")
                .build();

    }

    @POST
    @Path("/verification")
    @CrossOriginResourceSharing(allowAllOrigins = true)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userVerification(DatasetDTO dataset) {

        // logger.log(Level.INFO, "I got At WS: \n " + dataset.toString());
        logger.log(Level.INFO, "calling service....: \n ");
        // return Response.ok(dataset)
        return Response.ok(datasetService.saveDataset(dataset))
        .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "GET,HEAD,OPTIONS,POST,PUT")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "true")
                .header(CorsHeaderConstants.HEADER_AC_ALLOW_HEADERS,
                        "Access-Control-Allow-Headers, origin, content-type, accept, authorization, Origin, Content-type, Accept, Authorization")
                .build();
    }




    @POST
    @Path("/testPost")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DatasetDTO testpost(DatasetDTO dataset) {

        System.out.println("testpost \n " + dataset.toString());
logger.log(Level.SEVERE, "I got At WS: \n " + dataset.toString());
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // edw prepei na kanei read..............................................................
        return dataset;
    }


    @GET
    @Path("/testget")
    @CrossOriginResourceSharing(allowAllOrigins = true)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response test() {

        ///TODO 
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // edw prepei na kanei read.......................
        // return Response.ok(datasetService.saveDataset(new DatasetDTO())).build();

        //   return Response.ok((new DatasetDTO()))
          return Response.ok(("new DatasetDTO() APO TO SERVER"))
                  .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*")
                  .header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "GET,HEAD,OPTIONS,POST,PUT")
                  .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "true")
                  .header(CorsHeaderConstants.HEADER_AC_ALLOW_HEADERS, "origin, content-type, accept, authorization, Origin, Content-type, Accept, Authorization")
                  .build();

    }

    // @OPTIONS
    // @Path("{path : .*}")
    // @CrossOriginResourceSharing(allowAllOrigins = true)
    // @LocalPreflight
    // public Response options() {
    //     String origin = headers.getRequestHeader("Origin").get(0);

    //         return Response.ok()
    //                        .header(CorsHeaderConstants.HEADER_AC_ALLOW_METHODS, "DELETE PUT POST")
    //                        .header(CorsHeaderConstants.HEADER_AC_ALLOW_CREDENTIALS, "true")
    //                        .header(CorsHeaderConstants.HEADER_AC_ALLOW_ORIGIN, "*")
    //                     //    .header(CorsHeaderConstants.HEADER_AC_ALLOW_HEADERS, "origin, content-type, accept, authorization, Origin, Content-type, Accept, Authorization")
    //                        .build();
    //     }

}
