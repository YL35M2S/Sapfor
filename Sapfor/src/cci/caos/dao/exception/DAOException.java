package cci.caos.dao.exception;

public class DAOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 * @param message - message d'erreur
	 */
    public DAOException( String message ) {
        super( message );
    }

    /**
     * Constructeur
     * @param message - message d'erreur
     * @param cause - l'Exception de niveau inférieur qui a provoqué l'appel de ce constructeur
     */
    public DAOException( String message, Throwable cause ) {
        super( message, cause );
    }

    /**
     * Constructeur
     * @param cause - l'Exception de niveau inférieur qui a provoqué l'appel de ce constructeur
     */
    public DAOException( Throwable cause ) {
        super( cause );
    }
}

