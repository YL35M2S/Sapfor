package cci.caos.dao;

public class DAOExceptionConfiguration extends RuntimeException {
	private static final long serialVersionUID = 1L;
	/*
     * Constructeurs
     */
    public DAOExceptionConfiguration( String message ) {
        super( message );
    }

    public DAOExceptionConfiguration( String message, Throwable cause ) {
        super( message, cause );
    }

    public DAOExceptionConfiguration( Throwable cause ) {
        super( cause );
    }
}