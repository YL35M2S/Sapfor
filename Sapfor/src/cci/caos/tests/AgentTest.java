package cci.caos.tests;

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

public class AgentTest {
   
	@Test
	// Resultat Attendu: Status OK (200)
    public void testConnexionOK() {
    	Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080" ).path( "Sapfor/rest" );    

        MultivaluedMap<String, String> formConnexion = new MultivaluedHashMap<String, String>();
        formConnexion.add("Matricule", "06091990" );
        formConnexion.add("Password", "max" );
        Response response = target.path( "agent").path("connexion").request().post(Entity.form(formConnexion));
          
        Assert.assertEquals(
        		200,
        		response.getStatus());
    }
	
	@Test
	// Resultat Attendu: Status FORBIDDEN (403)
    public void testConnexionNOK() {
    	Client client = ClientBuilder.newClient();
        WebTarget target = client.target( "http://localhost:8080" ).path( "Sapfor/rest" );    

        MultivaluedMap<String, String> formConnexion = new MultivaluedHashMap<String, String>();
        formConnexion.add("Matricule", "06091990" );
        formConnexion.add("Password","mauvaisMotDePasse");
        Response response = target.path( "agent").path("connexion").request().post(Entity.form(formConnexion));

        Assert.assertEquals(
        		403,
        		response.getStatus());
    }

}
