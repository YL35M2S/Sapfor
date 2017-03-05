package cci.caos.dao;

import cci.caos.repository.Stage;

public interface StageDao {
	/**
	 * Creation d'un stage dans la DAO
	 * @param stage
	 * @return l'Id du stage cree
	 * @throws DAOException si la creation dans la DAO a provoque une Exception
	 */
    public int creer( Stage stage ) throws DAOException;
    /**
     * Mise a jour d'un stage dans la DAO
     * @param stage
     * @throws DAOException si la mise a jour dans la DAO a provoque une Exception
     */
    public void mettreAJour( Stage stage ) throws DAOException;
    /**
     * Recherche d'un stage dans la DAO a partir de son id
     * @param id
     * @return le stage recherche
     * @throws DAOException si la recherche dans la DAO a provoque une Exception
     */
    public Stage trouver( int id ) throws DAOException;
    /**
     *Recherche si un stage identifie par son id existe dans la DAO 
     * @param stage
     * @return Vrai si le stage existe sinon false
     * @throws DAOException si la recherche dans la DAO a provoque une Exception
     */
    public boolean existe( Stage stage ) throws DAOException;

}
