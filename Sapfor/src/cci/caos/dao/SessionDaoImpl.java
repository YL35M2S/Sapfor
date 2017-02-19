package cci.caos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import cci.caos.repository.Candidature;
import cci.caos.repository.Session;
import cci.caos.repository.Uv;
import cci.caos.server.SapforServer;

import static cci.caos.dao.DaoUtilitaires.fermeturesSilencieuses;

public class SessionDaoImpl implements SessionDao {
	
	private DaoFactory daoFactory;
	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Session WHERE idUv = ?";
	private static final String SQL_INSERT_SESSION = "INSERT INTO Session VALUES(?, ?, ?, ?, ?, ?);";

	
	/*Constructeur */
	
	public SessionDaoImpl(DaoFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	/*Implementation des méthodes*/
	
	@Override
	public void creer (Session session) throws DAOException{
		 int               id = session.getId();
	     String            nom = session.getNom();
	     Date              dateDebut = session.getDateDebut();
	     Date 			   dateFin = session.getDateFin();
	     boolean           ouverteInscription = session.isOuverteInscription();
	     int               idUv = session.getUv().getId() ;
	     int 			   statut;
	     
	     Connection connexion = null; 
	     PreparedStatement preparedStatement = null; 
	     
	     try{
	     connexion = daoFactory.getConnection();
	     preparedStatement = DaoFactory.initialisationRequetePreparee(connexion, SQL_INSERT_SESSION, false, id, nom, dateDebut, dateFin, ouverteInscription, idUv);
	     statut = preparedStatement.executeUpdate(); 
	     if (statut==0){System.out.println("ok");};
	     } catch ( SQLException e ) {
         	throw new DAOException( e );
			}
			finally {
				fermeturesSilencieuses( preparedStatement, connexion );
			}
	}
	
	
	@Override
	public Session trouver (int id) throws DAOException{
		Connection connexion = null;
		ResultSet resultSet = null;
		Session session = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connexion = daoFactory.getConnection();
			preparedStatement = DaoFactory.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()){
				session = new Session();
				session.setId(id);
				session.setDateDebut(resultSet.getDate("dateDebut"));
				session.setDateFin(resultSet.getDate("dateFin"));
				session.setNom(resultSet.getString("nom"));
				session.setOuverteInscription(resultSet.getBoolean("ouverteInscription"));
				session.setUv(SapforServer.getSessionServer().getUvById(resultSet.getInt("id")));
			}
		}	catch ( SQLException e ) {
            	throw new DAOException( e );
			}
			finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
		return session;
	}
	
	

}
