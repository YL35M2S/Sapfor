package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cci.caos.dao.exception.DAOException;
import cci.caos.dao.factory.AbstractDAOFactory;
import cci.caos.repository.Agent;
import cci.caos.repository.Aptitude;
import cci.caos.repository.Uv;
import cci.caos.server.SapforServer;

public class AgentDaoImpl extends Dao implements AgentDao {
    private static final String SQL_SELECT_PAR_ID         = "SELECT * FROM Agent WHERE idAgent = ?";
    private static final String SQL_SELECT_ALL            = "SELECT * FROM Agent";
    private static final String SQL_INSERT_AGENT          = "INSERT INTO Agent (  nomAgent ,  mdp ,  matricule,  gestionnaire) VALUES (?, ?, ?, ?);";
    private static final String SQL_EXISTE_AGENT          = "SELECT * FROM Agent WHERE idAgent = ?";
    private static final String SQL_UPDATE_AGENT          = "UPDATE Agent SET nomAgent=? ,  mdp=?,  matricule=?,  gestionnaire=? WHERE idAgent = ?";
    private static final String SQL_INSERT_UV_AGENT       = "INSERT INTO listeUv (idAgent, idUv) VALUES (?,?)";
    private static final String SQL_INSERT_APTITUDE_AGENT = "INSERT INTO listeAptitude (idAgent, idAptitude) VALUES (?,?)";
    private static final String SQL_DELETE_UV_AGENT       = "DELETE FROM listeUv WHERE idAgent=?";
    private static final String SQL_DELETE_APTITUDE_AGENT = "DELETE FROM listeAptitude WHERE idAgent=?";
    private static final String SQL_SELECT_UV_AGENT       = "SELECT * FROM listeUv WHERE idAgent=?";
    private static final String SQL_SELECT_APTITUDE_AGENT = "SELECT * FROM listeAptitude WHERE idAgent=?";

    /**
     * Constructeur
     * 
     * @param conn
     */
    public AgentDaoImpl( Connection conn ) {
        super( conn );
    }

    /* Implementation des méthodes */

    /**
     * Création d'un agent dans la base de données
     * 
     * @param agent
     *            - l'agent à enregistrer
     * @return L'id de l'agent enregistré
     * @throws DAOException
     *             si la création dans la base de données a provoqué une
     *             SQLException
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

            /* Recuperation de l'Id cree */
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
     * Recherche d'un agent dans la base de données à partir de son id
     * 
     * @param id
     *            - l'id de l'agent à rechercher
     * @return Un objet de type Agent correspondant à l'agent recherché
     * @throws DAOException
     *             si la recherche dans la base de données a provoqué une
     *             SQLException
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
                agent.setListeAptitude( listerAptitudeParAgent( id ) );
                agent.setListeUV( listerUvParAgent( id ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
        return agent;
    }

    /**
     * Recherche si un agent existe dans la base de données
     * 
     * @param agent
     *            - l'agent a rechercher
     * @return True si l'agent existe, sinon renvoie false
     * @throws DAOException
     *             si la recherche dans la base de données a provoqué une
     *             SQLException
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
     * Mise a jour d'un agent dans la base de données
     * 
     * @param agent
     *            - l'agent à mettre à jour
     * @throws DAOException
     *             - si la mise à jour dans la base de données a provoqué une
     *             SQLException
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

    /**
     * Liste tous les agents existants dans la base de données
     * 
     * @return Liste de tous les agents existants dans la base de données
     * @throws DAOException
     *             si la recherche des agents existants dans la base de données
     *             a provoqué une SQLException
     */
    @Override
    public List<Agent> listerTous() throws DAOException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Agent agent = null;
        List<Agent> listeAgents = new ArrayList<Agent>();

        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_ALL );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Creation de la liste des agents */
            while ( resultSet.next() ) {
                agent = new Agent();
                agent.setId( resultSet.getInt( "idAgent" ) );
                agent.setNom( resultSet.getString( "nomAgent" ) );
                agent.setMdp( resultSet.getString( "mdp" ) );
                agent.setMatricule( resultSet.getString( "matricule" ) );
                agent.setGestionnaire( resultSet.getBoolean( "gestionnaire" ) );
                agent.setListeAptitude( listerAptitudeParAgent( resultSet.getInt( "idAgent" ) ) );
                agent.setListeUV( listerUvParAgent( resultSet.getInt( "idAgent" ) ) );
                listeAgents.add( agent );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
        return listeAgents;
    }

    /**
     * Liste toutes les aptitudes possédées par un agent qui sont enregistrées
     * dans la base de données
     * 
     * @param id
     *            - L'id de l'agent concerné
     * @return Liste des aptitudes possédées par l'agent
     * @throws DAOException
     *             si la recherche des aptitudes possédées par l'agent dans la
     *             base de données a provoqué une SQLException
     */
    @Override
    public List<Aptitude> listerAptitudeParAgent( int id ) throws DAOException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Aptitude> listeAptitudes = new ArrayList<Aptitude>();
        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_APTITUDE_AGENT );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, id );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Creation de l'agent */
            while ( resultSet.next() ) {
                listeAptitudes
                        .add( SapforServer.getSessionServer().getAptitudeById( resultSet.getInt( "idAptitude" ) ) );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
        return listeAptitudes;
    }

    /**
     * Liste toutes les UV possédées par un agent qui sont enregistrées dans la
     * base de données
     * 
     * @param id
     *            - L'id de l'agent concerné
     * @return Liste des UV possédées par l'agent
     * @throws DAOException
     *             si la recherche des UV possédées par l'agent dans la base de
     *             données a provoqué une SQLException
     */
    @Override
    public List<Uv> listerUvParAgent( int id ) throws DAOException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Uv> listeUvs = new ArrayList<Uv>();
        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_UV_AGENT );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, id );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Creation de l'agent */
            while ( resultSet.next() ) {
                listeUvs
                        .add( SapforServer.getSessionServer().getUvById( resultSet.getInt( "idUv" ) ) );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new DAOException( e );
        }
        return listeUvs;
    }
}