package cci.caos.dao;

import cci.caos.repository.Candidature;

public class CandidatureDaoImpl implements CandidatureDao{

	private DaoFactory daoFactory;

	/*Constructeur */
	public CandidatureDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	/*Implémentation des méthodes */
	
	public void creer (Candidature candidature) throws DAOException{
		//TODO
	}


	
	
	
}
