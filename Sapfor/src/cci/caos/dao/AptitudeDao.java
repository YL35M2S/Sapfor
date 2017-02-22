package cci.caos.dao;

import cci.caos.repository.Aptitude;

public interface AptitudeDao {

    public int creer( Aptitude aptitude ) throws DAOException;

    public Aptitude trouver( int id ) throws DAOException;

    public boolean existe( int id ) throws DAOException;
}