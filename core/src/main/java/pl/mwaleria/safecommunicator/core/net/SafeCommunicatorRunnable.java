package pl.mwaleria.safecommunicator.core.net;

import java.io.Serializable;

public abstract class SafeCommunicatorRunnable<OUT extends Serializable, IN extends Serializable> implements Runnable {

	protected final IN request;

	private final OutputStreamHandler<OUT> outputStreamHandler;

	public SafeCommunicatorRunnable(IN request, OutputStreamHandler<OUT> outputStreamHandler) {
		super();
		this.request = request;
		this.outputStreamHandler = outputStreamHandler;
	}

	@Override
	public void run() {
		OUT t = doTask(request);
		if (t != null) {
			outputStreamHandler.addTask(t);
		}
	}

	protected abstract OUT doTask(IN request);

}
