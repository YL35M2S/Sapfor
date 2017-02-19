package cci.caos.dao;

import cci.caos.repository.Session;
import cci.caos.repository.Agent;
import cci.caos.repository.Candidature;


public interface SessionDao {

	void creer (Session session) throws DAOException;
	
	Session trouver (int id) throws DAOException;
		
}
