package cci.caos.dao;

import cci.caos.repository.Agent;

public interface AgentDao {
	
	void creer (Agent agent) throws DAOException;
	
	Agent trouver (int id) throws DAOException;
	
	boolean existe (int id) throws DAOException;
	
}
