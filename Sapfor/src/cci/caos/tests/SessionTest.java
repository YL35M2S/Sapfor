package cci.caos.tests;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import cci.caos.repository.Session;
import cci.caos.repository.Uv;
import cci.caos.server.SapforServer;

public class SessionTest {

    @Test
    public void testFermerCandidatureOK() throws IOException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080" ).path( "Sapfor/rest" );

        Assert.assertEquals(
                200,
                target.path( "session" ).path( "19041975/fermerCandidature" )
                        .queryParam( "Session", 1 )
                        .request()
                        .get().getStatus() );
    }

    public void testFermerCandidatureNOK() throws IOException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080" ).path( "Sapfor/rest" );

        Assert.assertEquals(
                403,
                target.path( "session" ).path( "28051982/fermerCandidature" )
                        .queryParam( "Session", 1 )
                        .request()
                        .get().getStatus() );
    }

    @Test
    public void testGetSession() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080" ).path( "Sapfor/rest" );

        Uv uv1 = new Uv( 1, "UV_INC1", 5, 3, 12, "Rennes" );
        Uv uv2 = new Uv( 2, "UV_FDF1", 10, 3, 12, "Rennes" );
        Uv uv3 = new Uv( 3, "UV_SAR1", 5, 3, 12, "Rennes" );
        // Avec pr�requis
        uv1.getListePrerequis().add( uv2 );
        uv1.getListePrerequis().add( uv3 );

        Session s1 = null;
        try {
            s1 = new Session( 1, "INC1", new SimpleDateFormat( "dd/MM/yyyy" ).parse( "06/02/2017" ),
                    new SimpleDateFormat( "dd/MM/yyyy" ).parse( "10/02/2017" ), true, uv1 );
        } catch ( ParseException e ) {
            e.printStackTrace();
        }

        Assert.assertEquals(
                200, target.path( "session" ).path( "sessions" ).queryParam( "Session", 1 )
                        .request( MediaType.APPLICATION_XML )
                        .get().getStatus() );
    }

    @Test
    public void testCreateSession() throws IOException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080" ).path( "Sapfor/rest" );

        Session current = new Session();
        current.setId( 1 );
        current.setNom( "INC2" );
        current.setOuverteInscription( true );
        try {
            current.setDateDebut( new SimpleDateFormat( "dd/MM/yyyy" ).parse( "13/02/2017" ) );
            current.setDateFin( new SimpleDateFormat( "dd/MM/yyyy" ).parse( "17/02/2017" ) );
        } catch ( ParseException e ) {
            e.printStackTrace();
        }
        Assert.assertEquals(
                200,
                target.path( "session" ).path( "createSession" )
                        .request( MediaType.APPLICATION_XML )
                        .post( Entity.xml( current ) ).getStatus() );
    }
    
    @Test
    // Resultat Attendu: Liste avec la session d'id 3
    public void testGetSessionsAccessiblesOK() throws IOException {
    	Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080" ).path( "Sapfor/rest" );   

        SapforServer server = SapforServer.getSessionServer();
        
        List<Session> liste_session_attendue = new ArrayList<Session>();
        liste_session_attendue.add(server.getSessionById(3));
        
        ObjectMapper mapper = new ObjectMapper();
        // Conversion de la liste en String correspondant � un format json
        String liste_session_attendue_json = mapper.writeValueAsString(liste_session_attendue);

        // R�cup�ration et conversion de la liste envoy�e par le serveur (au format json) en String
        Response response = target.path( "session").path("19041975/accessible").request().get();
        String liste_session_accessible_json = response.readEntity(String.class);

        Assert.assertEquals(
        		liste_session_attendue_json,
        		liste_session_accessible_json);
    }
    
    @Test
    // Resultat Attendu: liste vide
    public void testGetSessionsAccessiblesOK_2() throws IOException {
    	Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080" ).path( "Sapfor/rest" );    
        List<Session> liste_session_attendue = new ArrayList<Session>();
        
        ObjectMapper mapper = new ObjectMapper();
        String liste_session_attendue_json = mapper.writeValueAsString(liste_session_attendue);

        Response response = target.path( "session").path("06091991/accessible").request().get();
        String liste_session_accessible_json = response.readEntity(String.class);

        Assert.assertEquals(
        		liste_session_attendue_json,
        		liste_session_accessible_json);
    }
}
