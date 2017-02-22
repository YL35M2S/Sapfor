package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

public class SessionDaoImpl extends Dao implements SessionDao {
    private static final String SQL_SELECT_PAR_ID  = "SELECT * FROM Session WHERE idSession = ?";
    private static final String SQL_INSERT_SESSION = "INSERT INTO Session (nom, dateDebut, dateFin, ouvertureInscription, idStage, idUv) VALUES(?, ?, ?, ?, ?, ?);";

    /* Constructeur */
    public SessionDaoImpl( Connection conn ) {
        super( conn );
    }

    /* Implémentation des méthodes */

    /**
     * Creation d'une session dans la Base de Donnees DAO
     * 
     * @param session
     *            La session à sauvegarder
     * @return l'Id de la session créée
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

            /* Recuperation de l'Id créé */
            ResultSet resultat = preparedStatement.getGeneratedKeys();
            resultat.next();
            return resultat.getInt( 1 );
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    /**
     * Recherche d'un agent dans la Base de Donnees DAO à partir de son id
     * 
     * @param id
     *            L'id de l'agent a rechercher
     * @return l'agent recherché
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

            /* Creation de l'agent */
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
            throw new DAOException( e );
        }
        return session;
    }
}
