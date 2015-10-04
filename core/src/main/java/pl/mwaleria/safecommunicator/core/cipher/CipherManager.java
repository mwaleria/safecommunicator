/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mwaleria.safecommunicator.core.cipher;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.ArrayUtils;

import pl.mwaleria.safecommunicator.core.Constants;

/**
 *
 * @author mwaleria
 */
public class CipherManager {

	private byte[] encryptAsymetric(byte[] input, PublicKey publicKey) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return this.cryptAsymetric(Cipher.ENCRYPT_MODE, publicKey, input);
	}

	private byte[] decryptAsymetric(byte[] input, PrivateKey privateKey) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return this.cryptAsymetric(Cipher.DECRYPT_MODE, privateKey, input);
	}

	public byte[] encrypt(byte[] input, PublicKey publicKey) throws CryptoException {
		try {
			KeyGenerator generator = KeyGenerator.getInstance(Constants.CIPHER_SYMETRIC_ALGORITHM);
			generator.init(Constants.CIPHER_SYMETRIC_SIZE);
			SecretKey key = generator.generateKey();
			byte[] aesKey = key.getEncoded();
			byte[] cryptedAesKey = this.encryptAsymetric(aesKey, publicKey);
			Cipher cipher = Cipher.getInstance(Constants.CIPHER_SYMETRIC_INSTANCE);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return ArrayUtils.addAll(cryptedAesKey, cipher.doFinal(input));
		} catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException ex) {
			Logger.getLogger(CipherManager.class.getName()).log(Level.SEVERE, null, ex);
			throw new CryptoException(ex);
		}
	}

	public byte[] decrypt(byte[] input, PrivateKey privateKey) throws CryptoException {
		try {
			byte[] aesKeyEncypted = ArrayUtils.subarray(input, 0, Constants.CIPHER_ASYMETRIC_SIZE_IN_BYTES);
			byte[] aesKeyDecrypted = this.decryptAsymetric(aesKeyEncypted, privateKey);
			SecretKey originalKey = new SecretKeySpec(aesKeyDecrypted, 0, aesKeyDecrypted.length,
					Constants.CIPHER_SYMETRIC_ALGORITHM);
			Cipher cipher = Cipher.getInstance(Constants.CIPHER_SYMETRIC_INSTANCE);
			cipher.init(Cipher.DECRYPT_MODE, originalKey);
			return cipher.doFinal(ArrayUtils.subarray(input, Constants.CIPHER_ASYMETRIC_SIZE_IN_BYTES, input.length));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException ex) {
			Logger.getLogger(CipherManager.class.getName()).log(Level.SEVERE, null, ex);
			throw new CryptoException(ex);
		}
	}

	private byte[] cryptAsymetric(int mode, Key key, byte[] data) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(Constants.CIPHER_ASYMETRIC_INSTANCE);
		cipher.init(mode, key);
		return cipher.doFinal(data);
	}
}
