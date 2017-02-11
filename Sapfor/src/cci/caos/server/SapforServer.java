package cci.caos.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cci.caos.repository.Agent;
import cci.caos.repository.Session;
import cci.caos.repository.Uv;

public class SapforServer {

    Map<Integer, Session>       sessions;
    private Map<String, Agent> uuid_agents;
    private static SapforServer sessionServer;
    
    public SapforServer() {
        sessions = new HashMap<Integer, Session>();
        sessions.put( 1, new Session( 1, "Test1", new Date( "06/02/2017" ), new Date( "10/02/2017" ), true ) );
        sessions.put( 2, new Session( 2, "FDF1", new Date( "06/02/2017" ), new Date( "17/02/2017" ), true ) );
        sessions.put( 3, new Session( 3, "SAP1", new Date( "30/01/2017" ), new Date( "3/02/2017" ), true ) );  
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
 
    public List<Session> getSessionsAccessibles(String uuid) {
    	/*
    	 * Pour l'instant on récupère les sessions accessibles en tant que candidat
    	 * A rajouter: les sessions accessibles en tant que formateur (posséder l'UV cette session + liste d'UV requises pour être formateur)
    	 */
    	Agent agent = uuid_agents.get(uuid);
    	List<Session> SessionsAccessibles = new ArrayList<Session>();
    	
    	for (Integer mapKey : sessions.keySet()) { // Pour chaque session existante
    		List<Uv> listeUvPrerequis = sessions.get(mapKey).getUv().getListePrerequis(); // Pour chaque session on récupère les UV requises
    		int nombreUvRequises = listeUvPrerequis.size();  // nombre d'UV requises pour cette session
    		int nombreUvPossede = 0; // futur nombre de ces UV possédées par l'agent
    		Iterator<Uv> it = listeUvPrerequis.iterator();
    		Iterator<Uv> it2 = agent.getListeUV().iterator();
    		
    		while(it.hasNext()) { // Pour chacune des UV requises pour la session
    			Uv UvRequise = it.next();
    			
    			while(it2.hasNext()) { // Pour chacune des UV possédée par l'agent
    				Uv UvAgent = it2.next();
    				
    				if (UvRequise.getNom().compareTo(UvAgent.getNom())==0) {
    					nombreUvPossede++; // Il possède l'UV
    					break;
    				}
    			}
    		}
    		if(nombreUvRequises==nombreUvPossede) { // Si il possède toutes les UV requises
    			SessionsAccessibles.add(sessions.get(mapKey));
    		}
    	}
    	return SessionsAccessibles;
    }
}
