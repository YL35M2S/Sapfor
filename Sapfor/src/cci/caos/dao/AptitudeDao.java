package cci.caos.dao;

import cci.caos.repository.Aptitude;


public interface AptitudeDao {
	
	void creer (Aptitude aptitude) throws DAOException;
	
	boolean existe(String nom) throws DAOException;
	
}
