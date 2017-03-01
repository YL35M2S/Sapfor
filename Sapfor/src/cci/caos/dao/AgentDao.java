package cci.caos.dao;

import java.util.List;

import cci.caos.repository.Agent;
import cci.caos.repository.Aptitude;
import cci.caos.repository.Uv;

public interface AgentDao {

	/**
	 * Création d'un agent dans la DAO
	 * @param agent - l'agent à enregistrer
	 * @return L'id de l'agent enregistré
	 * @throws DAOException si la création dans la DAO a provoqué une Exception
	 */
    public int creer( Agent agent ) throws DAOException;

    /**
     * Mise a jour d'un agent dans la DAO
     * @param agent - l'agent à mettre à jour
     * @throws DAOException - si la mise à jour dans la DAO a provoqué une Exception
     */
    public void mettreAJour( Agent agent ) throws DAOException;
    
    /**
     * Recherche d'un agent dans la DAO à partir de son id
     * @param id - l'id de l'agent à rechercher
     * @return Un objet de type Agent correspondant à l'agent recherché
     * @throws DAOException si la recherche dans la DAO a provoqué une Exception
     */
    public Agent trouver( int id ) throws DAOException;

    /**
     * Liste tous les agents existants dans la DAO
     * @return Liste de tous les agents existants dans la DAO
     * @throws DAOException si la recherche des agents existants dans la DAO a provoqué une Exception
     */
    public List<Agent> listerTous() throws DAOException;

    /**
     * Liste toutes les aptitudes possédées par un agent
     * @param id - L'id de l'agent concerné
     * @return Liste des aptitudes possédées par l'agent
     * @throws DAOException si la recherche des aptitudes possédées par l'agent dans la DAO a provoqué une Exception
     */
    public List<Aptitude> listerAptitudeParAgent( int id ) throws DAOException;

    /**
     * Liste toutes les UV possédées par un agent
     * @param id - L'id de l'agent concerné
     * @return Liste des UV possédées par l'agent
     * @throws DAOException si la recherche des UV possédées par l'agent dans la DAO a provoqué une Exception
     */
    public List<Uv> listerUvParAgent( int id ) throws DAOException;
  
    /**
     * Recherche si un agent existe dans la DAO
     * @param agent - l'agent a rechercher
     * @return True si l'agent existe, sinon renvoie false
     * @throws DAOException si la recherche dans la DAO a provoqué une Exception
     */
    public boolean existe( Agent agent ) throws DAOException;

}
