package cci.caos.managers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

@Path( "/session" )
public class SessionManager {

    /*
     * @GET
     * 
     * @Path( "{uuid}/fermerCandidature" )
     * 
     * @Produces( MediaType.TEXT_HTML ) // @Produces( MediaType.TEXT_PLAIN )
     * public String fermerCandidature( @PathParam( "uuid" ) String
     * id, @QueryParam( "Session" ) String idSession ) { int ids =
     * Integer.parseInt( idSession );
     * 
     * return "<html> " + "<title>" + "Hello Jersey" + "</title>" +
     * "<body><h1>Session</h1>" + "Nom de la Session: " +
     * SapforServer.getSession().getSessionById( ids ).presenteToi() + "</body>"
     * + "</html> "; }
     */

    @GET
    @Path( "sessions" )
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Session listeSession( @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
        Session s = SapforServer.getSessionServer().getSessionById( ids );
        return s;
    }

    @GET
    @Path( "{uuid}/fermerCandidature" )
    public boolean fermerCandidature( @PathParam( "uuid" ) String id, @QueryParam( "Session" ) String idSession ) {
        int ids = Integer.parseInt( idSession );
        return SapforServer.getSessionServer().getSessionById( ids ).fermerCandidature();
    }

    /*
     * @GET
     * 
     * @Produces( MediaType.TEXT_HTML ) public String sayHtmlHello() { return
     * "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" +
     * "Hello Jersey" + "</body></h1>" + "</html> "; }
     */

    /*
     * @GET
     * 
     * @Produces( MediaType.TEXT_PLAIN ) public String sayPlainTextHello() {
     * return "Hello Jersey"; }
     * 
     * // This method is called if XML is request
     * 
     * @GET
     * 
     * @Produces( MediaType.TEXT_XML ) public String sayXMLHello() { return
     * "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>"; }
     * 
     * // This method is called if HTML is request
     * 
     * @GET
     * 
     * @Produces( MediaType.TEXT_HTML ) public String sayHtmlHello() { return
     * "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" +
     * "Hello Jersey" + "</body></h1>" + "</html> "; }
     */
}
