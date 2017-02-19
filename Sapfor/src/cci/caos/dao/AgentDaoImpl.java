package cci.caos.dao;

import static cci.caos.dao.DaoUtilitaires.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cci.caos.repository.Agent;
import cci.caos.repository.Aptitude;
import cci.caos.repository.Session;
import cci.caos.repository.Uv;
import cci.caos.server.SapforServer;

public class AgentDaoImpl implements AgentDao{
	
	private DaoFactory daoFactory;
	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Agent WHERE idAgent = ?";
	private static final String SQL_INSERT_AGENT = "INSERT INTO Agent VALUES(?, ?, ?, ?, ?);";
	
	/*Constructeur */
	
	public AgentDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	/*Implementation des méthodes*/
	
	public void creer (Agent agent) throws DAOException{
		 int            id = agent.getId();
	     String         nom = agent.getNom();
	     String         mdp = agent.getMdp();
	     String         matricule = agent.getMatricule();
	     Boolean        gestionnaire = agent.getGestionnaire();
	     int 			statut;
		
		 Connection connexion = null; 
	     PreparedStatement preparedStatement = null; 
	     
	     try{
	     connexion = daoFactory.getConnection();
	     preparedStatement = DaoFactory.initialisationRequetePreparee(connexion,SQL_INSERT_AGENT, false,id, nom, mdp, matricule, gestionnaire);
	     statut = preparedStatement.executeUpdate();
	     if (statut==0){System.out.println("ok");}; 
	     } catch ( SQLException e ) {
	         	throw new DAOException( e );
				}
				finally {
					fermeturesSilencieuses( preparedStatement, connexion );
				}
	}

	public Agent trouver (int id) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		Agent agent = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connexion = daoFactory.getConnection();
			preparedStatement = DaoFactory.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()){
				agent = new Agent();
				agent.setId(id);
				agent.setNom(resultSet.getString("nom"));
				agent.setMdp(resultSet.getString("mdp"));
				agent.setMatricule(resultSet.getString("matricule"));
				agent.setGestionnaire(resultSet.getBoolean("gestionnaire"));
			}	
				
		} catch ( SQLException e ) {
            	throw new DAOException( e );
		  }
			finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
			
		return agent;
	}
}
