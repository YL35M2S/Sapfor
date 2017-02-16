package cci.caos.dao;

import cci.caos.repository.Agent;

public interface AgentDao {
	
	void creer (Agent agent) throws DAOException;
	
	Agent trouver (String uuid) throws DAOException;
	
}
