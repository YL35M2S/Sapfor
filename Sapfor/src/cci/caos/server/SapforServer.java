package cci.caos.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cci.caos.repository.Session;

public class SapforServer {

    Map<Integer, Session>       sessions;
    private static SapforServer session;

    public SapforServer() {
        sessions = new HashMap<Integer, Session>();
        sessions.put( 1, new Session( 1, "Test1", new Date( "06/02/2017" ), new Date( "10/02/2017" ), true ) );
    }

    public static SapforServer getSession() {
        if ( session == null ) {
            session = new SapforServer();
        }
        return session;
    }

    public Session getSessionById( int id ) {
        Session res = new Session();
        for ( Map.Entry<Integer, Session> entry : sessions.entrySet() ) {
            if ( (int) ( entry.getKey() ) == id ) {
                res.setId( entry.getValue().getId() );
                res.setNom( entry.getValue().getNom() );
                res.setDateFin( entry.getValue().getDateFin() );
                res.setDateDebut( entry.getValue().getDateDebut() );
            }
        }
        return res;
    }
}
