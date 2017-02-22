package cci.caos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CaosConnection {

    private static final String PROPERTY_URL             = "jdbc:mysql://mysql.istic.univ-rennes1.fr:3306/base_10000334";
    private static final String PROPERTY_DRIVER          = "com.mysql.jdbc.Driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "user_10000334";
    private static final String PROPERTY_MOT_DE_PASSE    = "Lespert";
    private static Connection   connect;

    public static Connection getInstance() {
        if ( connect == null ) {
            try {
                Class.forName( PROPERTY_DRIVER );
                connect = DriverManager.getConnection( PROPERTY_URL, PROPERTY_NOM_UTILISATEUR, PROPERTY_MOT_DE_PASSE );
            } catch ( ClassNotFoundException e ) {
                throw new DAOExceptionConfiguration( "Le driver est introuvable dans le classpath.", e );
            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }
        return connect;
    }
}