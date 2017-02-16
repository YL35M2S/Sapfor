package cci.caos.dao;

import cci.caos.repository.Aptitude;

public class AptitudeDaoImpl implements AptitudeDao{
	
	private DaoFactory daoFactory;

	/*Constructeur */

	public AptitudeDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	} 
	
	/*Implémentation des méthodes */
	
	public void creer (Aptitude aptitude) throws DAOException{
		//TODO
	}
	
	public Aptitude trouver(String nom) throws DAOException{
		//TODO
		return null;
	}
	

}
