package cci.caos.dao;

import cci.caos.repository.Aptitude;

public interface AptitudeDao {

    public int creer( Aptitude aptitude ) throws DAOException;

    public Aptitude trouver( int id ) throws DAOException;

    public void mettreAJour( Aptitude aptitude ) throws DAOException;

    public boolean existe( Aptitude aptitude ) throws DAOException;
}