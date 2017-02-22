package cci.caos.dao;

import cci.caos.repository.Stage;

public interface StageDao {

    public int creer( Stage stage ) throws DAOException;

    public boolean existe( int idStage ) throws DAOException;

}
