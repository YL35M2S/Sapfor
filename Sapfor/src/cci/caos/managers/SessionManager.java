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

import cci.caos.beans.SessionGenerique;
import cci.caos.dao.AbstractDAOFactory;
import cci.caos.dao.SessionDao;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

@Path( "/session" )
public class SessionManager {

    /*
     * UseCase : #GESTION/FermerCandidature Fermer une sesion � la candidature
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/fermerCandidature?
     * Session=1
     * 
     * Permet � un gestionnaire de fermer les candidatures pour une session
     * donn�e
     * 
     * @return True si la fermeture a �t� effective, sinon False
     */
    @GET
    @Path( "{uuid}/fermerCandidature" )
    public Response fermerCandidature( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
        if ( SapforServer.getSessionServer().isConnectedByUUID( uuid )
                && SapforServer.getSessionServer().getAgentByUUID( uuid ).getGestionnaire() ) {
            Session s = SapforServer.getSessionServer().getSessionById( ids );
            s.fermerCandidature();
            AbstractDAOFactory adf = AbstractDAOFactory.getFactory( SapforServer.typeDao );
            SessionDao sessionDao = adf.getSessionDao();
            sessionDao.mettreAJour( s );
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
     * UseCase : #SELC/modifierCandidats Modifier les candidatures � une session
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/modifierCandidats?
     * Session=1
     * 
     * Permet � un gestionnaire de modifier les candidatures pour une session
     * donn�e (Accept�e/Refus�e/Liste d'Attente)
     * 
     * @Param candidatures Liste des candidatures modifi�e
     * 
     * @return True si la liste des candidats a �t� modifi�e, sinon False
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
     * UseCase : #LACC Renvoie la liste des sessions accessibles � un agent
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
     * UseCase : #LISTC Renvoie la liste des candidatures pour un agent donn�
     * http://localhost:8080/Sapfor/rest/sessions/{uuid} --------------------
     * Renvoie la liste des sessions auxquelles un agent � candidater
     * 
     * @return la liste des sessions auxquelles un agent � candidater
     */
    @GET
    @Path( "{uuid}" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getListeCandidatures( @PathParam( "uuid" ) String uuid ) {
        SapforServer server = SapforServer.getSessionServer();
        if ( server.isConnectedByUUID( uuid ) ) {
            List<Candidature> listeCandidature = server.getListeSession( uuid );
            GenericEntity<List<Candidature>> listeCandidatureEntity = new GenericEntity<List<Candidature>>(
                    listeCandidature ) {
            };
            return Response.status( Status.OK ).entity( listeCandidatureEntity ).build();
        } else {
            return Response.status( Status.FORBIDDEN ).build();
        }
    }

    /*
     * UseCase : #RETC Retirer une candidature (Form/Stag) pour un agent
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/retirerCandidature?
     * Session=1
     * 
     * Permet � un agent de retirer sa candidature pour une session donn�e
     * 
     * @return True si la candidature a �t� retir�e, sinon False
     */
    @GET
    @Path( "{uuid}/retirerCandidature" )
    public Response retirerCandidature( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession ) {
        SapforServer server = SapforServer.getSessionServer();
        if ( server.isConnectedByUUID( uuid ) ) {
            server.retirerCandidature( server.getAgentByUUID( uuid ).getId(), Integer.parseInt( idSession ) );
            return Response.status( Status.OK ).build();
        } else {
            return Response.status( Status.FORBIDDEN ).build();
        }
    }

    /*
     * UseCase : #SELC Renvoie la liste des sessions fermees � la candidature
     * http://localhost:8080/Sapfor/rest/sessions/listeFermees --------------
     * Permet d'obtenir la liste des sessions ferm�es � la candidature
     * 
     * @return Liste des sessions ferm�es � la candidature
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
     * ouvertes � la candidature
     * http://localhost:8080/Sapfor/rest/sessions/listeOuvertes
     * 
     * Permet d'obtenr la liste des sessions ouvertes � la candidature
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
     * Permet � un agent de d�poser une candidature pour une session donn�e
     * 
     * @return True si la candidature a �t� enregistr�e, sinon False
     */
    @GET
    @Path( "{uuid}/candidater" )
    public Response deposerCandidature( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession,
            @QueryParam( "Formateur" ) String role ) {
        SapforServer server = SapforServer.getSessionServer();
        if ( server.isConnectedByUUID( uuid ) ) {
            server.deposerCandidature( server.getAgentByUUID( uuid ).getId(),
                    server.getSessionById( Integer.parseInt( idSession ) ).getId(), Boolean.valueOf( role ) );
            return Response.status( Status.OK ).build();
        } else {
            return Response.status( Status.FORBIDDEN ).build();
        }
    }

    /*
     * Liste toutes les sessions enregistr�es sur le serveur
     * 
     * @return Liste des sessions ouvertes
     */
    @GET
    public Response listerSessions() {
        List<SessionGenerique> listeSessionGenerique = SapforServer.getSessionServer().getListeSessionsGeneriques();
        GenericEntity<List<SessionGenerique>> listeSessionsGeneriques = new GenericEntity<List<SessionGenerique>>(
                listeSessionGenerique ) {
        };
        return Response.status( Status.OK ).entity( listeSessionsGeneriques ).build();
    }

}
