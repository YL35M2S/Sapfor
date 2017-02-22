package cci.caos.dao;

import cci.caos.repository.Session;

public interface SessionDao {

    public int creer( Session session ) throws DAOException;

    public void mettreAJour( Session session ) throws DAOException;

    public boolean existe( Session session ) throws DAOException;

    public Session trouver( int id ) throws DAOException;

}
