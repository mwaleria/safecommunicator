/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mwaleria.safecommunicator.core.cipher;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang.ArrayUtils;
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

    public byte[] newEncrypt(byte[] input, PublicKey publicKey) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(Constants.CIPHER_SYMETRIC_ALGORITHM);
            generator.init(Constants.CIPHER_SYMETRIC_SIZE);
            SecretKey key = generator.generateKey();
            byte[] aesKey = key.getEncoded();
            byte[] cryptedAesKey = this.encrypt(aesKey, publicKey);
            Cipher cipher = Cipher.getInstance(Constants.CIPHER_SYMETRIC_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return ArrayUtils.addAll(cryptedAesKey, cipher.doFinal(input));
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CipherManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public byte[] newDecrypt(byte[] input, PrivateKey privateKey) {
        try {
            byte[] aesKeyEncypted = ArrayUtils.subarray(input, 0, 256);

            byte[] aesKeyDecrypted = this.decrypt(aesKeyEncypted, privateKey);
            SecretKey originalKey = new SecretKeySpec(aesKeyDecrypted, 0, aesKeyDecrypted.length, Constants.CIPHER_SYMETRIC_ALGORITHM);
            Cipher cipher = Cipher.getInstance(Constants.CIPHER_SYMETRIC_INSTANCE);
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, originalKey, ivspec);
            return cipher.doFinal(ArrayUtils.subarray(input, 256, input.length));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(CipherManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private byte[] crypt(int mode, Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(Constants.CIPHER_ASYMETRIC_INSTANCE);
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
