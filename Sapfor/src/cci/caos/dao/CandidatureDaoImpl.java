package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cci.caos.repository.Agent;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;

public class CandidatureDaoImpl extends Dao implements CandidatureDao {
    private static final String SQL_SELECT_PAR_CANDIDAT_SESSION = "SELECT * FROM Candidature WHERE idAgent = ? and idSession = ?";
    private static final String SQL_INSERT_CANDIDATURE          = "INSERT INTO Candidature (idAgent, statutCandidature, estFormateur, idSession) VALUES(?, ?, ?, ?);";

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
     */
    @Override
    public void creer( Candidature candidature ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_INSERT_CANDIDATURE );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, candidature.getAgent().getId() );
            preparedStatement.setInt( 2, candidature.getStatutCandidature() );
            preparedStatement.setBoolean( 3, candidature.isEstFormateur() );
            preparedStatement.setInt( 4, candidature.getSession().getId() );

            /* Execution de la requete */
            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
    }

    /**
     * Recherche d'une candidature dans la Base de Donnees DAO à partir de
     * l'agent et de la session
     * 
     * @param agent
     *            L'agent concerné par la candidature recherchée
     * @param session
     *            La session concernée par la candidature recherchée
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
            e.printStackTrace();
            throw new DAOException( e );
        }
        return candidature;
    }
}
