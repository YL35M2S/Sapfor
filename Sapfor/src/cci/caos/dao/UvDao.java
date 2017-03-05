package cci.caos.dao;

import cci.caos.repository.Uv;

public interface UvDao {
	/**
	 * Creation d'une Uv dans la DAO
	 * @param uv
	 * @return l'Id de l'uv creee
	 * @throws DAOException si la creation dans la DAO a provoque une Exception
	 */
    public int creer( Uv uv ) throws DAOException;
    /**
     * Recherche si une uv existe dans la DAO
     * @param uv
     * @return Vrai si elle existe sinon false
     * @throws DAOException si la recherche dans la DAO a provoque une Exception
     */
    public boolean existe( Uv uv ) throws DAOException;
    /**
     * Mise a jour d'une Uv dans la DAO
     * @param uv
     * @throws DAOException si la mise a jour dans la DAO a provoque une Exception
     */
    public void mettreAJour( Uv uv ) throws DAOException;
    /**
     * Recherche d'une uv dans la DAO a partir de son id
     * @param idUv
     * @return l'uv recherchee
     * @throws DAOException si la recherche dans la DAO a provoque une Exception
     */
    public Uv trouver( int idUv ) throws DAOException;

}
