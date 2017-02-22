package cci.caos.dao;

import cci.caos.repository.Stage;

public interface StageDao {

    public int creer( Stage stage ) throws DAOException;

    public void mettreAJour( Stage stage ) throws DAOException;

    public Stage trouver( int id ) throws DAOException;

    public boolean existe( Stage stage ) throws DAOException;

}
