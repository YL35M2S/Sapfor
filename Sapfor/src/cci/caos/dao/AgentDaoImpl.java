package cci.caos.dao;

import cci.caos.repository.Agent;

public class AgentDaoImpl implements AgentDao{
	
	private DaoFactory daoFactory;
	
	/*Constructeur */
	
	public AgentDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	/*Implementation des méthodes*/
	
	public void creer (Agent agent) throws DAOException{
		//TODO
	}

	public Agent trouver (String uuid) throws DAOException {
		//TODO
		return null;
	}

}
