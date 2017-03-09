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
import cci.caos.beans.CandidatureGenerique;
import cci.caos.beans.SessionGenerique;
import cci.caos.dao.AgentDao;
import cci.caos.dao.CandidatureDao;
import cci.caos.dao.SessionDao;
import cci.caos.dao.factory.AbstractDAOFactory;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

public class TestAT {

    private final String WS_URI          = "http://localhost:8080/Sapfor/rest";
    private int			 idAgent = 2;
    private String       uuidAgent;
    private String       uuidGestionnaire;
    private int          idSessionDeTest = 1;
    private int 		 idSessionDeTestCandidature = 2;
    private int [] 		 tabIdSessionsAccessibles = {2,3,4};
    private boolean      etatInitialSession;
    private boolean  	 etatInitialCandidature;
    private int 		 statutAgentCandidature;
    private boolean 	 roleAgentCandidature;
    
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
        
        // Sauvegarde de la présence et de l'etat de la candidature de test sur le serveur
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        etatInitialCandidature = candidatureDao.existe(idAgent, idSessionDeTestCandidature);
        
       // Sauvegarde de la candidature et retrait (si besoin) de la DAO 		
        if (etatInitialCandidature) {
        	AgentDao agentDao = adf.getAgentDao();
            Candidature candidature = candidatureDao.trouver(agentDao.trouver(idAgent), sessionDao.trouver(idSessionDeTestCandidature));
            statutAgentCandidature = candidature.getStatutCandidature();
            roleAgentCandidature = candidature.isEstFormateur();
        	candidatureDao.supprimerCandidature(idAgent, idSessionDeTestCandidature);
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

    @Test
    public void testAccessiblesListeCandidatureAjoutRetraitCandidature() {
        // Test de la presence de toutes les sessions accessibles par l'agent
        Assert.assertEquals( true, testListesAccessibles() );

        // Test du depot de candidature
        Assert.assertEquals(200, testDepotCandidature());
        
        // Test de la présence de la candidature dans la DAO
        Assert.assertEquals(true, testPresenceCandidature());
        
        // Test de la présence de la candidature
        Assert.assertEquals(true, testListeCandidatures());
        
        // Test du retrait de candidature
        Assert.assertEquals(200, testRetirerCandidature());
        
        // Test de l'absence de la candidature dans la DAO
        Assert.assertEquals(true, testAbsenceCandidature());
    }
    
    @After
    public void resetTest() {
    	AbstractDAOFactory adf = AbstractDAOFactory.getFactory( SapforServer.typeDao );
        if ( etatInitialSession ) {
            // Retour a l'etat initial de la session de test sur le serveur
            SessionDao sessionDao = adf.getSessionDao();
            Session sessionDeTest = sessionDao.trouver( idSessionDeTest );
            sessionDeTest.setOuverteInscription( etatInitialSession );
        }
        if (etatInitialCandidature) {
        	CandidatureDao candidatureDao = adf.getCandidatureDao();
        	AgentDao agentDao = adf.getAgentDao();
        	SessionDao sessionDao = adf.getSessionDao();
        	Candidature candidature = new Candidature(agentDao.trouver(idAgent),statutAgentCandidature,
        			roleAgentCandidature,sessionDao.trouver(idSessionDeTestCandidature));
        	candidatureDao.creer(candidature);
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
    
    /**
     * Renvoie le statut de la présence de toutes les sessions accessibles par l'agent
     * @return true si toutes les sessions accessibles par l'agent sont dans la reponse HTTP
     */
	public boolean testListesAccessibles() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target( getBaseUri() );
		List<CandidatureGenerique> listeSessionsAccessibles = target.path( "session/"+uuidAgent+"/accessible").request().get( new GenericType<List<CandidatureGenerique>>() {});		
		if (listeSessionsAccessibles.size()==tabIdSessionsAccessibles.length) {
			for (int i=0; i<tabIdSessionsAccessibles.length; i++) {
				if (listeSessionsAccessibles.get(i).getId_Session()!=tabIdSessionsAccessibles[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
    
	public boolean testListeCandidatures() {
		boolean estDansListeCandidatures = false;
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target( getBaseUri() );
		List<CandidatureGenerique> listeCandidature = target.path( "session/"+uuidAgent).request().get( new GenericType<List<CandidatureGenerique>>() {});	
		
		for ( CandidatureGenerique c : listeCandidature ) {
	            if ( c.getId_Agent() == idAgent && c.getId_Session()==idSessionDeTestCandidature) {
	            	estDansListeCandidatures = true;
	                break;
	            }
		}
		return estDansListeCandidatures;
	}
	
    /**
     * Renvoie le statut du depot de la candidature par un agent
     * @return statut de la reponse HTTP
     */
    public int testDepotCandidature() {
    	int resultat;
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target( getBaseUri() );
		resultat = target.path( "session/"+uuidAgent+"/candidater")
				.queryParam("Session", idSessionDeTestCandidature)
				.queryParam("Formateur", "false")
				.request()
				.get().getStatus();
		return resultat;
		
    }
    
    /**
     * Renvoie le statut du retrait de la candidature par un agent
     * @return statut de la reponse HTTP
     */
	public int testRetirerCandidature() {
		int resultat;
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target( getBaseUri() );
		resultat = target.path( "session/"+uuidAgent+"/retirerCandidature")
				.queryParam("Session", idSessionDeTestCandidature)
				.request()
				.get().getStatus();
		return resultat;
	}
    
	/**
	 * Test la présence de la candidature dans la DAO
	 * @return true si la candidature est présente dans la DAO
	 */
	public boolean testPresenceCandidature() {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory( SapforServer.typeDao );
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        return candidatureDao.existe(idAgent, idSessionDeTestCandidature);
	}
	
	/**
	 * Test l'absence de la candidature dans la DAO
	 * @return true si la candidature est absente dans la DAO
	 */
	public boolean testAbsenceCandidature() {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory( SapforServer.typeDao );
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        return !candidatureDao.existe(idAgent, idSessionDeTestCandidature);
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