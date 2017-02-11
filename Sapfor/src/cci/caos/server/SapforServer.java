package cci.caos.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    private Map<String, Agent>     uuid_agents;
    private static SapforServer    sessionServer;

    public SapforServer() {
        connexions = new HashMap<String, Agent>();
        sessions = new HashMap<Integer, Session>();
        uvs = new HashMap<Integer, Uv>();
        agents = new HashMap<Integer, Agent>();
        stages = new HashMap<Integer, Stage>();
        aptitudes = new HashMap<Integer, Aptitude>();

        // Creation des UV
        Uv uv1 = new Uv( 1, "UV_INC1", 5, 3, 12, "Rennes" );
        Uv uv2 = new Uv( 2, "UV_FDF1", 10, 3, 12, "Rennes" );
        Uv uv3 = new Uv( 3, "UV_SAR1", 5, 3, 12, "Rennes" );
        // Avec prérequis
        uv1.getListePrerequis().add( uv2 );
        uv1.getListePrerequis().add( uv3 );
        uv2.getListePrerequis().add( uv3 );

        // Creation d'agents
        Agent a1 = new Agent( "ATREUILLIER", "mdp", "19041975", true );
        Agent a2 = new Agent( "LTREUILLIER", "mdp", "15122010", false );
        Agent a3 = new Agent( "NTREUILLIER", "mdp", "09102013", false );
        agents.put( 1, a1 );
        agents.put( 2, a2 );
        agents.put( 3, a3 );

        connexions.put( "19041975", a1 );

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

        uvs.put( 1, uv1 );
        uvs.put( 2, uv2 );
        uvs.put( 3, uv3 );

        s1.getCandidats().add( new Candidature( a2, 2, false ) );
        s1.getCandidats().add( new Candidature( a3, 2, false ) );
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

    public List<Session> getSessionsAccessibles( String uuid ) {
        /*
         * Pour l'instant on récupère les sessions accessibles en tant que
         * candidat A rajouter: les sessions accessibles en tant que formateur
         * (posséder l'UV cette session + liste d'UV requises pour être
         * formateur)
         */
        Agent agent = uuid_agents.get( uuid );
        List<Session> SessionsAccessibles = new ArrayList<Session>();

        for ( Integer mapKey : sessions.keySet() ) { // Pour chaque session
                                                     // existante
            List<Uv> listeUvPrerequis = sessions.get( mapKey ).getUv().getListePrerequis(); // Pour
                                                                                            // chaque
                                                                                            // session
                                                                                            // on
                                                                                            // récupère
                                                                                            // les
                                                                                            // UV
                                                                                            // requises
            int nombreUvRequises = listeUvPrerequis.size(); // nombre d'UV
                                                            // requises pour
                                                            // cette session
            int nombreUvPossede = 0; // futur nombre de ces UV possédées par
                                     // l'agent
            Iterator<Uv> it = listeUvPrerequis.iterator();
            Iterator<Uv> it2 = agent.getListeUV().iterator();

            while ( it.hasNext() ) { // Pour chacune des UV requises pour la
                                     // session
                Uv UvRequise = it.next();

                while ( it2.hasNext() ) { // Pour chacune des UV possédée par
                                          // l'agent
                    Uv UvAgent = it2.next();

                    if ( UvRequise.getNom().compareTo( UvAgent.getNom() ) == 0 ) {
                        nombreUvPossede++; // Il possède l'UV
                        break;
                    }
                }
            }
            if ( nombreUvRequises == nombreUvPossede ) { // Si il possède toutes
                                                         // les UV requises
                SessionsAccessibles.add( sessions.get( mapKey ) );
            }
        }
        return SessionsAccessibles;
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
        boolean res = false;
        for ( Map.Entry<String, Agent> entry : connexions.entrySet() ) {
            if ( entry.getKey().compareTo( uuid ) == 0 ) {
                res = true;
                break;
            }
        }
        return res;
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

}
