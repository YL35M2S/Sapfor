package cci.caos.dao;

import java.util.List;

import cci.caos.repository.Agent;
import cci.caos.repository.Aptitude;
import cci.caos.repository.Uv;

public interface AgentDao {

    public int creer( Agent agent ) throws DAOException;

    public void mettreAJour( Agent agent ) throws DAOException;

    public Agent trouver( int id ) throws DAOException;

    public List<Agent> listerTous() throws DAOException;

    public List<Aptitude> listerAptitudeParAgent( int id ) throws DAOException;

    public List<Uv> listerUvParAgent( int id ) throws DAOException;

    public boolean existe( Agent agent ) throws DAOException;

}
