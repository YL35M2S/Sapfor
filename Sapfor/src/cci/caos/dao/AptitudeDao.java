package cci.caos.dao;

import cci.caos.repository.Agent;
import cci.caos.repository.Uv;
import cci.caos.repository.Aptitude;
import cci.caos.repository.Session;


public interface AptitudeDao {
	
	void creer (Aptitude aptitude) throws DAOException;
	
	Aptitude trouver(String nom) throws DAOException;
	
}
