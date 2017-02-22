package cci.caos.dao;

import cci.caos.repository.Agent;

public interface AgentDao {

    public int creer( Agent agent ) throws DAOException;

    public void mettreAJour( Agent agent ) throws DAOException;

    public Agent trouver( int id ) throws DAOException;

    public boolean existe( int id ) throws DAOException;

}
