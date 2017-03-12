package cci.caos.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cci.caos.dao.exception.DAOExceptionConfiguration;

public class CaosConnection {

    private static final String PROPERTY_URL             = "jdbc:mysql://mysql.istic.univ-rennes1.fr:3306/base_10000334";
    private static final String PROPERTY_DRIVER          = "com.mysql.jdbc.Driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "user_10000334";
    private static final String PROPERTY_MOT_DE_PASSE    = "Lespert";
    private static Connection   connect;

    // Constructeur prive
    /**
     * Cree une connexion a la base de donnees
     */
    private CaosConnection() {
        try {
            Class.forName( PROPERTY_DRIVER );
            connect = DriverManager.getConnection( PROPERTY_URL, PROPERTY_NOM_UTILISATEUR, PROPERTY_MOT_DE_PASSE );
        } catch ( ClassNotFoundException e ) {
            throw new DAOExceptionConfiguration( "Le driver est introuvable dans le classpath.", e );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Recupere une connexion a la base de donnees
     * 
     * @return une connexion à la base de donnees
     */
    public static Connection getInstance() {
        if ( connect == null ) {
            new CaosConnection();
        }
        return connect;
    }
}