package cci.caos.dao;

import java.util.List;

import cci.caos.dao.exception.DAOException;
import cci.caos.repository.Session;

public interface SessionDao {
	/**
	 * Creation d'une session dans la DAO
	 * @param session
	 * @return l'Id de la session creee
	 * @throws DAOException si la creation dans la DAO a provoque une Exception
	 */
    public int creer( Session session ) throws DAOException;
    /**
     * Mise a jour d'une session dans la DAO
     * @param session
     * @throws DAOException si la mise a jour dans la DAO a provoque une Exception
     */
    public void mettreAJour( Session session ) throws DAOException;
    /**
     *  Recherche si une session existe dans la DAO
     * @param session
     * @return Vrai si la session existe sinon false
     * @throws DAOException si la recherche dans la DAO a provoque une Exception
     */
    public boolean existe( Session session ) throws DAOException;
    /**
     * Liste toutes les sessions existantes dans la DAO
     * @return Liste des sessions existantes
     * @throws DAOException si la recherche des sessions dans la DAO a provoque une Exception
     */
    public List<Session> listerToutes() throws DAOException;
    /**
     *Recherche d'une session dans la DAO � partir de son id 
     * @param id
     * @return la session recherchee
     * @throws DAOException si la recherche dans la DAO a provoque une Exception
     */
    public Session trouver( int id ) throws DAOException;

}
