package cci.caos.dao;

import cci.caos.repository.Uv;

public class UvDaoImpl implements UvDao{

	private DaoFactory daoFactory;
	
	/*Constructeur */
	
	public UvDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	/*Implementation des méthodes */
	
	public void creer (Uv uv) throws DAOException{
		//TODO
	}
	
	
}
