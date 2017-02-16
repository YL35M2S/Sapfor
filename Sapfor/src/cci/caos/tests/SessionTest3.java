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
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import cci.caos.repository.Session;
import cci.caos.repository.Uv;
import cci.caos.server.SapforServer;

public class SessionTest3 {   
    
    @Test
    // Resultat Attendu: Liste avec la session d'id 2 et 3
    public void testListeSessionsOuvertes2() throws IOException {
        // Initialisation du client
    	Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080" ).path( "Sapfor/rest" );
    	
        SapforServer server = SapforServer.getSessionServer();
        
    	// Initialisation des sessions pour le test
        List<Session> liste_session_ouvertes_attendues = new ArrayList<Session>();
        target.path( "session" ).path( "19041975/fermerCandidature" ).queryParam( "Session", 1 ).request().get().getStatus();
        liste_session_ouvertes_attendues.add(server.getSessionById(2));
        liste_session_ouvertes_attendues.add(server.getSessionById(3));
       

        // Mise en forme du resultat attendu
        ObjectMapper mapper = new ObjectMapper();
        String liste_session_ouvertes_attendues_json = mapper.writeValueAsString(liste_session_ouvertes_attendues);


        // Test
        Response response = target.path( "session").path("listeOuvertes").request().get();
        Assert.assertEquals(liste_session_ouvertes_attendues_json, response.readEntity(String.class));
    }
    
}
