package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cci.caos.repository.Agent;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

public class CandidatureDaoImpl extends Dao implements CandidatureDao {
    private static final String SQL_SELECT_PAR_CANDIDAT_SESSION = "SELECT * FROM Candidature WHERE idAgent = ? AND idSession = ?";
    private static final String SQL_EXISTE_CANDIDATURE          = "SELECT * FROM Candidature WHERE idAgent = ? AND idSession = ?";
    private static final String SQL_SELECT_ALL                  = "SELECT * FROM Candidature";
    private static final String SQL_SELECT_PAR_AGENT            = "SELECT * FROM Candidature WHERE idAgent = ? ";
    private static final String SQL_SELECT_PAR_SESSION          = "SELECT * FROM Candidature WHERE idSession = ?";
    private static final String SQL_INSERT_CANDIDATURE          = "INSERT INTO Candidature (idAgent, statutCandidature, estFormateur, idSession) VALUES(?, ?, ?, ?);";
    private static final String SQL_DELETE_PAR_SESSION          = "DELETE FROM Candidature WHERE idSession = ?";
    private static final String SQL_DELETE_PAR_CANDIDAT_SESSION = "DELETE FROM Candidature WHERE idSession = ? AND idAgent = ? ";

    /* Constructeur */
    public CandidatureDaoImpl( Connection conn ) {
        super( conn );
    }

    /* Impl�mentation des m�thodes */

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
     * Recherche d'une candidature dans la Base de Donnees DAO � partir de
     * l'agent et de la session
     * 
     * @param agent
     *            L'agent concern� par la candidature recherch�e
     * @param session
     *            La session concern�e par la candidature recherch�e
     * @return la candidature recherch�e
     */
    @Override
    public Candidature trouver( Agent agent, Session session ) throws DAOException {
        PreparedStatement preparedStatement;
        Candidature candidature = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_PAR_CANDIDAT_SESSION );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, agent.getId() );
            preparedStatement.setInt( 2, session.getId() );

            /* Execution de la requete */
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

    /**
     * Liste toutes les candidatures existantes dans la Base de Donnees DAO
     * 
     * @return liste des toutes les candidatures en BDD
     */
    @Override
    public List<Candidature> listerToutes() throws DAOException {
        PreparedStatement preparedStatement;
        List<Candidature> listeCandidature = new ArrayList<Candidature>();
        Candidature candidature;
        ResultSet resultSet = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_ALL );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
                candidature = new Candidature();
                candidature.setEstFormateur( resultSet.getBoolean( "estFormateur" ) );
                candidature.setStatutCandidature( resultSet.getInt( "statutCandidature" ) );
                candidature.setAgent( SapforServer.getSessionServer().getAgentById( resultSet.getInt( "idAgent" ) ) );
                candidature.setSession(
                        SapforServer.getSessionServer().getSessionById( resultSet.getInt( "idSession" ) ) );
                listeCandidature.add( candidature );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
        return listeCandidature;
    }

    /**
     * Liste les candidatures pour un agent donn�
     * 
     * @param agent
     *            L'agent concern� par les candidatures recherch�es
     * @return la liste des candidatures pour l'agent donn�
     */
    @Override
    public List<Candidature> listerCandidaturesParAgent( int idAgent ) throws DAOException {
        PreparedStatement preparedStatement;
        List<Candidature> listeCandidature = new ArrayList<Candidature>();
        Candidature candidature;
        ResultSet resultSet = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_PAR_AGENT );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, idAgent );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
                candidature = new Candidature();
                candidature.setEstFormateur( resultSet.getBoolean( "estFormateur" ) );
                candidature.setStatutCandidature( resultSet.getInt( "statutCandidature" ) );
                candidature.setAgent( SapforServer.getSessionServer().getAgentById( idAgent ) );
                candidature.setSession(
                        SapforServer.getSessionServer().getSessionById( resultSet.getInt( "idSession" ) ) );
                listeCandidature.add( candidature );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
        return listeCandidature;
    }

    /**
     * Liste les candidatures pour une session donn�e
     * 
     * @param session
     *            La session concern�e par les candidatures recherch�es
     * @return la liste des candidatures pour une sesion donn�e
     */
    @Override
    public List<Candidature> listerCandidaturesParSession( int idSession ) throws DAOException {
        PreparedStatement preparedStatement;
        List<Candidature> listeCandidature = new ArrayList<Candidature>();
        Candidature candidature;
        ResultSet resultSet = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_PAR_SESSION );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, idSession );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
                candidature = new Candidature();
                candidature.setEstFormateur( resultSet.getBoolean( "estFormateur" ) );
                candidature.setStatutCandidature( resultSet.getInt( "statutCandidature" ) );
                candidature.setAgent( SapforServer.getSessionServer().getAgentById( resultSet.getInt( "idAgent" ) ) );
                candidature.setSession( SapforServer.getSessionServer().getSessionById( idSession ) );
                listeCandidature.add( candidature );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
        return listeCandidature;
    }

    /**
     * Met a jour la liste des candidatures pour une session donn�e
     * 
     * @param session
     *            La session concern�e par la mise a jour
     */
    @Override
    public void mettreAJourCandidatureASession( int idSession, List<Candidature> listeCandidature )
            throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_DELETE_PAR_SESSION );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, idSession );

            /* Execution de la requete */
            preparedStatement.executeQuery();

            /* Creation des nouvelles candidatures */
            for ( Candidature c : listeCandidature ) {
                creer( c );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
    }

    /**
     * Supprime la candidature d'un agent pour une session donn�e
     * 
     * @param candidature
     *            La candidature � supprimer
     */
    @Override
    public void supprimerCandidature( Candidature candidature ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_DELETE_PAR_CANDIDAT_SESSION );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, candidature.getSession().getId() );
            preparedStatement.setInt( 2, candidature.getAgent().getId() );

            /* Execution de la requete */
            preparedStatement.executeQuery();
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
    }

    /**
     * Supprime la candidature d'un agent pour une session donn�e
     * 
     * @param idAgent
     *            L'id de l'agent concern� par la candidature � supprimer
     * @param idSession
     *            L'id de la session concern�e par la candidature � supprimer
     */
    @Override
    public void supprimerCandidature( int idAgent, int idSession ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_DELETE_PAR_CANDIDAT_SESSION );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, idSession );
            preparedStatement.setInt( 2, idAgent );

            /* Execution de la requete */
            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
    }

    /**
     * Verifie l'existence d'une candidature d'un agent pour une session donn�e
     * 
     * @param candidature
     *            la candidature � recherch�e
     * @return Vrai si la candaiture existe deja, sinon faux
     */
    @Override
    public boolean existe( Candidature candidature ) throws DAOException {
        boolean existe = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_EXISTE_CANDIDATURE );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, candidature.getAgent().getId() );
            preparedStatement.setInt( 2, candidature.getSession().getId() );

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
     * Verifie l'existence d'une candidature d'un agent pour une session donn�e
     * 
     * @param idSession
     *            l'id de la session � rechercher
     * @param idAgent
     *            l'id de l'agent a rechercher
     * @return Vrai si la candaiture existe deja, sinon faux
     */
    @Override
    public boolean existe( int idAgent, int idSession ) throws DAOException {
        boolean existe = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_EXISTE_CANDIDATURE );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, idAgent );
            preparedStatement.setInt( 2, idSession );

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
