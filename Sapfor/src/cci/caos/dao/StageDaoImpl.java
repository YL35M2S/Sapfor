package cci.caos.dao;
import static cci.caos.dao.DaoUtilitaires.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cci.caos.dao.DAOException;

import cci.caos.repository.Stage;


public class StageDaoImpl implements StageDao{

	private DaoFactory daoFactory;
	private static final String SQL_INSERT_STAGE = "INSERT INTO Stage VALUES(?);";
	private static final String SQL_EXISTE_STAGE = "SELECT * FROM Stage WHERE nomStage = ?";

	public StageDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	} 
	
	public void creer (Stage stage) throws DAOException{
		 String            nom = stage.getNom();
		 int 			   statut;
	     
	     Connection connexion = null; 
	     PreparedStatement preparedStatement = null; 
	     
	     try{
	     connexion = daoFactory.getConnection();
	     preparedStatement = DaoFactory.initialisationRequetePreparee(connexion, SQL_INSERT_STAGE, false, nom);
	     statut = preparedStatement.executeUpdate();
	     if (statut==0){System.out.println("ok");};
	     } catch ( SQLException e ) {
	         	throw new DAOException( e );
		   }
			finally {
					fermeturesSilencieuses( preparedStatement, connexion );
			}
		
		
	}
	
	public boolean existe (String nom) throws DAOException{
		boolean existe = false;
		
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connexion = daoFactory.getConnection();
			preparedStatement = DaoFactory.initialisationRequetePreparee( connexion, SQL_EXISTE_STAGE, false, nom );
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()){
				existe = true;
			}
		}catch ( SQLException e ) {
        	throw new DAOException( e );
		 }
		finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		
		return existe;
	}
	
}

