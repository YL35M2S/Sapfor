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

    /*
     * UseCase : #GESTION/FermerCandidature Fermer une sesion à la candidature
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/fermerCandidature?
     * Session=1
     * 
     * Permet à un gestionnaire de fermer les candidatures pour une session
     * donnée
     * 
     * @return True si la fermeture a été effective, sinon False
     */
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

    /*
     * UseCase : #SELC/modifierCandidats Modifier les candidatures à une session
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/modifierCandidats?
     * Session=1
     * 
     * Permet à un gestionnaire de modifier les candidatures pour une session
     * donnée (Acceptée/Refusée/Liste d'Attente)
     * 
     * @Param candidatures Liste des candidatures modifiée
     * 
     * @return True si la liste des candidats a été modifiée, sinon False
     */
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

    /*
     * UseCase : #LACC Renvoie la liste des sessions accessibles à un agent
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/accessible -------
     * Renvoie la liste des sessions auxquelles un agent peut candidater
     * 
     * @return la liste des sessions accessibles
     */
    @GET
    @Path( "{uuid}/accessible" )
    @Produces( MediaType.APPLICATION_XML )
    public Response getListeSessionsAccessibles( @PathParam( "uuid" ) String uuid ) {
        SapforServer server = SapforServer.getSessionServer();
        if ( server.isConnectedByUUID( uuid ) ) {
            List<Candidature> listSessionsAccessibles = server.getSessionsAccessibles( uuid );
            GenericEntity<List<Candidature>> listSessionsEntity = new GenericEntity<List<Candidature>>(
                    listSessionsAccessibles ) {
            };
            return Response.status( Status.OK ).entity( listSessionsEntity ).build();
        } else {
            return Response.status( Status.FORBIDDEN ).build();
        }
    }

    /*
     * UseCase : #LISTC Renvoie la liste des candidatures pour un agent donné
     * http://localhost:8080/Sapfor/rest/sessions/{uuid} --------------------
     * Renvoie la liste des sessions auxquelles un agent à candidater
     * 
     * @return la liste des sessions auxquelles un agent à candidater
     */
    @GET
    @Path( "{uuid}" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getListeCandidatures( @PathParam( "uuid" ) String id ) {
        SapforServer server = SapforServer.getSessionServer();
        if ( server.isConnectedByUUID( id ) ) {
            List<Session> listeCandidature = server.getListeSession( id );
            return Response.status( Status.OK ).entity( listeCandidature ).build();
        } else {
            return Response.status( Status.FORBIDDEN ).build();
        }
    }

    /*
     * UseCase : #RETC Retirer une candidature (Form/Stag) pour un agent
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/retirerCandidature?
     * Session=1
     * 
     * Permet à un agent de retirer sa candidature pour une session donnée
     * 
     * @return True si la candidature a été retirée, sinon False
     */
    @GET
    @Path( "{uuid}/retirerCandidature" )
    public Response retirerCandidature( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession ) {
        SapforServer server = SapforServer.getSessionServer();
        int ids = Integer.parseInt( idSession );
        if ( server.isConnectedByUUID( uuid ) ) {
            int idAgent = server.getAgentByUUID( uuid ).getId();

            if ( server.retirerCandidature( idAgent, ids ) ) {
                return Response.status( Status.OK ).build();
            } else {
                return Response.status( Status.BAD_REQUEST ).build();
            }
        } else {
            return Response.status( Status.FORBIDDEN ).build();
        }
    }

    /*
     * UseCase : #SELC Renvoie la liste des sessions fermees à la candidature
     * http://localhost:8080/Sapfor/rest/sessions/listeFermees --------------
     * Permet d'obtenir la liste des sessions fermées à la candidature
     * 
     * @return Liste des sessions fermées à la candidature
     */
    @GET
    @Path( "listeFermees" )
    @Produces( { MediaType.APPLICATION_JSON } )
    public Response getClosedSession() {
        List<Session> listeFermees = SapforServer.getSessionServer().getListeSessionsFermees();
        return Response.status( Status.OK ).entity( listeFermees ).build();
    }

    /*
     * UseCase : #GESTION/FermerCandidature Renvoie la liste des sessions
     * ouvertes à la candidature
     * http://localhost:8080/Sapfor/rest/sessions/listeOuvertes
     * 
     * Permet d'obtenr la liste des sessions ouvertes à la candidature
     * 
     * @return Liste des sessions ouvertes au format JSON
     */
    @GET
    @Path( "listeOuvertes" )
    @Produces( { MediaType.APPLICATION_JSON } )
    public Response getOpenedSession() {
        List<Session> listeOuvertes = SapforServer.getSessionServer().getListeSessionsOuvertes();
        return Response.status( Status.OK ).entity( listeOuvertes ).build();
    }

    /*
     * UseCase : #DEPC Deposer une candidature (Form/Stag) pour un agent
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/candidater?Session=1&
     * Formateur="True"
     * 
     * Permet à un agent de déposer une candidature pour une session donnée
     * 
     * @return True si la candidature a été enregistrée, sinon False
     */
    @GET
    @Path( "{uuid}/candidater" )
    public Response deposerCandidature( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession,
            @QueryParam( "Formateur" ) String role ) {
        SapforServer server = SapforServer.getSessionServer();
        int ids = Integer.parseInt( idSession );
        boolean estFormateur = Boolean.getBoolean( role );
        if ( server.isConnectedByUUID( uuid ) ) {
            int idAgent = server.getAgentByUUID( uuid ).getId();
            if ( server.deposerCandidature( idAgent, ids, estFormateur ) ) {
                return Response.status( Status.OK ).build();
            } else {
                return Response.status( Status.BAD_REQUEST ).build();
            }
        } else {
            return Response.status( Status.FORBIDDEN ).build();
        }
    }
}
