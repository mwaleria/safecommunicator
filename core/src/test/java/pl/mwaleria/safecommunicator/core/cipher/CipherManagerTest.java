/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mwaleria.safecommunicator.core.cipher;

import static org.junit.Assert.assertEquals;

import java.security.KeyPair;
import java.util.Random;

import org.junit.Test;

/**
 *
 * @author waler
 */
public class CipherManagerTest {

	@Test
	public void testCipher() throws CryptoException {
		SafeCommunicatorKeyGenerator s = new SafeCommunicatorKeyGenerator();
		KeyPair keyPair = s.generateKeyPair("marcin");
		CipherManager cipherManager = new CipherManager();

		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			int size = random.nextInt(10000) + 1999;
			byte[] array = generateByte(size);
			byte[] enryptedArray = cipherManager.encrypt(array, keyPair.getPublic());
			byte[] decryptedArray = cipherManager.decrypt(enryptedArray, keyPair.getPrivate());
			myAssertEquals(array, decryptedArray);
		}

	}

	private byte[] generateByte(int size) {
		byte[] array = new byte[size];
		Random random = new Random();
		random.nextBytes(array);
		return array;
	}

	public void myAssertEquals(byte[] array1, byte[] array2) {
		assertEquals(array1.length, array2.length);
		for (int i = 0; i < array1.length; i++) {
			assertEquals(array1[i], array2[i]);
		}
	}

}
