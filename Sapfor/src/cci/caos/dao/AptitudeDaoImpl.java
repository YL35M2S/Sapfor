package cci.caos.dao;

import static cci.caos.dao.DaoUtilitaires.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cci.caos.repository.Aptitude;

public class AptitudeDaoImpl implements AptitudeDao{
	
	private DaoFactory daoFactory;
	private static final String SQL_INSERT_APTITUDE = "INSERT INTO Aptitude VALUES(?);";
	private static final String SQL_EXISTE_APTITUDE = "SELECT * FROM Aptitude WHERE nomAptitude = ?";
	


	/*Constructeur */

	public AptitudeDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	} 
	
	/*Implémentation des méthodes */
	
	public void creer (Aptitude aptitude) throws DAOException{
			String nom = aptitude.getNom();
			int    statut;
		     
		     Connection connexion = null; 
		     PreparedStatement preparedStatement = null; 
		     
		     try{
		     connexion = daoFactory.getConnection();
		     preparedStatement = DaoFactory.initialisationRequetePreparee(connexion, SQL_INSERT_APTITUDE, false, nom);
		     statut = preparedStatement.executeUpdate(); 
		     if (statut==0){System.out.println("ok");};
		     } catch ( SQLException e ) {
	         	throw new DAOException( e );
				}
				finally {
					fermeturesSilencieuses( preparedStatement, connexion );
				}
	
	}
	
	public boolean existe(String nom) throws DAOException{
		boolean existe = false;
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connexion = daoFactory.getConnection();
			preparedStatement = DaoFactory.initialisationRequetePreparee( connexion, SQL_EXISTE_APTITUDE, false, nom );
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
