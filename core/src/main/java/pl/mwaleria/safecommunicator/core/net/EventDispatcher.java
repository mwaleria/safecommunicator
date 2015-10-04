package pl.mwaleria.safecommunicator.core.net;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author mwaleria
 *
 * @param <O>
 *            output object class
 * @param <I>
 *            input object class
 */
public abstract class EventDispatcher<O extends Serializable, I extends Serializable> {

	private ExecutorService threadPool = Executors.newCachedThreadPool();

	public void dispatch(I input, Long senderId) {
		threadPool.execute(this.createTask(input, senderId));
	}

	public abstract SafeCommunicatorRunnable<O, I> createTask(I t, Long senderId);

}
