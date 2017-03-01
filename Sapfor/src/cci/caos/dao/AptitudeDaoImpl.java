package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cci.caos.repository.Aptitude;
import cci.caos.repository.Uv;

public class AptitudeDaoImpl extends Dao implements AptitudeDao {
    private static final String SQL_INSERT_APTITUDE    = "INSERT INTO Aptitude (nomAptitude) VALUES(?);";
    private static final String SQL_INSERT_UV_APTITUDE = "INSERT INTO listeAptitudeUv (idAptitude, idUv) VALUES (?,?);";
    private static final String SQL_SELECT_PAR_ID      = "SELECT * FROM Aptitude WHERE idAptitude = ?";
    private static final String SQL_EXISTE_APTITUDE    = "SELECT * FROM Aptitude WHERE idAptitude = ?";
    private static final String SQL_UPDATE_APTITUDE    = "UPDATE Aptitude SET nomAptitude=? WHERE idAptitude=?";
    private static final String SQL_DELETE_UV_APTITUDE = "DELETE FROM listeAptitudeUv WHERE idAptitude = ?";

    /**
     * Constructeur
     * @param conn
     */
    public AptitudeDaoImpl( Connection conn ) {
        super( conn );
    }

    /* Implémentation des méthodes */
  
	/**
	* Création d'une aptitude dans la base de données
	* @param aptitude - l'aptitude a enregistrer
	* @return l'id de l'aptitude enregistrée
	* @throws DAOException si la création dans la base de données a provoqué une SQLException
	*/
    @Override
    public int creer( Aptitude aptitude ) throws DAOException {
        int idNewAptitude;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connect.prepareStatement( SQL_INSERT_APTITUDE, Statement.RETURN_GENERATED_KEYS );

            /* Remplissage des champs de la requete */
            preparedStatement.setString( 1, aptitude.getNom() );

            /* Execution de la requete */
            preparedStatement.executeUpdate();
            ResultSet resultat = preparedStatement.getGeneratedKeys();
            resultat.next();
            idNewAptitude = resultat.getInt( 1 );

            /* Creation des Uv requises pour l'aptitude */
            if ( aptitude.getListeUV().size() > 0 ) {
                preparedStatement = connect.prepareStatement( SQL_INSERT_UV_APTITUDE );

                AbstractDAOFactory adf = AbstractDAOFactory.getFactory( AbstractDAOFactory.DAO_FACTORY );
                UvDao uvDao = adf.getUvDao();

                /* Verification de l'existence de l'UV */
                for ( Uv u : aptitude.getListeUV() ) {
                    int idNewUv;
                    if ( !uvDao.existe( u ) ) {
                        idNewUv = uvDao.creer( u );
                    } else {
                        idNewUv = u.getId();
                    }
                    /* Remplissage des champs de la requete */
                    preparedStatement.setInt( 1, idNewAptitude );
                    preparedStatement.setInt( 2, idNewUv );

                    /* Execution de la requete */
                    preparedStatement.executeUpdate();
                }
            }
            return idNewAptitude;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    /**
     * Recherche d'une aptitude par son id dans la base de données
     * @param id - L'id de l'aptitude à rechercher dans la base de données
     * @return Un objet Aptitude correspondant à l'aptitude recherchée.
     * Si l'aptitude n'est pas présente dans la base de données, l'objet Aptitude est initialisé à null
     * @throws DAOException si la mise à jour dans la base de données a provoqué une SQLException
     */
    @Override
    public Aptitude trouver( int id ) throws DAOException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Aptitude aptitude = null;
        try {
            preparedStatement = connect.prepareStatement( SQL_SELECT_PAR_ID );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, id );

            /* Execution de la requete */
            resultSet = preparedStatement.executeQuery();

            /* Creation de l'aptitude */
            if ( resultSet.next() ) {
                aptitude = new Aptitude();
                aptitude.setId( id );
                aptitude.setNom( resultSet.getString( "nomAptitude" ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
        return aptitude;
    }

    /**
     * Recherche d'une aptitude dans la base de données
     * @param aptitude - l'aptitude à rechercher dans la base de données
     * @return true si l'aptitude est présente dans la base de données, sinon renvoie false
     * @throws DAOException si la recherche dans la base de données a provoqué une SQLException
     */
    @Override
    public boolean existe( Aptitude aptitude ) throws DAOException {
        boolean existe = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connect.prepareStatement( SQL_EXISTE_APTITUDE );

            /* Remplissage des champs de la requete */
            preparedStatement.setInt( 1, aptitude.getId() );

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
     * Mise à jour d'une aptitude dans la base de données
     * @param aptitude - l'aptitude à mettre à jour dans la base de données
     * @throws DAOException si la mise à jour dans la base de données a provoqué une SQLException
     */
    @Override
    public void mettreAJour( Aptitude aptitude ) throws DAOException {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connect.prepareStatement( SQL_UPDATE_APTITUDE );

            /* Remplissage des champs de la requete */
            preparedStatement.setString( 1, aptitude.getNom() );
            preparedStatement.setInt( 2, aptitude.getId() );

            /* Execution de la requete */
            preparedStatement.executeUpdate();

            /* Effacement des Uv prerequises */
            preparedStatement = connect.prepareStatement( SQL_DELETE_UV_APTITUDE );
            preparedStatement.setInt( 1, aptitude.getId() );
            preparedStatement.executeUpdate();

            /* Creation des Uv requises par l'aptitude */
            if ( aptitude.getListeUV().size() > 0 ) {
                preparedStatement = connect.prepareStatement( SQL_INSERT_UV_APTITUDE );

                AbstractDAOFactory adf = AbstractDAOFactory.getFactory( AbstractDAOFactory.DAO_FACTORY );
                UvDao uvDao = adf.getUvDao();

                /* Verification de l'existence de l'UV */
                for ( Uv u : aptitude.getListeUV() ) {
                    int idNewUv;
                    if ( !uvDao.existe( u ) ) {
                        idNewUv = uvDao.creer( u );
                    } else {
                        idNewUv = u.getId();
                    }
                    /* Remplissage des champs de la requete */
                    preparedStatement.setInt( 1, aptitude.getId() );
                    preparedStatement.setInt( 2, idNewUv );

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