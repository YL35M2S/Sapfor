package cci.caos.dao;

import cci.caos.repository.Session;

public interface SessionDao {

    public int creer( Session session ) throws DAOException;

    public Session trouver( int id ) throws DAOException;

}
