package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cci.caos.repository.Stage;

public class StageDaoImpl extends Dao implements StageDao {
    private static final String SQL_INSERT_STAGE  = "INSERT INTO Stage (nomStage) VALUES(?);";
    private static final String SQL_EXISTE_STAGE  = "SELECT * FROM Stage WHERE idStage = ?";
    private static final String SQL_UPDATE_STAGE  = "UPDATE Stage SET nomStage=?  WHERE idStage= ?";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Stage WHERE idStage = ?";
    
    /**
     * Constructeur
     * @param conn
     */
    public StageDaoImpl( Connection conn ) {
        super( conn );
    }

    /**
     * Creation d'un stage dans la Base de Donnees
     * 
     * @param stage
     *            Le stage a sauvegarder
     * @return l'Id du stage cree
     * @throws DAOException si la creation dans la base de donnees a provoque une Exception
     */
    @Override
    public int creer( Stage stage ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_INSERT_STAGE, Statement.RETURN_GENERATED_KEYS );

            /* Remplissage des champs de la requete */
            preparedStatement.setString( 1, stage.getNom() );

            /* Execution de la requete */
            preparedStatement.executeUpdate();

            /* Recuperation de l'Id créé */
            ResultSet resultat = preparedStatement.getGeneratedKeys();
            resultat.next();
            return resultat.getInt( 1 );
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    /**
     * Recherche si un stage identifie par son id existe dans la Base de Donnees
     * 
     * @param id
     *            L'id du stage a rechercher
     * @return Vrai si il existe sinon false
     * @throws DAOException si la recherche dans la base de donnees a provoque une Exception
     */
    @Override
    public boolean existe( Stage stage ) throws DAOException {
        boolean existe = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_EXISTE_STAGE );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, stage.getId() );

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
     * Mise a jour d'un stage dans la Base de Donnees
     * 
     * @param stage
     *            Le stage a mettre a jour
     *@throws DAOException si la mise a jour dans la base de donnees a provoque une Exception
     */
    @Override
    public void mettreAJour( Stage stage ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_UPDATE_STAGE );

            /* Remplissage des champs de la requete */
            preparedStatement.setString( 1, stage.getNom() );
            preparedStatement.setInt( 2, stage.getId() );

            /* Execution de la requete */
            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
    }

    /**
     * Recherche d'un stage dans la Base de Donnees a partir de son id
     * 
     * @param id
     *            L'id du stage a rechercher
     * @return le stage recherche
     * @throws DAOException si la recherche dans la base de donnees a provoque une Exception
     */
    @Override
    public Stage trouver( int id ) throws DAOException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Stage stage = null;
        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_PAR_ID );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, id );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Creation de l'agent */
            if ( resultSet.next() ) {
                stage = new Stage();
                stage.setId( id );
                stage.setNom( resultSet.getString( "nomStage" ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
        return stage;
    }
}