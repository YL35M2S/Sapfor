package cci.caos.managers;

import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.util.Base64;

import cci.caos.beans.AgentConnection;
import cci.caos.repository.Agent;
import cci.caos.repository.Uv;
import cci.caos.server.SapforServer;

@Path( "/agent" )
public class AgentManager {
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME  = "Basic";
    /**
     * Permet d'obtenir l'agent a partir de l'id de l'agent pour la session actuellement en cours
     * @param idAg
     * @return l'agent correspondant à l'idAg 
     */
    @GET
    @Path( "agents" )
    // @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    @Produces( { MediaType.APPLICATION_XML } )
    public Agent listeUv( @QueryParam( "Agent" ) String idAg ) {
        int ida = Integer.parseInt( idAg );
        Agent a = SapforServer.getSessionServer().getAgentById( ida );
        return a;
    }
    
    /**
     * Recupere la liste des prequis pour une uv a partir de l'id de l'uv
     * @param idUv
     * @return la liste des prerequis d'une uv
     */
    @GET
    @Path( "getPrerequis" )
    // @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    @Produces( { MediaType.APPLICATION_XML } )
    public Collection<Uv> listePreRequis( @QueryParam( "Uv" ) String idUv ) {
        int idu = Integer.parseInt( idUv );
        Uv u = SapforServer.getSessionServer().getUvById( idu );
        return u.getListePrerequis();
    }
    /**
     * verifie les donnees de connection et renvoie une reponse
     * @param request
     * @return ok si les donnees de connection sont correctes sinon non autorise
     */
    @GET
    @Path( "connexion" )
    @Produces( { MediaType.APPLICATION_JSON } )
    public Response connexion( @Context ContainerRequestContext request ) {
        // Get request headers
        final MultivaluedMap<String, String> headers = request.getHeaders();

        // Fetch authorization header
        final List<String> authorization = headers.get( AUTHORIZATION_PROPERTY );

        // Get encoded username and password
        final String encodedUserPassword = authorization.get( 0 ).replaceFirst( AUTHENTICATION_SCHEME + " ", "" );

        // Decode username and password
        String usernameAndPassword = new String( Base64.decode( encodedUserPassword.getBytes() ) );

        // Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer( usernameAndPassword, ":" );
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        // Verifying Username and password
        // System.out.println( username );
        // System.out.println( password );

        SapforServer server = SapforServer.getSessionServer();
        AgentConnection ag = server.getConnexionAgent( username, password );

        if ( ag != null ) {
            return Response.status( Status.OK ).entity( new GenericEntity<AgentConnection>( ag ) {
            } ).build();
        } else {
            return Response.status( Status.UNAUTHORIZED ).build();
        }
    }

    // Sauvegarde au cas ou ...
    /*
     * @POST
     * 
     * @Path( "connexion" )
     * 
     * @Produces( MediaType.APPLICATION_JSON ) public Response
     * connexion(@FormParam( "Matricule" ) String matricule, @FormParam(
     * "Password" ) String password) { SapforServer server =
     * SapforServer.getSessionServer(); String uuid =
     * server.getConnexionAgent(matricule, password); if (uuid!=null) {
     * GenericEntity<String> uuidEntity = new GenericEntity<String>( uuid ){};
     * return Response.status(Status.OK).entity(uuidEntity).build(); } else {
     * return Response.status(Status.FORBIDDEN).build(); } }
     */

}
