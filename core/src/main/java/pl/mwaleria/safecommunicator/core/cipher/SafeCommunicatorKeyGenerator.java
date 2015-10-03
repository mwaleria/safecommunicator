package pl.mwaleria.safecommunicator.core.cipher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.mwaleria.safecommunicator.core.Constants;

/**
 *
 * @author mwaleria
 */
public class SafeCommunicatorKeyGenerator {

    public KeyPair generateKeyPair(String base) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Constants.CIPHER_ASYMETRIC_ALGORITHM);
            SecureRandom safeRandom = new SecureRandom(this.generateSeed(base));
            keyPairGenerator.initialize(Constants.CIPHER_ASYMETRIC_SIZE, safeRandom);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SafeCommunicatorKeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    private byte[] generateSeed(String base) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.nanoTime());
        sb.append(base);
        sb.append(System.nanoTime());
        return sb.toString().getBytes();
    }
}
