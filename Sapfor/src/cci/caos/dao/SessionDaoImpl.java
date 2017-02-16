package cci.caos.dao;

import cci.caos.repository.Candidature;
import cci.caos.repository.Session;

public class SessionDaoImpl implements SessionDao {
	
	private DaoFactory daoFactory; 
	
	/*Constructeur */
	
	public SessionDaoImpl(DaoFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	/*Implementation des méthodes*/
	
	@Override
	public void creer (Session session) throws DAOException{
		//TODO
	}
	
	public Session trouver (int id) throws DAOException{
		//TODO
		return null;
	}
	
	public Session ajouterCandidat(Candidature candidature) throws DAOException{
		//TOOD
		return null;
	}
	

}
