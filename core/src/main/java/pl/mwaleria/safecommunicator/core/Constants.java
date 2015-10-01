
package pl.mwaleria.safecommunicator.core;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import pl.mwaleria.safecommunicator.core.cipher.CipherManager;

/**
 *
 * @author mwaleria
 */
public class Constants {

    private Constants(){}
    
    public static final int NETWORK_MESSAGE_SIZE = 4;
    public static final String CIPHER_ALGORITHM = "RSA";
    public static final int CIPHER_SIZE = 2048;
    
}
