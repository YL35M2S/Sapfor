package cci.caos.dao;

import java.sql.Connection;

public abstract class Dao {
    protected Connection connect = null;

    public Dao( Connection conn ) {
        this.connect = conn;
    }
}
