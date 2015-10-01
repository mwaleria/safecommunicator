package pl.mwaleria.safecommunicator.core.net;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author mwaleria
 * @param <T>
 */
public abstract class EventDispatcher<T> {
    
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    
    public void dispatch(T t){
        threadPool.execute(this.createTask(t));
    }
    
    public abstract Runnable createTask(T t);

}
