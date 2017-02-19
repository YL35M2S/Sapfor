package cci.caos.dao;

import cci.caos.repository.Stage;

public interface StageDao {

	void creer (Stage stage) throws DAOException;
	
	public boolean existe (String nom) throws DAOException;
	
}
