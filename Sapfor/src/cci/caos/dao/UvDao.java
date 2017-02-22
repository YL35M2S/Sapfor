package cci.caos.dao;

import cci.caos.repository.Uv;

public interface UvDao {

    public int creer( Uv uv ) throws DAOException;

    public boolean existe( Uv uv ) throws DAOException;

    public void mettreAJour( Uv uv ) throws DAOException;

    public Uv trouver( int idUv ) throws DAOException;

}
