package cci.caos.tests;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cci.caos.beans.AgentConnection;
import cci.caos.beans.SessionGenerique;
import cci.caos.dao.SessionDao;
import cci.caos.dao.factory.AbstractDAOFactory;
import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

public class TestAT {

    private final String WS_URI          = "http://localhost:8080/Sapfor/rest";
    private String       uuidAgent;
    private String       uuidGestionnaire;
    private int          idSessionDeTest = 1;
    private boolean      etatInitialSession;

    @Before
    public void initTest() {
        // Initialisation des UUID Agent et Gestionnaire
        uuidAgent = getIdentification( "15122010", "mdp" ).getUuid();
        uuidGestionnaire = getIdentification( "19041975", "mdp" ).getUuid();

        // Sauvegarde de l'etat initial de la session de test sur le serveur
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( SapforServer.typeDao );
        SessionDao sessionDao = adf.getSessionDao();
        Session sessionDeTest = sessionDao.trouver( idSessionDeTest );
        etatInitialSession = sessionDeTest.isOuverteInscription();

        // Ouverture de la session (si besoin) et mise a jour du DAO
        if ( !etatInitialSession ) {
            sessionDeTest.setOuverteInscription( true );
            sessionDao.mettreAJour( sessionDeTest );
        }
    }

    @Test
    public void testOuvertesFermerFermees() {
        // Test de la presence dans la liste des sessions ouvertes
        Assert.assertEquals( true, testListesOuvertes() );

        // Test de la fermeture de la session par un gestionnaire
        Assert.assertEquals( 200, testFermerCandidatureGestionnaire() );

        // Test de la fermeture de la session par un agent
        Assert.assertEquals( 403, testFermerCandidatureAgent() );

        // Test de la presence dans la liste des sessions fermees
        Assert.assertEquals( true, testListesFermees() );
    }

    @After
    public void resetTest() {
        if ( etatInitialSession ) {
            // Retour a l'etat initial de la session de test sur le serveur
            AbstractDAOFactory adf = AbstractDAOFactory.getFactory( SapforServer.typeDao );
            SessionDao sessionDao = adf.getSessionDao();
            Session sessionDeTest = sessionDao.trouver( idSessionDeTest );
            sessionDeTest.setOuverteInscription( etatInitialSession );
        }
    }

    /* FONCTIONS ELEMENTAIRES */
    /**
     * Renvoie la liste des sessions ouvertes
     * 
     * @return true si la session est presente dans la liste des sessions
     *         ouvertes
     */
    public boolean testListesOuvertes() {
        boolean estDansListesOuvertes = false;
        List<SessionGenerique> models = new ArrayList<>();
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( getBaseUri() );
        List<SessionGenerique> all = target.path( "session/listeOuvertes" ).request()
                .get( new GenericType<List<SessionGenerique>>() {
                } );
        for ( SessionGenerique s : all ) {
            if ( s.getId() == idSessionDeTest ) {
                estDansListesOuvertes = true;
                break;
            }
        }
        return estDansListesOuvertes;
    }

    /**
     * Renvoie la liste des sessions fermees
     * 
     * @return true si la session est presente dans la liste des sessions
     *         fermees
     */
    public boolean testListesFermees() {
        boolean estDansListesFermees = false;
        List<SessionGenerique> models = new ArrayList<>();
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( getBaseUri() );
        List<SessionGenerique> all = target.path( "session/listeFermees" ).request()
                .get( new GenericType<List<SessionGenerique>>() {
                } );
        for ( SessionGenerique s : all ) {
            if ( s.getId() == idSessionDeTest ) {
                estDansListesFermees = true;
                break;
            }
        }
        return estDansListesFermees;
    }

    /**
     * Renvoie le status de la fermeture d'une session par un Gestionnaire
     * 
     * @param idSession
     *            l'id de la session a fermer
     * @return status de la reponse
     */
    public int testFermerCandidatureGestionnaire() {
        int resultat;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( getBaseUri() );
        resultat = target.path( "session/" + uuidGestionnaire + "/fermerCandidature" )
                .queryParam( "Session", idSessionDeTest )
                .request()
                .get().getStatus();

        return resultat;
    }

    /**
     * Renvoie le status de la fermeture d'une session par un Gestionnaire
     * 
     * @param idSession
     *            l'id de la session a fermer
     * @return status de la reponse
     */
    public int testFermerCandidatureAgent() {
        int resultat;
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( getBaseUri() );
        resultat = target.path( "session/" + uuidAgent + "/fermerCandidature" )
                .queryParam( "Session", idSessionDeTest )
                .request()
                .get().getStatus();

        return resultat;
    }

    /* FONCTIONS COMPLEMENTAIRES */
    public cci.caos.beans.AgentConnection getIdentification( String user, String pw ) {
        Client client = null;
        try {
            // Connexion au serveur REST
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic( user, pw );
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.register( feature );

            client = ClientBuilder.newClient( clientConfig );
            WebTarget target = client.target( getBaseUri() );

            Response response = target.path( "agent/connexion" ).request().get();
            System.out.println( response.getStatus() );

            if ( response.getStatus() != 200 ) {
                return null;
            } else {
                AgentConnection agentConnection = response.readEntity( AgentConnection.class );
                return agentConnection;
            }
        } catch ( RuntimeException e ) {
            e.printStackTrace();
            return null;
        }
    }

    private URI getBaseUri() {
        return UriBuilder.fromUri( WS_URI ).build();
    }
}