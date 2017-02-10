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
        sessions.put( 2, new Session( 2, "FDF1", new Date( "06/02/2017" ), new Date( "17/02/2017" ), true ) );
        sessions.put( 3, new Session( 3, "SAP1", new Date( "30/01/2017" ), new Date( "3/02/2017" ), true ) );
    }

    public static SapforServer getSession() {
        if ( session == null ) {
            session = new SapforServer();
        }
        return session;
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
}
