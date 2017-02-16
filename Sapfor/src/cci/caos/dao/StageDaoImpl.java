package cci.caos.dao;
import cci.caos.dao.DAOException;

import cci.caos.repository.Stage;


public class StageDaoImpl implements StageDao{

	private DaoFactory daoFactory;

	public StageDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	} 
	
	public void creer (Stage stage) throws DAOException{
		//TODO
	}
	
	public Stage trouver (String nom) throws DAOException{
		//TODO
		return null;
	}
	
}

