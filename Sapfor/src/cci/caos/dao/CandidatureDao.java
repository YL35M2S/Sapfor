package cci.caos.dao;

import java.util.List;

import cci.caos.repository.Agent;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;

public interface CandidatureDao {

    public void creer( Candidature candidature ) throws DAOException;

    public boolean existe( Candidature candidature ) throws DAOException;

    public boolean existe( int idAgent, int idSession ) throws DAOException;

    public List<Candidature> listerToutes() throws DAOException;

    public List<Candidature> listerCandidaturesParAgent( int idAgent ) throws DAOException;

    public List<Candidature> listerCandidaturesParSession( int idSession ) throws DAOException;

    public void mettreAJourCandidatureASession( int idSession, List<Candidature> listeCandidature ) throws DAOException;

    public void supprimerCandidature( Candidature candidature ) throws DAOException;

    public void supprimerCandidature( int idAgent, int idSession ) throws DAOException;

    public Candidature trouver( Agent agent, Session session ) throws DAOException;
}
