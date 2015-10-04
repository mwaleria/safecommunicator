package pl.mwaleria.safecommunicator.core.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.SerializationUtils;

import pl.mwaleria.safecommunicator.core.Constants;
import pl.mwaleria.safecommunicator.core.cipher.CipherManager;
import pl.mwaleria.safecommunicator.core.cipher.CryptoException;
import pl.mwaleria.safecommunicator.core.utils.CommunicationUtils;

/**
 *
 * @author mwaleria
 */
public class InputStreamHandler<T extends Serializable> implements Runnable {

	private final InputStream inputStream;
	private final PrivateKey myPrivateKey;
	private final CipherManager cipherManager;
	private final EventDispatcher<?, T> dispatcher;
	private final Long senderId;

	public InputStreamHandler(InputStream inputStream, PrivateKey myPrivateKey, EventDispatcher<?, T> dispatcher,
			Long senderId) {
		this.inputStream = inputStream;
		cipherManager = new CipherManager();
		this.dispatcher = dispatcher;
		this.myPrivateKey = myPrivateKey;
		this.senderId = senderId;
	}

	@Override
	public void run() {
		while (true) {
			byte[] responseSize = new byte[Constants.NETWORK_MESSAGE_SIZE];
			try {
				inputStream.read(responseSize);
				int responseSizeInBytes = CommunicationUtils.bytesToint(responseSize);
				byte[] encyrpteddata = new byte[responseSizeInBytes];
				inputStream.read(encyrpteddata);
				byte[] data = cipherManager.decrypt(encyrpteddata, myPrivateKey);
				T response = (T) SerializationUtils.deserialize(data);
				dispatcher.dispatch(response, senderId);
			} catch (IOException | CryptoException ex) {
				Logger.getLogger(InputStreamHandler.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}
}
