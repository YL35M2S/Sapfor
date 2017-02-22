package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cci.caos.repository.Agent;

public class AgentDaoImpl extends Dao implements AgentDao {
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Agent WHERE idAgent = ?";
    private static final String SQL_INSERT_AGENT  = "INSERT INTO Agent (  nomAgent ,  mdp ,  matricule,  gestionnaire) VALUES(?, ?, ?, ?);";
    private static final String SQL_EXISTE_AGENT  = "SELECT * FROM Agent WHERE idAgent = ?";
    private static final String SQL_UPDATE_AGENT  = "UPDATE Agent SET nomAgent=? ,  mdp=?,  matricule=?,  gestionnaire=? WHERE idAgent = ?";

    /* Constructeur */
    public AgentDaoImpl( Connection conn ) {
        super( conn );
    }

    /* Implementation des méthodes */

    /**
     * Creation d'un agent dans la Base de Donnees DAO
     * 
     * @param agent
     *            L'agent a sauvegarder
     * @return l'Id de l'agent créé
     */
    @Override
    public int creer( Agent agent ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_INSERT_AGENT, Statement.RETURN_GENERATED_KEYS );

            /* Remplissage des champs de la requete */
            preparedStatement.setString( 1, agent.getNom() );
            preparedStatement.setString( 2, agent.getMdp() );
            preparedStatement.setString( 3, agent.getMatricule() );
            preparedStatement.setBoolean( 4, agent.getGestionnaire() );

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
    public Agent trouver( int id ) throws DAOException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Agent agent = null;
        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_PAR_ID );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, id );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Creation de l'agent */
            if ( resultSet.next() ) {
                agent = new Agent();
                agent.setId( id );
                agent.setNom( resultSet.getString( "nomAgent" ) );
                agent.setMdp( resultSet.getString( "mdp" ) );
                agent.setMatricule( resultSet.getString( "matricule" ) );
                agent.setGestionnaire( resultSet.getBoolean( "gestionnaire" ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
        return agent;
    }

    /**
     * Recherche si un agent identifié par son id existe dans la Base de Donnees
     * DAO
     * 
     * @param id
     *            L'id de l'agent a rechercher
     * @return Vrai si il existe sinon false
     */
    @Override
    public boolean existe( int idAgent ) throws DAOException {
        boolean existe = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_EXISTE_AGENT );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, idAgent );

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
     * Mise a jour d'un agent dans la Base de Donnees DAO
     * 
     * @param agent
     *            L'agent a mettre a jour
     */
    @Override
    public void mettreAJour( Agent agent ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_UPDATE_AGENT );

            /* Remplissage des champs de la requete */
            preparedStatement.setString( 1, agent.getNom() );
            preparedStatement.setString( 2, agent.getMdp() );
            preparedStatement.setString( 3, agent.getMatricule() );
            preparedStatement.setBoolean( 4, agent.getGestionnaire() );
            preparedStatement.setInt( 5, agent.getId() );

            /* Execution de la requete */
            preparedStatement.executeUpdate();

        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }
}
