package cci.caos.dao;

import java.util.List;

import cci.caos.repository.Agent;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;

public interface CandidatureDao {
	
	/**
	 * Création d'une candidature dans la DAO
	 * @param candidature - La candidature à enregistrer
	 * @throws DAOException si la création dans la DAO a provoqué une Exception
	 */
    public void creer( Candidature candidature ) throws DAOException;

    /**
     * Recherche d'une candidature dans la DAO
     * @param candidature - la candidature à rechercher
     * @return true si la candidature existe dans la DAO, sinon renvoie false
     * @throws DAOException si la recherche dans la DAO a provoqué une Exception
     */
    public boolean existe( Candidature candidature ) throws DAOException;

    /**
     * Recherche si une candidature correspondant à l'agent d'id idAgent et de session idSession
     * existe dans la DAO
     * @param idAgent - L'id d'un agent
     * @param idSession - L'id d'une session
     * @return true si une candidature existe pour cet agent et cette session, sinno renvoie false
     * @throws DAOException si la recherche dans la DAO a provoqué une Exception
     */
    public boolean existe( int idAgent, int idSession ) throws DAOException;
   
    /**
     * Liste toutes les candidatures existantes dans la DAO
     * @return liste de toutes les candidatures existantes dans la DAO
     * @throws DAOException si la recherche des candidatures existantes dans la DAO a provoqué une Exception
     */
    public List<Candidature> listerToutes() throws DAOException;

    /**
     * Liste toutes les candidatures correspondant à un agent d'id idAgent
     * @param idAgent - L'id de l'agent concerné
     * @return liste de toutes les candidatures de cet agent
     * @throws DAOException si la recherche des candidatures de cet agent dans la DAO a provoqué une Exception
     */
    public List<Candidature> listerCandidaturesParAgent( int idAgent ) throws DAOException;

    /**
     * Liste toutes les candidatures d'une session d'id idSession
     * @param idSession - L'id de la session concernée
     * @return liste de toutes les candidatures concernant cette session
     * @throws DAOException si la recherche des candidatures correspondant à cette session dans la DAO a provoqué une Exception
     */
    public List<Candidature> listerCandidaturesParSession( int idSession ) throws DAOException;

    /**
     * Met à jour les candidatures d'une session d'id idSession
     * @param idSession - L'id de la session concernée
     * @param listeCandidature - la liste des candidatures à mettre à jour
     * @throws DAOException si la mise à jour des candidatures dans la DAO a provoqué une Exception
     */
    public void mettreAJourCandidatureASession( int idSession, List<Candidature> listeCandidature ) throws DAOException;

    /**
     * Supprime une candidature présente dans la DAO
     * @param candidature - la candidature à supprimer
     * @throws DAOException si la suppression de la candidature dans la DAO a provoqué une Exception
     */
    public void supprimerCandidature( Candidature candidature ) throws DAOException;

    /**
     * Supprime une candidature d'un agent donné pour une session donnée dans la DAO
     * @param idAgent - l'id de l'agent concerné
     * @param idSession - l'id de la session concernée
     * @throws DAOException si la suppression de la candidature dans la DAO a provoqué une Exception
     */
    public void supprimerCandidature( int idAgent, int idSession ) throws DAOException;

    /**
     * Récupère une candidature dans la DAO
     * @param agent - l'agent concerné
     * @param session - la session concernée
     * @return La candidature correspondant à l'agent et à la session
     * @throws DAOException si la recherche de la candidature dans la DAO a provoqué une Exception
     */
    public Candidature trouver( Agent agent, Session session ) throws DAOException;
}
