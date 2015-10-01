/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mwaleria.safecommunicator.core.cipher;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import pl.mwaleria.safecommunicator.core.Constants;

/**
 *
 * @author mwaleria
 */
public class CipherManager {

    public byte[] encrypt(byte[] input, PublicKey publicKey) {
        return this.crypt(Cipher.ENCRYPT_MODE, publicKey, input);
    }
    
    public byte[] decrypt(byte[] input, PrivateKey privateKey) {
        return this.crypt(Cipher.DECRYPT_MODE, privateKey, input);
    }

    private byte[] crypt(int mode, Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(Constants.CIPHER_ALGORITHM);
            cipher.init(mode, key);
            return cipher.doFinal(data);

        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException ex) {
            Logger.getLogger(CipherManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
