package cci.caos.dao;

import cci.caos.dao.exception.DAOException;
import cci.caos.repository.Aptitude;

public interface AptitudeDao {
		
	/**
	* Création d'une aptitude dans la DAO
	* @param aptitude - l'aptitude a enregistrer
	* @return l'id de l'aptitude enregistrée
	* @throws DAOException si la création dans la DAO a provoqué une Exception
	*/
    public int creer( Aptitude aptitude ) throws DAOException;

    /**
     * Recherche d'une aptitude par son id dans la DAO
     * @param id - L'id de l'aptitude à rechercher dans la DAO
     * @return Un objet Aptitude correspondant à l'aptitude recherchée.
     * Si l'aptitude n'est pas présente dans la DAO, l'objet Aptitude est initialisé à null
     * @throws DAOException si la mise à jour dans la DAO a provoqué une Exception
     */
    public Aptitude trouver( int id ) throws DAOException;

    /**
     * Mise à jour d'une aptitude dans la DAO
     * @param aptitude - l'aptitude à mettre à jour dans la DAO
     * @throws DAOException si la mise à jour dans la DAO a provoqué une Exception
     */
    public void mettreAJour( Aptitude aptitude ) throws DAOException;

    /**
     * Recherche d'une aptitude dans la DAO
     * @param aptitude - l'aptitude à rechercher dans la DAO
     * @return true si l'aptitude est présente dans la DAO, sinon renvoie false
     * @throws DAOException si la recherche dans la DAO a provoqué une Exception
     */
    public boolean existe( Aptitude aptitude ) throws DAOException;
}