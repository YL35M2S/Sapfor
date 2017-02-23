package cci.caos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

public class SessionDaoImpl extends Dao implements SessionDao {
    private static final String SQL_SELECT_PAR_ID  = "SELECT * FROM Session WHERE idSession = ?";
    private static final String SQL_SELECT_ALL     = "SELECT * FROM Session";
    private static final String SQL_INSERT_SESSION = "INSERT INTO Session (nom, dateDebut, dateFin, ouvertureInscription, idStage, idUv) VALUES(?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE_SESSION = "UPDATE Session SET nom=?, dateDebut=?, dateFin=?, ouvertureInscription=?, idStage=?, idUv=? WHERE idSession = ?";
    private static final String SQL_EXISTE_SESSION = "SELECT * FROM Session WHERE idSession = ?";

    /* Constructeur */
    public SessionDaoImpl( Connection conn ) {
        super( conn );
    }

    /* Impl�mentation des m�thodes */

    /**
     * Creation d'une session dans la Base de Donnees DAO
     * 
     * @param session
     *            La session � sauvegarder
     * @return l'Id de la session cr��e
     */
    @Override
    public int creer( Session session ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_INSERT_SESSION, Statement.RETURN_GENERATED_KEYS );

            /* Remplissage des champs de la requete */
            preparedStatement.setString( 1, session.getNom() );
            preparedStatement.setDate( 2, (java.sql.Date) session.getDateDebut() );
            preparedStatement.setDate( 3, (java.sql.Date) session.getDateFin() );
            preparedStatement.setBoolean( 4, session.isOuverteInscription() );
            preparedStatement.setInt( 5, session.getStage().getId() );
            preparedStatement.setInt( 6, session.getUv().getId() );

            /* Execution de la requete */
            preparedStatement.executeUpdate();

            /* Recuperation de l'Id cr�� */
            ResultSet resultat = preparedStatement.getGeneratedKeys();
            resultat.next();
            return resultat.getInt( 1 );
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
    }

    /**
     * Recherche d'une session dans la Base de Donnees DAO � partir de son id
     * 
     * @param id
     *            L'id de la session a rechercher
     * @return la session recherch�e
     */
    @Override
    public Session trouver( int id ) throws DAOException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Session session = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_PAR_ID );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, id );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Creation de la session */
            if ( resultSet.next() ) {
                session = new Session();
                session.setId( id );
                session.setDateDebut( resultSet.getDate( "dateDebut" ) );
                session.setDateFin( resultSet.getDate( "dateFin" ) );
                session.setNom( resultSet.getString( "nom" ) );
                session.setStage( SapforServer.getSessionServer().getStageById( resultSet.getInt( "idStage" ) ) );
                session.setOuverteInscription( resultSet.getBoolean( "ouvertureInscription" ) );
                session.setUv( SapforServer.getSessionServer().getUvById( resultSet.getInt( "idUv" ) ) );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
        return session;
    }

    /**
     * Mise a jour d'une session dans la Base de Donnees DAO
     * 
     * @param session
     *            La session a mettre a jour
     */
    @Override
    public void mettreAJour( Session session ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_UPDATE_SESSION );

            /* Remplissage des champs de la requete */
            preparedStatement.setString( 1, session.getNom() );
            preparedStatement.setDate( 2, (Date) session.getDateDebut() );
            preparedStatement.setDate( 3, (Date) session.getDateFin() );
            preparedStatement.setBoolean( 4, session.isOuverteInscription() );
            preparedStatement.setInt( 5, session.getStage().getId() );
            preparedStatement.setInt( 6, session.getUv().getId() );
            preparedStatement.setInt( 7, session.getId() );

            /* Execution de la requete */
            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
    }

    /**
     * Recherche si une session existe dans la Base de Donnees DAO
     * 
     * @param session
     *            La session a rechercher
     * @return Vrai si il existe sinon false
     */
    @Override
    public boolean existe( Session session ) throws DAOException {
        boolean existe = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_EXISTE_SESSION );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, session.getId() );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                existe = true;
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
        return existe;
    }

    /**
     * Liste toutes les sessions existantes dans la Base de Donnees DAO
     * 
     * @return Liste des sessions existantes en BDD
     */
    @Override
    public List<Session> listerToutes() throws DAOException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Session session = null;
        List<Session> listeSessions = new ArrayList<Session>();

        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_ALL );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Creation de la liste de sessions */
            while ( resultSet.next() ) {
                session = new Session();
                session.setId( resultSet.getInt( "idSession" ) );
                session.setDateDebut( resultSet.getDate( "dateDebut" ) );
                session.setDateFin( resultSet.getDate( "dateFin" ) );
                session.setNom( resultSet.getString( "nom" ) );
                session.setStage( SapforServer.getSessionServer().getStageById( resultSet.getInt( "idStage" ) ) );
                session.setOuverteInscription( resultSet.getBoolean( "ouvertureInscription" ) );
                session.setUv( SapforServer.getSessionServer().getUvById( resultSet.getInt( "idUv" ) ) );
                listeSessions.add( session );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
        return listeSessions;
    }
}
