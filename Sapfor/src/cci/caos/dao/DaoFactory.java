package cci.caos.dao;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement; 


public class DaoFactory {
    private static final String PROPERTY_URL             = "jdbc:mysql://mysql.istic.univ-rennes1.fr:3306/base_10000334";
    private static final String PROPERTY_DRIVER          = "com.mysql.jdbc.Driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "user_10000334";
    private static final String PROPERTY_MOT_DE_PASSE    = "Lespert";    
    
    private String url;
    private String nomUtilisateur; 
    private String motDePasse;
    
    DaoFactory(String url, String nomUtilisateur, String motDePasse) {
    	this.url = url; 
    	this.nomUtilisateur = nomUtilisateur; 
    	this.motDePasse = motDePasse;
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DaoFactory getInstance() throws DAOExceptionConfiguration {
        String url;
        String driver;
        String nomUtilisateur; 
        String motDePasse;
        
        url = PROPERTY_URL ;
		driver = PROPERTY_DRIVER ;
		nomUtilisateur = PROPERTY_NOM_UTILISATEUR ;
		motDePasse = PROPERTY_MOT_DE_PASSE ;

        try {
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            throw new DAOExceptionConfiguration( "Le driver est introuvable dans le classpath.", e );
          } 

        DaoFactory instance = new DaoFactory( url, nomUtilisateur, motDePasse );
        return instance;
    }

    
    /* Méthode chargée de fournir une connexion à la base de données */
     Connection getConnection() throws SQLException {
        return DriverManager.getConnection( url, nomUtilisateur, motDePasse );
     }

    /*
     * Méthodes de récupération de l'implémentation des différentes DAO */
     
    public AgentDao getAgentDao() {
        return new AgentDaoImpl( this );
    }
    
    public AptitudeDao getAptitudeDao(){
    	return new AptitudeDaoImpl (this);
    }
    
    public StageDao getStageDao(){
    	return new StageDaoImpl(this);
    }

    public SessionDao getSessionDao(){
    	return new SessionDaoImpl(this);
    }

    public CandidatureDao getCandidatureDao(){
    	return new CandidatureDaoImpl(this);
    }
    
    public UvDao getUvDao(){
    	return new UvDaoImpl(this);
    }

	/*
     * Initialise la requête préparée basée sur la connexion passée en argument,
     * avec la requête SQL et les objets donnés.
     */
    public static PreparedStatement initialisationRequetePreparee( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }
}