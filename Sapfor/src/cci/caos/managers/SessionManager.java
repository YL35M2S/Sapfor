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

import cci.caos.beans.CandidatGenerique;
import cci.caos.beans.CandidatureGenerique;
import cci.caos.beans.SessionGenerique;
import cci.caos.dao.AbstractDAOFactory;
import cci.caos.dao.SessionDao;
import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

@Path( "/session" )
public class SessionManager {

    /*
     * UseCase : #GESTION/FermerCandidature Fermer une session a la candidature
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/fermerCandidature?
     * Session=1
     */
    /**
     * Permet a un gestionnaire de fermer les candidatures pour une session
     * donnee
     * 
     * @author TA (Antrema)
     * @param uuid
     * @param idSession
     * @return True si la fermeture a ete effective, sinon False
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
     * UseCase : #GESTION/ouvrirCandidature ouvrir une session a la candidature
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/ouvrirCandidature?
     * Session=1
     */
    /**
     * Permet a un gestionnaire d'ouvrir les candidatures pour une session
     * donnee
     * 
     * @param uuid
     * @param idSession
     * @return True si l'ouverture a ete effective, sinon False
     */
    @GET
    @Path( "{uuid}/ouvrirCandidature" )
    public Response ouvrirCandidature( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
        if ( SapforServer.getSessionServer().isConnectedByUUID( uuid )
                && SapforServer.getSessionServer().getAgentByUUID( uuid ).getGestionnaire() ) {
            Session s = SapforServer.getSessionServer().getSessionById( ids );
            s.ouvrirCandidature();
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
     * UseCase : #SELC/modifierCandidats Modifier les candidatures à une session
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/modifierCandidats?
     * Session=1
     */
    /**
     * Permet à un gestionnaire de modifier les candidatures pour une session
     * donnee (Acceptee/Refusee/Liste d'Attente)
     * 
     * @author AT (Antrema)
     * @param uuid
     * @param idSession
     * @Param candidatures Liste des candidatGenerique modifiee
     * 
     * @return True si la liste des candidats a ete modifiee, sinon False
     */
    @POST
    @Path( "{uuid}/modifierCandidats" )
    @Consumes( { MediaType.APPLICATION_XML } )
    public Response modifierCandidats( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession,
            List<CandidatGenerique> candidatures ) {
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
     */
    /**
     * Renvoie la liste des sessions auxquelles un agent peut candidater
     * 
     * @author FD (Fdescaves)
     * @param uuid
     * @return la liste des sessions accessibles
     */
    @GET
    @Path( "{uuid}/accessible" )
    @Produces( MediaType.APPLICATION_XML )
    public Response getListeSessionsAccessibles( @PathParam( "uuid" ) String uuid ) {
        SapforServer server = SapforServer.getSessionServer();
        if ( server.isConnectedByUUID( uuid ) ) {
            List<CandidatureGenerique> listSessionsAccessibles = server.getSessionsAccessibles( uuid );
            GenericEntity<List<CandidatureGenerique>> listSessionsEntity = new GenericEntity<List<CandidatureGenerique>>(
                    listSessionsAccessibles ) {
            };
            return Response.status( Status.OK ).entity( listSessionsEntity ).build();
        } else {
            return Response.status( Status.FORBIDDEN ).build();
        }
    }

    /*
     * UseCase : #LISTC Renvoie la liste des candidatures pour un agent donne
     * http://localhost:8080/Sapfor/rest/sessions/{uuid} --------------------
     */
    /**
     * Renvoie la liste des sessions auxquelles un agent a candidate
     * 
     * @author TP (TheoPerrin)
     * @param uuid
     * @return la liste des sessions auxquelles un agent a candidate
     */
    @GET
    @Path( "{uuid}" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getListeCandidatures( @PathParam( "uuid" ) String uuid ) {
        SapforServer server = SapforServer.getSessionServer();
        if ( server.isConnectedByUUID( uuid ) ) {
            List<CandidatureGenerique> listeCandidature = server.getListeSession( uuid );
            GenericEntity<List<CandidatureGenerique>> listeCandidatureEntity = new GenericEntity<List<CandidatureGenerique>>(
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
     */
    /**
     * Permet a un agent de retirer sa candidature pour une session donnee
     * 
     * @author YL (YL35M2S)
     * @param uuid
     * @param idSession
     * @return True si la candidature a ete retiree, sinon False
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
     * UseCase : #SELC Renvoie la liste des sessions fermees à la candidature
     * http://localhost:8080/Sapfor/rest/sessions/listeFermees --------------
     */
    /**
     * Permet d'obtenir la liste des sessions fermees a la candidature
     * 
     * @author YL (YL35M2S)
     * @return Liste des sessions fermees a la candidature
     */
    @GET
    @Path( "listeFermees" )
    @Produces( { MediaType.APPLICATION_XML } )
    public Response getClosedSession() {
        List<SessionGenerique> listeFermees = SapforServer.getSessionServer().getListeSessionsFermees();
        GenericEntity<List<SessionGenerique>> listeClosedSessionEntity = new GenericEntity<List<SessionGenerique>>(
                listeFermees ) {
        };
        return Response.status( Status.OK ).entity( listeClosedSessionEntity ).build();
    }

    /*
     * UseCase : #GESTION/ListeOuverte Renvoie la liste des sessions ouvertes à
     * la candidature http://localhost:8080/Sapfor/rest/sessions/listeOuvertes
     */
    /**
     * Permet d'obtenr la liste des sessions ouvertes a la candidature
     * 
     * @author RPD (Denier-Poulain-Romain)
     * @return Liste des sessions ouvertes au format XML
     */
    @GET
    @Path( "listeOuvertes" )
    @Produces( { MediaType.APPLICATION_XML } )
    public Response getOpenedSession() {
        List<SessionGenerique> listeOuvertes = SapforServer.getSessionServer().getListeSessionsOuvertes();
        GenericEntity<List<SessionGenerique>> listeOpenedSessionEntity = new GenericEntity<List<SessionGenerique>>(
                listeOuvertes ) {
        };
        return Response.status( Status.OK ).entity( listeOpenedSessionEntity ).build();
    }

    /*
     * UseCase : #DEPC Deposer une candidature (Form/Stag) pour un agent
     * http://localhost:8080/Sapfor/rest/sessions/{uuid}/candidater?Session=1&
     * Formateur="True"
     */
    /**
     * Permet a un agent de deposer une candidature pour une session donnee
     * 
     * @author SI (souraagui)
     * @param uuid
     * @param idSession
     * @param role
     * @return True si la candidature a ete enregistree, sinon False
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
     * UseCase : #SELC Renvoie la liste des candidature d'une session donnée
     * http://localhost:8080/Sapfor/rest/session/{uuid}/listeCandidat?Session=1
     */
    /**
     * Renvoie la liste des candidatures
     * 
     * @author RPD (Denier-Poulain-Romain)
     * @param uuid
     * @param idSession
     * @return la liste des candidature d'une session donnée
     */
    @GET
    @Path( "{uuid}/listeCandidat" )
    public Response getListeCandidat( @PathParam( "uuid" ) String uuid, @QueryParam( "Session" ) String idSession ) {
        SapforServer server = SapforServer.getSessionServer();
        if ( server.isConnectedByUUID( uuid ) ) {
            List<CandidatGenerique> listeCandidat = server.getListeCandidats( Integer.parseInt( idSession ) );
            GenericEntity<List<CandidatGenerique>> listeCandidatEntity = new GenericEntity<List<CandidatGenerique>>(
                    listeCandidat ) {
            };
            return Response.status( Status.OK ).entity( listeCandidatEntity ).build();
        } else {
            return Response.status( Status.FORBIDDEN ).build();
        }
    }

    /**
     * Liste toutes les sessions enregistrees sur le serveur
     * 
     * @return Liste des sessions ouvertes
     */
    @GET
    @Produces( { MediaType.APPLICATION_XML } )
    public Response listerSessions() {
        List<SessionGenerique> listeSessionGenerique = SapforServer.getSessionServer().getListeSessionsGeneriques();
        GenericEntity<List<SessionGenerique>> listeSessionsGeneriques = new GenericEntity<List<SessionGenerique>>(
                listeSessionGenerique ) {
        };
        return Response.status( Status.OK ).entity( listeSessionsGeneriques ).build();
    }

    /**
     * Detaille la session identifiee par l'id sur le serveur
     * 
     * @param idSession
     *            L'id de la session recherchee
     * @return Liste des sessions ouvertes
     */
    @GET
    @Path( "/session" )
    @Produces( { MediaType.APPLICATION_XML } )
    public Response listerSession( @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
        SessionGenerique session = SapforServer.getSessionServer().getSessionGenerique( ids );
        GenericEntity<SessionGenerique> sessionGenerique = new GenericEntity<SessionGenerique>(
                session ) {
        };
        return Response.status( Status.OK ).entity( sessionGenerique ).build();
    }

}
