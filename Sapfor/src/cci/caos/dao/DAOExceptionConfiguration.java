package cci.caos.dao;

public class DAOExceptionConfiguration extends RuntimeException {
	private static final long serialVersionUID = 1L;

    /**
     * Constructeur
     * @param message - message d'erreur
     */
	public DAOExceptionConfiguration( String message ) {
        super( message );
    }
	/**
	 * Constructeur
	 * @param message - message d'erreur
	 * @param cause - l'Exception de niveau inferieur qui a provoque l'appel à ce constructeur
	 */
    public DAOExceptionConfiguration( String message, Throwable cause ) {
        super( message, cause );
    }
    /**
     * Constructeur
     * @param cause - l'Exception de niveau inferieur qui a provoque l'appel à ce constructeur
     */
    public DAOExceptionConfiguration( Throwable cause ) {
        super( cause );
    }
}