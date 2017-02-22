package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cci.caos.repository.Agent;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;

public class CandidatureDaoImpl extends Dao implements CandidatureDao {
    private static final String SQL_SELECT_PAR_CANDIDAT_SESSION = "SELECT * FROM Candidature WHERE idAgent = ? and idSession = ?";
    private static final String SQL_INSERT_CANDIDATURE          = "INSERT INTO Candidature (agent, statutCandidature, estFormateur, session) VALUES(?, ?, ?, ?);";

    /* Constructeur */
    public CandidatureDaoImpl( Connection conn ) {
        super( conn );
    }

    /* Implémentation des méthodes */

    /**
     * Creation d'une candidature dans la Base de Donnees DAO
     * 
     * @param candidature
     *            La candidature a sauvegarder
     * @return l'id créé
     */
    @Override
    public int creer( Candidature candidature ) throws DAOException {
        int statut;
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_INSERT_CANDIDATURE, Statement.RETURN_GENERATED_KEYS );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, candidature.getAgent().getId() );
            preparedStatement.setInt( 2, candidature.getStatutCandidature() );
            preparedStatement.setBoolean( 3, candidature.isEstFormateur() );
            preparedStatement.setInt( 4, candidature.getSession().getId() );

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
     * Recherche d'une candidatture dans la Base de Donnees DAO à partir de son
     * id
     * 
     * @param id
     *            L'id de la candidature a rechercher
     * @return la candidature recherchée
     */
    @Override
    public Candidature trouver( Agent agent, Session session ) throws DAOException {
        Candidature candidature = null;
        ResultSet resultSet = null;

        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_PAR_CANDIDAT_SESSION );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, agent.getId() );
            preparedStatement.setInt( 2, session.getId() );

            resultSet = preparedStatement.executeQuery();

            if ( resultSet.next() ) {
                candidature = new Candidature();
                candidature.setEstFormateur( resultSet.getBoolean( "estFormateur" ) );
                candidature.setStatutCandidature( resultSet.getInt( "statutCandidature" ) );
                candidature.setAgent( agent );
                candidature.setSession( session );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
        return candidature;
    }

}
