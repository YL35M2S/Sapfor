package cci.caos.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import cci.caos.repository.Agent;
import cci.caos.repository.Aptitude;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;
import cci.caos.repository.Stage;
import cci.caos.repository.Uv;

public class SapforServer {

    private Map<String, Agent>     connexions;
    private Map<Integer, Session>  sessions;
    private Map<Integer, Uv>       uvs;
    private Map<Integer, Agent>    agents;
    private Map<Integer, Stage>    stages;
    private Map<Integer, Aptitude> aptitudes;
    private Map<Integer, Candidature> candidatures;

    private Map<String, Agent>     uuid_agents;
    private static SapforServer    sessionServer;

    public SapforServer() {
        connexions = new HashMap<String, Agent>();
        sessions = new HashMap<Integer, Session>();
        uvs = new HashMap<Integer, Uv>();
        agents = new HashMap<Integer, Agent>();
        stages = new HashMap<Integer, Stage>();
        aptitudes = new HashMap<Integer, Aptitude>();
        candidatures = new HashMap<Integer, Candidature>();
        
        // Creation des UV
        Uv uv1 = new Uv( 1, "UV_INC1", 5, 3, 12, "Rennes" );
        Uv uv2 = new Uv( 2, "UV_FDF1", 10, 3, 12, "Rennes" );
        Uv uv3 = new Uv( 3, "UV_SAR1", 5, 3, 12, "Rennes" );
        Uv uv4 = new Uv( 4, "UV_WJK2", 5, 3, 12, "Rennes" );
        Uv uv5 = new Uv( 5, "UV_WJK1", 5, 3, 12, "Rennes" );
        Uv uv6 = new Uv( 6, "UV_AJH", 5, 3, 12, "Rennes" );
        Uv uv7 = new Uv( 7, "UV_FORM", 5, 3, 12, "Rennes" ); // Uv requise pour être formateur
        Uv uv8 = new Uv( 8, "UV_FORM", 5, 3, 12, "Rennes" ); // Uv requise pour être formateur
        
        uvs.put( 1, uv1 );
        uvs.put( 2, uv2 );
        uvs.put( 3, uv3 );
        uvs.put( 4, uv4 );
        uvs.put( 5, uv5 );
        uvs.put( 6, uv6 );
        uvs.put( 7, uv7 );
        uvs.put( 8, uv8 );
        
        // Avec prérequis
        uv1.getListePrerequis().add( uv2 );
        uv1.getListePrerequis().add( uv3 );
        uv2.getListePrerequis().add( uv3 );
        uv3.getListePrerequis().add( uv6 );
        uv4.getListePrerequis().add( uv5 );

        // Creation d'agents
        Agent a1 = new Agent( 1, "ATREUILLIER", "mdp", "19041975", true );
        Agent a2 = new Agent( 2, "LTREUILLIER", "mdp", "15122010", false );
        Agent a3 = new Agent( 3, "NTREUILLIER", "mdp", "09102013", false );
        Agent a4 = new Agent( 4, "FDESCAVES", "mdp", "06091991", false );
        Agent a5 = new Agent( 5, "MDESCAVES", "max", "06091990", false );
        
        a1.ajouterUv( uv6 );
        a2.ajouterUv( uv3 );
        a3.ajouterUv( uv2 );
        a3.ajouterUv( uv3 );
        agents.put( 1, a1 );
        agents.put( 2, a2 );
        agents.put( 3, a3 );
        agents.put( 4, a4 );
        agents.put( 5, a5 );
        
        connexions.put( "19041975", a1 );
        connexions.put( "15122010", a2 );
        connexions.put( "09102013", a3 );
        connexions.put( "06091991", a4 );

        // Creation de session
        Session s1 = null;
        Session s2 = null;
        Session s3 = null;

        try {
            s1 = new Session( 1, "INC1", new SimpleDateFormat( "dd/MM/yyyy" ).parse( "06/02/2017" ),
                    new SimpleDateFormat( "dd/MM/yyyy" ).parse( "10/02/2017" ), true, uv1 );
            s2 = new Session( 2, "FDF1", new SimpleDateFormat( "dd/MM/yyyy" ).parse( "06/02/2017" ),
                    new SimpleDateFormat( "dd/MM/yyyy" ).parse( "17/02/2017" ), true, uv2 );
            s3 = new Session( 3, "SAR1", new SimpleDateFormat( "dd/MM/yyyy" ).parse( "30/01/2017" ),
                    new SimpleDateFormat( "dd/MM/yyyy" ).parse( "3/02/2017" ), true, uv3 );

        } catch ( ParseException e ) {
            e.printStackTrace();
        }
        
        sessions.put( 1, s1 );
        sessions.put( 2, s2 );
        sessions.put( 3, s3 );

        candidatures.put(1, new Candidature( a2, 2, false, s1 ));
        candidatures.put(2, new Candidature( a3, 2, false, s1 ));
        
    }

