package cci.caos.dao;

import static cci.caos.dao.DaoUtilitaires.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cci.caos.repository.Agent;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;
import cci.caos.server.SapforServer;

public class CandidatureDaoImpl implements CandidatureDao{

	private DaoFactory daoFactory;
	private static final String SQL_SELECT_PAR_CANDIDAT_SESSION = "SELECT * FROM Session WHERE idCandidat = ? and idSession = ?";
	private static final String SQL_INSERT_CANDIDATURE = "INSERT INTO Session VALUES(?, ?, ?, ?, ?);"; 

	/*Constructeur */
	public CandidatureDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	/*Implémentation des méthodes */
	
	
	
	public Candidature trouver(int idCandidat, int idSession) throws DAOException{
		Candidature candidature = null;
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connexion = daoFactory.getConnection();
			preparedStatement = DaoFactory.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_CANDIDAT_SESSION, false, idCandidat, idSession );
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()){
				candidature = new Candidature();
				candidature.setEstFormateur(resultSet.getBoolean("estFormateur"));
				candidature.setStatutCandidature(resultSet.getInt("statutCandidature"));
				candidature.setAgent(SapforServer.getSessionServer().getAgentById(idCandidat));
				candidature.setSession(SapforServer.getSessionServer().getSessionById(idSession));
			}
		} catch ( SQLException e ) {
        	throw new DAOException( e );
		  }
		finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		
		return candidature;
	}
	
	
	
	
	public void creer (Candidature candidature) throws DAOException{
		  int   	idAgent = candidature.getAgent().getId();
		  int     	statutCandidature = candidature.getStatutCandidature();
		  boolean 	estFormateur = candidature.isEstFormateur();
		  int 		idSession = candidature.getSession().getId();
		  int 		statut;
		     
		     Connection connexion = null; 
		     PreparedStatement preparedStatement = null; 
		     
		     try{
		     connexion = daoFactory.getConnection();
		     preparedStatement = DaoFactory.initialisationRequetePreparee(connexion, SQL_INSERT_CANDIDATURE, false, idAgent, statutCandidature, estFormateur, idSession);
		     statut = preparedStatement.executeUpdate();
		     if (statut==0){System.out.println("ok");};
		     } catch ( SQLException e ) {
		         	throw new DAOException( e );
			   }
				finally {
					fermeturesSilencieuses( preparedStatement, connexion );
				} 
		     
	}

	
	

	
}
