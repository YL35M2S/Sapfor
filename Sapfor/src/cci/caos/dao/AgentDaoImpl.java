package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cci.caos.repository.Agent;
import cci.caos.repository.Aptitude;
import cci.caos.repository.Uv;

public class AgentDaoImpl extends Dao implements AgentDao {
    private static final String SQL_SELECT_PAR_ID         = "SELECT * FROM Agent WHERE idAgent = ?";
    private static final String SQL_INSERT_AGENT          = "INSERT INTO Agent (  nomAgent ,  mdp ,  matricule,  gestionnaire) VALUES (?, ?, ?, ?);";
    private static final String SQL_EXISTE_AGENT          = "SELECT * FROM Agent WHERE idAgent = ?";
    private static final String SQL_UPDATE_AGENT          = "UPDATE Agent SET nomAgent=? ,  mdp=?,  matricule=?,  gestionnaire=? WHERE idAgent = ?";
    private static final String SQL_INSERT_UV_AGENT       = "INSERT INTO listeUv (idAgent, idUv) VALUES (?,?);";
    private static final String SQL_INSERT_APTITUDE_AGENT = "INSERT INTO listeAptitude (idAgent, idAptitude) VALUES (?,?);";
    private static final String SQL_DELETE_UV_AGENT       = "DELETE FROM listeUv WHERE idAgent=?";
    private static final String SQL_DELETE_APTITUDE_AGENT = "DELETE FROM listeAptitude WHERE idAgent=?";

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
        int idNewAgent;

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
            idNewAgent = resultat.getInt( 1 );

            /* Creation des Uv obtenues par l'agent */
            if ( agent.getListeUV().size() > 0 ) {
                preparedStatement = connect.prepareStatement( SQL_INSERT_UV_AGENT );

                AbstractDAOFactory adf = AbstractDAOFactory.getFactory( AbstractDAOFactory.DAO_FACTORY );
                UvDao uvDao = adf.getUvDao();

                /* Verification de l'existence de l'UV */
                for ( Uv u : agent.getListeUV() ) {
                    int idNewUv;
                    if ( !uvDao.existe( u ) ) {
                        idNewUv = uvDao.creer( u );
                        u.setId( idNewUv );
                    } else {
                        idNewUv = u.getId();
                    }
                    /* Remplissage des champs de la requete */
                    preparedStatement.setInt( 1, idNewAgent );
                    preparedStatement.setInt( 2, idNewUv );

                    /* Execution de la requete */
                    preparedStatement.executeUpdate();
                }
            }

            /* Creation des Aptitudes obtenues par l'agent */
            if ( agent.getListeAptitude().size() > 0 ) {
                preparedStatement = connect.prepareStatement( SQL_INSERT_APTITUDE_AGENT );

                AbstractDAOFactory adf = AbstractDAOFactory.getFactory( AbstractDAOFactory.DAO_FACTORY );
                AptitudeDao aptitudeDao = adf.getAptitudeDao();

                /* Verification de l'existence de l'UV */
                for ( Aptitude a : agent.getListeAptitude() ) {
                    int idNewAptitude;
                    if ( !aptitudeDao.existe( a ) ) {
                        idNewAptitude = aptitudeDao.creer( a );
                        a.setId( idNewAptitude );
                    } else {
                        idNewAptitude = a.getId();
                    }
                    /* Remplissage des champs de la requete */
                    preparedStatement.setInt( 1, idNewAgent );
                    preparedStatement.setInt( 2, idNewAptitude );

                    /* Execution de la requete */
                    preparedStatement.executeUpdate();
                }
            }
            return idNewAgent;
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
     * Recherche si un agent existe dans la Base de Donnees DAO
     * 
     * @param agent
     *            L'agent a rechercher
     * @return Vrai si il existe sinon false
     */
    @Override
    public boolean existe( Agent agent ) throws DAOException {
        boolean existe = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_EXISTE_AGENT );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, agent.getId() );

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

            /* Effacement des Uv obtenues */
            preparedStatement = connect.prepareStatement( SQL_DELETE_UV_AGENT );
            preparedStatement.setInt( 1, agent.getId() );
            preparedStatement.executeUpdate();

            /* Creation des Uv obtenues par l'agent */
            if ( agent.getListeUV().size() > 0 ) {
                preparedStatement = connect.prepareStatement( SQL_INSERT_UV_AGENT );

                AbstractDAOFactory adf = AbstractDAOFactory.getFactory( AbstractDAOFactory.DAO_FACTORY );
                UvDao uvDao = adf.getUvDao();

                /* Verification de l'existence de l'UV */
                for ( Uv u : agent.getListeUV() ) {
                    int idNewUv;
                    if ( !uvDao.existe( u ) ) {
                        idNewUv = uvDao.creer( u );
                        u.setId( idNewUv );
                    } else {
                        idNewUv = u.getId();
                    }
                    /* Remplissage des champs de la requete */
                    preparedStatement.setInt( 1, agent.getId() );
                    preparedStatement.setInt( 2, idNewUv );

                    /* Execution de la requete */
                    preparedStatement.executeUpdate();
                }
            }

            /* Effacement des aptitudes obtenues */
            preparedStatement = connect.prepareStatement( SQL_DELETE_APTITUDE_AGENT );
            preparedStatement.setInt( 1, agent.getId() );
            preparedStatement.executeUpdate();

            /* Creation des Aptitudes obtenues par l'agent */
            if ( agent.getListeAptitude().size() > 0 ) {
                preparedStatement = connect.prepareStatement( SQL_INSERT_APTITUDE_AGENT );

                AbstractDAOFactory adf = AbstractDAOFactory.getFactory( AbstractDAOFactory.DAO_FACTORY );
                AptitudeDao aptitudeDao = adf.getAptitudeDao();

                /* Verification de l'existence de l'UV */
                for ( Aptitude a : agent.getListeAptitude() ) {
                    int idNewAptitude;
                    if ( !aptitudeDao.existe( a ) ) {
                        idNewAptitude = aptitudeDao.creer( a );
                        a.setId( idNewAptitude );
                    } else {
                        idNewAptitude = a.getId();
                    }
                    /* Remplissage des champs de la requete */
                    preparedStatement.setInt( 1, agent.getId() );
                    preparedStatement.setInt( 2, idNewAptitude );

                    /* Execution de la requete */
                    preparedStatement.executeUpdate();
                }
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
    }
}
