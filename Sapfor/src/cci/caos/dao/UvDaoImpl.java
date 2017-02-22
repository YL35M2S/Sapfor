package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cci.caos.repository.Uv;
import cci.caos.server.SapforServer;

public class UvDaoImpl extends Dao implements UvDao {
    private static final String SQL_SELECT_PAR_ID             = "SELECT * FROM Uv WHERE idUv = ?";
    private static final String SQL_INSERT_UV                 = "INSERT INTO Uv ( nomUv, duree, nombrePlaceMin, nombrePlaceMax, lieu) VALUES( ?, ?, ?, ?, ?);";
    private static final String SQL_INSERT_UV_PREREQUISES     = "INSERT INTO ListePrerequis ( idUv, idPrerequis ) VALUES(?, ?);";
    private static final String SQL_SELECT_PREREQUISES_PAR_ID = "SELECT idPrerequis FROM ListePrerequis WHERE idUv = ?";
    private static final String SQL_EXISTE_UV                 = "SELECT * FROM Uv WHERE idUv = ?";

    /* Constructeur */
    public UvDaoImpl( Connection conn ) {
        super( conn );
    }

    /* Implémentation des méthodes */

    /**
     * Creation d'une Uv dans la Base de Donnees DAO
     * 
     * @param uv
     *            L'uv a sauvegarder
     * @return l'Id de l'uv créée
     */
    public int creer( Uv uv ) {
        // public int creer( Uv uv ) throws DAOException {
        PreparedStatement preparedStatement;
        int idNewUv = 0;

        try {
            preparedStatement = connect.prepareStatement( SQL_INSERT_UV, Statement.RETURN_GENERATED_KEYS );

            /* Remplissage des champs de la requete */
            preparedStatement.setString( 1, uv.getNom() );
            preparedStatement.setInt( 2, uv.getDuree() );
            preparedStatement.setInt( 3, uv.getNombrePlaceMin() );
            preparedStatement.setInt( 4, uv.getNombrePlaceMax() );
            preparedStatement.setString( 5, uv.getLieu() );

            /* Execution de la requete */
            preparedStatement.executeUpdate();

            /* Recuperation de l'Id créé */
            ResultSet resultat = preparedStatement.getGeneratedKeys();
            resultat.next();
            idNewUv = resultat.getInt( 1 );

            /* Insertion des Uv Prerequises */
            if ( uv.getListePrerequis().size() > 0 ) {
                preparedStatement = connect.prepareStatement( SQL_INSERT_UV_PREREQUISES );
                for ( Uv u : uv.getListePrerequis() ) {
                    preparedStatement.setInt( 1, idNewUv );
                    preparedStatement.setInt( 2, u.getId() );
                    preparedStatement.executeUpdate();
                }
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            // throw new DAOException( e );
        }
        return idNewUv;
    }

    /**
     * Recherche d'une uv dans la Base de Donnees DAO à partir de son id
     * 
     * @param id
     *            L'id de l'uv a rechercher
     * @return l'uv recherchée
     */
    @Override
    public Uv trouver( int idUv ) throws DAOException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Uv uv = null;
        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_PAR_ID );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, idUv );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Creation de l'uv */
            if ( resultSet.next() ) {
                uv = new Uv();
                uv.setId( idUv );
                uv.setNom( resultSet.getString( "nomAgent" ) );
                uv.setDuree( resultSet.getInt( "duree" ) );
                uv.setNombrePlaceMin( resultSet.getInt( "nombrePlaceMin" ) );
                uv.setNombrePlaceMax( resultSet.getInt( "nombrePlaceMax" ) );
                uv.setLieu( resultSet.getString( "lieu" ) );
                uv.setListePrerequis( new ArrayList<Uv>() );
            }

            /* Recherche des Uv prerequises */
            preparedStatement = connect.prepareStatement( SQL_SELECT_PREREQUISES_PAR_ID );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, idUv );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Traitement des Uv prerequises */
            while ( resultSet.next() ) {
                uv.getListePrerequis()
                        .add( SapforServer.getSessionServer().getUvById( resultSet.getInt( "idPrerequis" ) ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
        return uv;
    }

    /**
     * Recherche si une uv existe dans la Base de Donnees DAO
     * 
     * @param uv
     *            L'uv recherchée
     * @return Vrai si elle existe sinon false
     */
    @Override
    public boolean existe( Uv uv ) throws DAOException {
        boolean existe = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_EXISTE_UV );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, uv.getId() );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                existe = true;
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
        return existe;
    }

    /**
     * Mise a jour d'une Uv dans la Base de Donnees DAO
     * 
     * @param uv
     *            L'uv a mettre a jour
     */
    @Override
    public void mettreAJour( Uv uv ) throws DAOException {
        // TODO Auto-generated method stub

    }
}
