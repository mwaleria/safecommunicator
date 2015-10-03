/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mwaleria.safecommunicator.core.cipher;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;
import org.apache.commons.lang.math.JVMRandom;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author waler
 */
public class CipherManagerTest {
    
    
    
    @Test
    public void testCipher() {
        SafeCommunicatorKeyGenerator s = new SafeCommunicatorKeyGenerator();
        KeyPair keyPair = s.generateKeyPair("marcin");
        CipherManager cipherManager = new CipherManager();
        
        byte[] array = generateByte(2323);
        byte[] enryptedArray = cipherManager.newEncrypt(array, keyPair.getPublic());
        byte[] decryptedArray = cipherManager.newDecrypt(enryptedArray, keyPair.getPrivate());
        myAssertEquals(array, decryptedArray);
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