    public static SapforServer getSessionServer() {
        if ( sessionServer == null ) {
            sessionServer = new SapforServer();
        }
        return sessionServer;
    }

    public Session getSessionById( int id ) {
        Session s = null;
        for ( Map.Entry<Integer, Session> entry : sessions.entrySet() ) {
            if ( (int) ( entry.getKey() ) == id ) {
                s = entry.getValue();
            }
        }
        return s;
    }

    public Uv getUvById( int id ) {
        Uv u = null;
        for ( Map.Entry<Integer, Uv> entry : uvs.entrySet() ) {
            if ( (int) ( entry.getKey() ) == id ) {
                u = entry.getValue();
            }
        }
        return u;
    }

    public boolean isConnectedByUUID( String uuid ) {
        boolean estConnecte = false;
        for ( Map.Entry<String, Agent> entry : connexions.entrySet() ) {
            if ( entry.getKey().compareTo( uuid ) == 0 ) {
                estConnecte = true;
                break;
            }
        }
        return estConnecte;
    }

    public Agent getAgentByUUID( String uuid ) {
        Agent a = null;
        for ( Map.Entry<String, Agent> entry : connexions.entrySet() ) {
            if ( entry.getKey().compareTo( uuid ) == 0 ) {
                a = entry.getValue();
            }
        }
        return a;
    }

    public Agent getAgentById( int id ) {
        Agent a = null;
        for ( Map.Entry<Integer, Agent> entry : agents.entrySet() ) {
            if ( entry.getKey() == id ) {
                a = entry.getValue();
            }
        }
        return a;
    }
    
    public List<Uv> getListeUvFormateur() {
    	List<Uv> ListeUvFormateur = new ArrayList<Uv>();
    	ListeUvFormateur.add(uvs.get(7));
    	ListeUvFormateur.add(uvs.get(8));
    	return ListeUvFormateur;
    }
    
    public List<Candidature> getSessionsAccessibles( String uuid ) {
        Agent agent = connexions.get( uuid );
        List<Candidature> CandidaturesSessionsAccessibles = new ArrayList<Candidature>();
    	List<Uv> listeUvRequiseFormateur = getListeUvFormateur();
    	
        // Pour chaque session existante sur le serveur
        for ( Integer mapKey : sessions.keySet() ) {
        	List<Uv> listeUvRequiseStagiaire = sessions.get( mapKey ).getUv().getListePrerequis();
            // nombre d'UV requises en tant que Candidat pour cette session
            int nombreUvRequisesStagiaire = listeUvRequiseStagiaire.size();
            // futur nombre de ces UV en tant que Candidat possédées par l'agent
            int nombreUvPossedeStagiaire = 0;
            
            int nombreUvRequiseFormateur = listeUvRequiseFormateur.size();
            int nombreUvPossedeFormateur = 0;
            boolean AgentPossedeUvSession = false;
        	
            Iterator<Uv> itListeUvRequisesStagiaire = listeUvRequiseStagiaire.iterator();
            Iterator<Uv> itListeUvRequisesFormateur = listeUvRequiseFormateur.iterator();
            
            // Regarde si l'agent a les UV nécessaires pour être stagiaire
            while (itListeUvRequisesStagiaire.hasNext()) {
            	Uv UvRequiseStagiaire = itListeUvRequisesStagiaire.next();
            	Iterator<Uv> itListeUvAgent = agent.getListeUV().iterator();
            	
            	while (itListeUvAgent.hasNext()) {
            		Uv UvAgent = itListeUvAgent.next();
            		
            		if ( UvRequiseStagiaire.getNom().compareTo( UvAgent.getNom() ) == 0 ) {
            			nombreUvPossedeStagiaire++; // Il possède l'UV
                        break;
                    }
            	}
            }
            // Regarde si l'agent a les UV nécessaires pour être formateur
            while (itListeUvRequisesFormateur.hasNext()) {
            	Uv UvRequiseFormateur = itListeUvRequisesFormateur.next();
            	Iterator<Uv> itListeUvAgent = agent.getListeUV().iterator();
            	while (itListeUvAgent.hasNext()) {
            		Uv UvAgent = itListeUvAgent.next();
            		
            		if (UvAgent.getNom().compareTo(sessions.get( mapKey ).getUv().getNom())==0) {
            			AgentPossedeUvSession = true;
            		}
            		
            		if (UvRequiseFormateur.getNom().compareTo(UvAgent.getNom())==0) {
            			nombreUvPossedeFormateur++;
            		}
            	}
            }
            	
            // Ajout candidature en tant que stagiaire
            if ( nombreUvRequisesStagiaire == nombreUvPossedeStagiaire && !AgentPossedeUvSession  ) { 
            	Candidature candidature = new Candidature(agent, -2, false, sessions.get( mapKey ));
            	CandidaturesSessionsAccessibles.add(candidature);
            }
            
            // Ajout candidature en tant que formateur
            if ( nombreUvRequiseFormateur == nombreUvPossedeFormateur & AgentPossedeUvSession ) {
            	Candidature candidature = new Candidature(agent, -2, true, sessions.get( mapKey ));
            	CandidaturesSessionsAccessibles.add(candidature);
            }
        }
        return CandidaturesSessionsAccessibles;
    }
    
