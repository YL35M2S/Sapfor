package cci.caos.managers;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cci.caos.repository.Uv;
import cci.caos.server.SapforServer;

@Path( "/uv" )
public class UvManager {
    /**
     * Renvoie une uv selon son id pour une session donnee
     * 
     * @param idUv
     *            l'id de l'UV recherchee
     * @return l'uv cherchee
     */
    @GET
    @Path( "uvs" )
    @Produces( { MediaType.APPLICATION_XML } )
    public Uv listeUv( @QueryParam( "Uv" ) String idUv ) {
        int idu = Integer.parseInt( idUv );
        Uv u = SapforServer.getSessionServer().getUvById( idu );
        return u;
    }

    /**
     * Renvoie la liste des prerequis pour une uv selon son id d'une session
     * donnee
     * 
     * @param idUv
     *            l'id de l'UV pour laquelle on cherche les prerequis
     * @return la liste des prerequis pour l'uv cherchee
     */
    @GET
    @Path( "getPrerequis" )
    @Produces( { MediaType.APPLICATION_XML } )
    public Collection<Uv> listePreRequis( @QueryParam( "Uv" ) String idUv ) {
        int idu = Integer.parseInt( idUv );
        Uv u = SapforServer.getSessionServer().getUvById( idu );
        return u.getListePrerequis();
    }
}
