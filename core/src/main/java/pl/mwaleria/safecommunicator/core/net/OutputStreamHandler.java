package pl.mwaleria.safecommunicator.core.net;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.SerializationUtils;

import pl.mwaleria.safecommunicator.core.cipher.CipherManager;
import pl.mwaleria.safecommunicator.core.cipher.CryptoException;
import pl.mwaleria.safecommunicator.core.utils.CommunicationUtils;

/**
 *
 * @author mwaleria
 */
public class OutputStreamHandler<T extends Serializable> implements Runnable {

	private final OutputStream outputStream;

	private final ConcurrentLinkedQueue<T> queue;

	private final PublicKey consumerPublicKey;

	private final CipherManager cipherManager;

	public OutputStreamHandler(OutputStream outputStream, PublicKey consumerPublicKey) {
		this.outputStream = outputStream;
		queue = new ConcurrentLinkedQueue<>();
		cipherManager = new CipherManager();
		this.consumerPublicKey = consumerPublicKey;
	}

	@Override
	public void run() {
		while (true) {
			T request = queue.poll();
			if (request != null) {
				try {
					byte[] obj = cipherManager.encrypt(SerializationUtils.serialize(request), consumerPublicKey);
					outputStream.write(CommunicationUtils.intToBytes(obj.length));
					outputStream.write(obj);
				} catch (IOException | CryptoException ex) {
					Logger.getLogger(OutputStreamHandler.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	public void addTask(T req) {
		queue.offer(req);
	}

}
