package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cci.caos.repository.Stage;

public class StageDaoImpl extends Dao implements StageDao {
    private static final String SQL_INSERT_STAGE = "INSERT INTO Stage (nomStage) VALUES(?);";
    private static final String SQL_EXISTE_STAGE = "SELECT * FROM Stage WHERE nomStage = ?";

    public StageDaoImpl( Connection conn ) {
        super( conn );
    }

    /**
     * Creation d'un agent dans la Base de Donnees DAO
     * 
     * @param agent
     *            L'agent a sauvegarder
     * @return l'Id de l'agent créé
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
     * Recherche si un stage identifié par son id existe dans la Base de Donnees
     * DAO
     * 
     * @param id
     *            L'id du stage à rechercher
     * @return Vrai si il existe sinon false
     */
    @Override
    public boolean existe( int idStage ) throws DAOException {
        boolean existe = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_EXISTE_STAGE );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, idStage );

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
}
