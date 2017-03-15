package cci.caos.dao;

import java.sql.Connection;

public abstract class Dao {
    protected Connection connect = null;

    /**
     * Constructeur
     * 
     * @param conn
     *            La connection generee par le DAOFactory
     */
    public Dao( Connection conn ) {
        this.connect = conn;
    }
}
