package cci.caos.dao;

import java.util.List;

import cci.caos.dao.exception.DAOException;
import cci.caos.repository.Session;

public interface SessionDao {
    /**
     * Creation d'une session dans la DAO
     * 
     * @param session
     *            la session a creer
     * @return l'Id de la session creee
     * @throws DAOException
     *             si la creation dans la DAO a provoque une Exception
     */
    public int creer( Session session ) throws DAOException;

    /**
     * Mise a jour d'une session dans la DAO
     * 
     * @param session
     *            la session a mettre a jour
     * @throws DAOException
     *             si la mise a jour dans la DAO a provoque une Exception
     */
    public void mettreAJour( Session session ) throws DAOException;

    /**
     * Recherche si une session existe dans la DAO
     * 
     * @param session
     *            la session dont on cherche a verifier l'existence
     * @return Vrai si la session existe sinon false
     * @throws DAOException
     *             si la recherche dans la DAO a provoque une Exception
     */
    public boolean existe( Session session ) throws DAOException;

    /**
     * Liste toutes les sessions existantes dans la DAO
     * 
     * @return Liste des sessions existantes
     * @throws DAOException
     *             si la recherche des sessions dans la DAO a provoque une
     *             Exception
     */
    public List<Session> listerToutes() throws DAOException;

    /**
     * Recherche d'une session dans la DAO a partir de son id
     * 
     * @param id
     *            l'id de la session recherche
     * @return la session recherchee
     * @throws DAOException
     *             si la recherche dans la DAO a provoque une Exception
     */
    public Session trouver( int id ) throws DAOException;
}
