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

import cci.caos.repository.Agent;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

@Path( "/session" )
public class SessionManager {


    @GET
    @Path( "{uuid}/fermerCandidature" )
    public Response fermerCandidature( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
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
    public Response modifierCandidats( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession,
            List<Candidature> candidatures ) {
        int ids = Integer.parseInt( idSession );
        if ( SapforServer.getSessionServer().isConnectedByUUID( uuid )
                && SapforServer.getSessionServer().getAgentByUUID( uuid ).getGestionnaire() ) {
            SapforServer.getSessionServer().modifierListeCandidats( ids, candidatures );
            return Response
                    .status( Status.OK )
                    .build();
        } else {
            return Response
                    .status( Status.FORBIDDEN )
                    .build();
        }
    }


    @GET
    @Path( "{uuid}/accessible" )
    @Produces( MediaType.APPLICATION_XML )
    public Response getListeSessionsAccessibles( @PathParam( "uuid" ) String uuid ) {
        SapforServer server = SapforServer.getSessionServer();
        if (server.isConnectedByUUID(uuid)) {
        	List<Candidature> listSessionsAccessibles = server.getSessionsAccessibles( uuid );
        	GenericEntity<List<Candidature>> listSessionsEntity = new GenericEntity<List<Candidature>>( listSessionsAccessibles ){};
        	return Response.status(Status.OK).entity(listSessionsEntity).build();
        } else {
        	return Response.status(Status.FORBIDDEN).build();
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
                .entity( s )
                .build();
    }

    // POUR L'EXEMPLE ****************************************************************************************************************************
    // Liste les candidatures à une session au format XML
    // http://localhost:8080/rest/session/listeCandidatures?Session=1
    @GET
    @Path( "listeCandidature" )
    @Produces( MediaType.APPLICATION_XML )
    public Response listeCandidature( @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
        SapforServer server = SapforServer.getSessionServer();
        List<Candidature> listeCandidatures = server.getListeCandidatures(ids);
        GenericEntity<List<Candidature>> entity = new GenericEntity<List<Candidature>>( listeCandidatures ) {
        };
        return Response.ok( entity ).build();
    }

    /* Liste Candidature ****************************************************************************************************************************
     * 
     * Liste les sessions auxquelles un agent à candidater
     * @return la liste des sessions accessibles
     * */
    @GET
    @Path("{uuid}")
    @Produces( MediaType.APPLICATION_JSON )
    public Response getListeCandidatures( @PathParam("uuid") String id){
    	SapforServer server = SapforServer.getSessionServer();
    	if (server.isConnectedByUUID(id)) {
    		List<Session> listeCandidature = server.getListeSession(id);
    		return Response.status(Status.OK).entity(listeCandidature).build();
    	}
    	else {
    		return Response.status(Status.FORBIDDEN).build();
    	}
    }


    /* Retirer Candidature */ //****************************************************************************************************************************
    @GET
    @Path( "{uuid}/retirerCandidature" )
    public Response retirerCandidature( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession ) {
    	SapforServer server = SapforServer.getSessionServer();
    	int ids = Integer.parseInt( idSession );
            if ( server.isConnectedByUUID( uuid )) {
                int idAgent = server.getAgentByUUID(uuid).getId();
                
                if (server.retirerCandidature(idAgent, ids)) {
                	return Response.status(Status.OK).build();
                } else {
                	/*
                	 * A discuter, ici la requête côté serveur s'est correctement déroulée mais la requête client a demandé
                	 * la suppression d'une candidature qui n'existe pas, ce que le serveur a detecté
                	 * J'aimerais donc notifier le client avec un statut, en l'occurence j'ai opté pour BAD_REQUEST
                	 */
                	return Response.status(Status.BAD_REQUEST).build();
                }
            } else {
            	return Response.status(Status.FORBIDDEN).build();
            }
    }
    	
    	
    @GET
    @Path( "listefermee" )
    @Produces( { MediaType.APPLICATION_JSON } )
    public List<Session> getClosedSession(){
            return 	SapforServer.getSessionServer().getListeSessionsFermees();
        }
    
    @GET
    @Path( "listeOuvertes" )
    @Produces( { MediaType.APPLICATION_XML } )
    public List<Session> getOpenedSession(){
            return 	SapforServer.getSessionServer().getListeSessionsOuvertes();
        }
    
  //****************************************************************************************************************************
    @GET
    @Path( "{uuid}/candidater" ) 
    public Response deposerCandidature( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession, @QueryParam( "Formateur" ) String role ) {
    	SapforServer server = SapforServer.getSessionServer();
    	int ids = Integer.parseInt( idSession );
    	boolean estFormateur = Boolean.getBoolean(role);
            if ( server.isConnectedByUUID( uuid )) {
                int idAgent = server.getAgentByUUID(uuid).getId();
                if (server.deposerCandidature( idAgent, ids, estFormateur )) {
                	return Response.status(Status.OK).build();
                } else {
                	return Response.status(Status.BAD_REQUEST).build();
                }
            } else {
            	return Response.status(Status.FORBIDDEN).build();
            }
    }
    
}