    public List<Session> getListeSessionsFermees(){
    	List<Session> listeFermees= new ArrayList<Session>();
    	for (Map.Entry<Integer, Session> entry : sessions.entrySet()){
    		if(entry.getValue().isOuverteInscription()==false){
    			listeFermees.add(entry.getValue());
    		}
    	}
    	return listeFermees;
    } 
    
	public List<Session> getListeSessionsOuvertes(){
    	List<Session> listeOuvertes= new ArrayList<Session>();
    	for (Map.Entry<Integer, Session> entry : sessions.entrySet()){
    		if(entry.getValue().isOuverteInscription()==true){
    			listeOuvertes.add(entry.getValue());
    		}
    	}
    	return listeOuvertes;
    } 
	
	public String getConnexionAgent(String matricule, String password) {
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm( "SHA-256" );
		passwordEncryptor.setPlainDigest( true );
		
		for (Map.Entry<Integer, Agent> entry : agents.entrySet()){
			if (entry.getValue().getMatricule().compareTo(matricule)==0) {
				if (passwordEncryptor.checkPassword(password, entry.getValue().getMdp())) {
					String uuid = UUID.randomUUID().toString();
					connexions.put(uuid, entry.getValue());
					return uuid; // Si la connexion est réussie
				}
			}
    	}
		return null; // Si la connexion a échouée
    }
	
	public List<Candidature> getListeCandidatures(int idSession) {
		List<Candidature> listeCandidatures = new ArrayList<Candidature>();
		for (Map.Entry<Integer, Candidature> entry : candidatures.entrySet()){
			if(entry.getValue().getSession().getId()==idSession) {
				listeCandidatures.add(entry.getValue());
			}
		}
		return listeCandidatures;
	}
	
	/* @parameter un uuid  
	 * 
	 * @return la liste des sessions dans lesquelles l'agent à candidater
	 * 
	 * Attention Agent compareTo(a) compare uniquement les id des agents. 
	 * */
	public List<Session> getListeSession(String uuid){
		List<Session> listeSessionsCandidat= new ArrayList<Session>();
		Agent agentCherche = getAgentByUUID(uuid);
		
		for (Map.Entry<Integer, Candidature> entry : candidatures.entrySet()){
			if (entry.getValue().getAgent().compareTo(agentCherche)==0) {
				listeSessionsCandidat.add(entry.getValue().getSession());
			}
		}
		return listeSessionsCandidat;
	}
	
	public boolean modifierListeCandidats(int idSession, List<Candidature> listeCandidature) {
		Iterator<Candidature> itCandidature = listeCandidature.iterator();
		
		for (Map.Entry<Integer, Candidature> entry : candidatures.entrySet()){
			while (itCandidature.hasNext()) {
				Candidature CandidatureCourante = itCandidature.next();
				if (CandidatureCourante.getAgent().compareTo(entry.getValue().getAgent())==0 && 
						CandidatureCourante.getSession().getId()==entry.getValue().getSession().getId()) {
					candidatures.put(entry.getKey(), CandidatureCourante); // Remplacement de la candidature
				}
			}	
		}
		// Temporaire
		return true;
	}
	
	public boolean retirerCandidature(int idAgent, int idSession) {
		for (Map.Entry<Integer, Candidature> entry : candidatures.entrySet()){
			if (entry.getValue().getAgent().getId()==idAgent) {
				if (entry.getValue().getSession().getId()==idSession) {
					candidatures.remove(entry.getKey());
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean deposerCandidature(int idAgent, int idSession, boolean estFormateur) {
		boolean alreadyExist = false;
		for (Map.Entry<Integer, Candidature> entry : candidatures.entrySet()){
			if (entry.getValue().getAgent().getId()==idAgent) {
				if (entry.getValue().getSession().getId()==idSession) {
					alreadyExist = true;
				}
			}
		}
		if (!alreadyExist) {
			int cle = ThreadLocalRandom.current().nextInt(1, 100000 + 1);
			// Assure l'unicité de la clé dans la hashMap
			while(candidatures.containsKey(cle)) {
				cle = ThreadLocalRandom.current().nextInt(1, 100000 + 1);
			}
			candidatures.put(cle, new Candidature( getAgentById(idAgent), -2, estFormateur ,getSessionById(idSession) ));
			return true;
		} else {
			return false;
		}
	}
}
