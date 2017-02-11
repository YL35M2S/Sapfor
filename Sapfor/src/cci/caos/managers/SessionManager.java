package cci.caos.managers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cci.caos.repository.Candidature;
import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

@Path( "/session" )
public class SessionManager {

    @GET
    @Path( "{uuid}/fermerCandidature" )
    public Response fermerCandidature( @PathParam( "uuid" ) String uid, @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
        int uuid = Integer.parseInt( uid );
        if ( SapforServer.getSessionServer().isConnectedByUUID( uuid )
                && SapforServer.getSessionServer().getAgentByUUID( uuid ).getGestionnaire() ) {
            SapforServer.getSessionServer().getSessionById( ids ).fermerCandidature();
            return Response
                    .status( Status.OK )
                    .build();
        } else {
            return Response
                    .status( Status.FORBIDDEN )
                    .build();
        }
    }

    @POST
    @Path( "{uuid}/modifierCandidats" )
    @Consumes( { MediaType.APPLICATION_JSON } )
    public Response modifierCandidats( @PathParam( "uuid" ) String uid, @QueryParam( "Session" ) String idSession,
            List<Candidature> candidatures ) {
        int ids = Integer.parseInt( idSession );
        int uuid = Integer.parseInt( uid );
        if ( SapforServer.getSessionServer().isConnectedByUUID( uuid )
                && SapforServer.getSessionServer().getAgentByUUID( uuid ).getGestionnaire() ) {
            SapforServer.getSessionServer().getSessionById( ids ).modifierListeCandidats( candidatures );
            return Response
                    .status( Status.OK )
                    .build();
        } else {
            return Response
                    .status( Status.FORBIDDEN )
                    .build();
        }
    }

    // POUR L'EXEMPLE
    // Recuperation d'une session au format XML
    // http://localhost:8080/rest/session/sessions?Session=1
    @GET
    @Path( "sessions" )
    @Produces( { MediaType.APPLICATION_XML } )
    public Session listeSession( @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
        Session s = SapforServer.getSessionServer().getSessionById( ids );
        return s;
    }

    // POUR L'EXEMPLE
    // Creation d'une session à partir d'une entrée au format XML
    // http://localhost:8080/rest/session/createSession
    @POST
    @Path( "createSession" )
    @Consumes( MediaType.APPLICATION_XML )
    public Response createSession( Session session ) {
        Session s = session;
        return Response
                .status( Status.OK )
                .entity( session )
                .build();
    }

    // POUR L'EXEMPLE
    // Liste les candidatures à une session au format XML
    // http://localhost:8080/rest/session/listeCandidatures?Session=1
    @GET
    @Path( "listeCandidature" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response listeCandidature( @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
        Session s = SapforServer.getSessionServer().getSessionById( ids );
        List<Candidature> list = s.getCandidats();
        GenericEntity<List<Candidature>> entity = new GenericEntity<List<Candidature>>( list ) {
        };
        return Response.ok( entity ).build();
    }
}