package cci.caos.dao;

import static cci.caos.dao.DaoUtilitaires.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cci.caos.repository.Uv;

public class UvDaoImpl implements UvDao{

	private DaoFactory daoFactory;
	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM Uv WHERE idUv = ?";
	private static final String SQL_INSERT_UV = "INSERT INTO Session VALUES(?, ?, ?, ?, ?, ?);";
	
	/*Constructeur */
	
	public UvDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	/*Implementation des méthodes */
	
	public void creer (Uv uv) throws DAOException{
			int 	idUv = uv.getId();
			String 	nom = uv.getNom();
			int 	duree = uv.getDuree();
			int 	nbPlaceMin = uv.getNombrePlaceMin();
			int 	nbPlaceMax = uv.getNombrePlaceMax();
			String 	lieu = uv.getLieu();
			int     statut;
		     
		     Connection connexion = null; 
		     PreparedStatement preparedStatement = null; 
		     
		     try{
		     connexion = daoFactory.getConnection();
		     preparedStatement = DaoFactory.initialisationRequetePreparee(connexion, SQL_INSERT_UV, false, idUv, nom, duree, nbPlaceMin, nbPlaceMax, lieu);
		     statut = preparedStatement.executeUpdate(); 
		     if (statut==0){System.out.println("ok");};
		     } catch ( SQLException e ) {
	         	throw new DAOException( e );
				}
				finally {
					fermeturesSilencieuses( preparedStatement, connexion );
				}
	
	}
	
	public Uv trouver (int idUv) throws DAOException{
		Connection connexion = null;
		ResultSet resultSet = null;
		Uv uv = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connexion = daoFactory.getConnection();
			preparedStatement = DaoFactory.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, idUv );
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()){
				uv = new Uv();
				uv.setId(idUv);
				uv.setNom(resultSet.getString("nomUv"));
				uv.setDuree(resultSet.getInt("duree"));
				uv.setNombrePlaceMin(resultSet.getInt("nombrePlaceMin"));
				uv.setNombrePlaceMax(resultSet.getInt("nombrePlaceMax"));
				uv.setLieu(resultSet.getString("lieu"));
			}
		}	catch ( SQLException e ) {
            	throw new DAOException( e );
			}
			finally {
				fermeturesSilencieuses( resultSet, preparedStatement, connexion );
			}
		return uv;
	}
	
	
}
