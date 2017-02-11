package cci.caos.managers;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import cci.caos.repository.Agent;
import cci.caos.repository.Uv;
import cci.caos.server.SapforServer;

@Path( "/agent" )
public class AgentManager {

    @GET
    @Path( "agents" )
    // @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    @Produces( { MediaType.APPLICATION_XML } )
    public Agent listeUv( @QueryParam( "Agent" ) String idAg ) {
        int ida = Integer.parseInt( idAg );
        Agent a = SapforServer.getSessionServer().getAgentById( ida );
        return a;
    }

    @GET
    @Path( "getPrerequis" )
    // @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    @Produces( { MediaType.APPLICATION_XML } )
    public Collection<Uv> listePreRequis( @QueryParam( "Uv" ) String idUv ) {
        int idu = Integer.parseInt( idUv );
        Uv u = SapforServer.getSessionServer().getUvById( idu );
        return u.getListePrerequis();
    }

    @POST
    @Path( "getUv" )
    @Consumes( MediaType.APPLICATION_XML )
    public void updateContentBooksWithJAXBElementXML( JAXBElement<Uv> currentJAXBElement ) {
        Uv current = currentJAXBElement.getValue();
        System.out.println( "Nom : " + current.getNom() + ", Durée: " + current.getDuree() );
    }
}
