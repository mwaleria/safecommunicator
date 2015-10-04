package pl.mwaleria.safecommunicator.core.cipher;

public class CryptoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CryptoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CryptoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CryptoException(String message) {
		super(message);
	}

	public CryptoException(Throwable cause) {
		super(cause);
	}

}
