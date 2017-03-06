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

    /**
     * 
     * @param conn
     */
    public CandidatureDaoImpl( Connection conn ) {
        super( conn );
    }

    /* Implémentation des méthodes */

    /**
     * Création d'une candidature dans la base de données
     * 
     * @param candidature
     *            - La candidature à enregistrer
     * @throws DAOException
     *             si la création dans la base de données a provoqué une
     *             SQLException
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
     * Récupère une candidature dans la base de données à partir de l'agent et
     * de la session
     * 
     * @param agent
     *            - l'agent concerné par la candidature recherchée
     * @param session
     *            - la session concernée par la candidature recherchée
     * @return La candidature correspondante
     * @throws DAOException
     *             si la recherche de la candidature dans la base de données a
     *             provoqué une SQLException
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
     * Liste toutes les candidatures existantes dans la Base de Donnees
     * 
     * @return liste de toutes les candidatures existantes dans la Base de
     *         Donnees
     * @throws DAOException
     *             si la recherche des candidatures existantes dans la Base de
     *             Donnees a provoqué une SQLException
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
     * Liste toutes les candidatures correspondant à un agent donné
     * 
     * @param idAgent
     *            - L'id de l'agent concerné
     * @return liste de toutes les candidatures de cet agent
     * @throws DAOException
     *             si la recherche des candidatures de cet agent dans la base de
     *             données a provoqué une SQLException
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
     * Liste toutes les candidatures d'une session donnée
     * 
     * @param idSession
     *            - L'id de la session concernée par les candidatures
     *            recherchées
     * @return liste de toutes les candidatures concernant cette session
     * @throws DAOException
     *             si la recherche des candidatures correspondant à cette
     *             session dans la base de données a provoqué une SQLException
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
     * Met à jour les candidatures d'une session donnée
     * 
     * @param idSession
     *            - L'id de la session concernée
     * @param listeCandidature
     *            - la liste des candidatures à mettre à jour
     * @throws DAOException
     *             si la mise à jour des candidatures dans la base de données a
     *             provoqué une SQLException
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
            preparedStatement.executeUpdate();

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
     * Supprime une candidature présente dans la base de données
     * 
     * @param candidature
     *            - la candidature à supprimer
     * @throws DAOException
     *             si la suppression de la candidature dans la base de données a
     *             provoqué une SQLException
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
     * Supprime une candidature d'un agent donné pour une session donnée
     * 
     * @param idAgent
     *            - l'id de l'agent concerné
     * @param idSession
     *            - l'id de la session concernée
     * @throws DAOException
     *             si la suppression de la candidature dans la base de données a
     *             provoqué une SQLException
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
     * Recherche d'une candidature dans la base de données
     * 
     * @param candidature
     *            - la candidature à rechercher
     * @return true si la candidature existe dans la base de données, sinon
     *         renvoie false
     * @throws DAOException
     *             si la recherche dans la base de données a provoqué une
     *             SQLException
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
     * Recherche si une candidature correspondant à l'agent d'id idAgent et de
     * session idSession existe dans la base de données
     * 
     * @param idAgent
     *            - L'id d'un agent
     * @param idSession
     *            - L'id d'une session
     * @return true si une candidature existe pour cet agent et cette session,
     *         sinno renvoie false
     * @throws DAOException
     *             si la recherche dans la base de données a provoqué une
     *             SQLException
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
