package cci.caos.dao;

import cci.caos.repository.Agent;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;

public interface CandidatureDao {

    public int creer( Candidature candidature ) throws DAOException;

    public Candidature trouver( Agent agent, Session session ) throws DAOException;
}
