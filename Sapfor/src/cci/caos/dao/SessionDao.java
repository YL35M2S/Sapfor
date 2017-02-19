package cci.caos.dao;

import cci.caos.repository.Session;



public interface SessionDao {

	void creer (Session session) throws DAOException;
	
	Session trouver (int id) throws DAOException;
		
}
