
package pl.mwaleria.safecommunicator.core;

/**
 *
 * @author mwaleria
 */
public class Constants {

	private Constants() {
	}

	private static final int BITS_IN_BYTE = 8;

	public static final int NETWORK_MESSAGE_SIZE = 4;
	public static final String CIPHER_ASYMETRIC_ALGORITHM = "RSA";
	public static final String CIPHER_ASYMETRIC_INSTANCE = "RSA/ECB/PKCS1Padding";
	public static final String CIPHER_SYMETRIC_ALGORITHM = "AES";
	public static final String CIPHER_SYMETRIC_INSTANCE = "AES/ECB/PKCS5Padding";

	public static final int CIPHER_ASYMETRIC_SIZE = 2048;
	public static final int CIPHER_ASYMETRIC_SIZE_IN_BYTES = CIPHER_ASYMETRIC_SIZE / BITS_IN_BYTE;
	public static final int CIPHER_SYMETRIC_SIZE = 128;
	public static final int CIPHER_SYMETRIC_SIZE_IN_BYTES = CIPHER_SYMETRIC_SIZE / BITS_IN_BYTE;

	public static final Long SERVER_SENDER_ID = 0L;

}
