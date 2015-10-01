package pl.mwaleria.safecommunicator.client.net;

import pl.mwaleria.safecommunicator.core.net.EventDispatcher;
import pl.mwaleria.safecommunicator.core.ServerResponse;
import pl.mwaleria.safecommunicator.core.ServerResponseType;

/**
 *
 * @author mwaleria
 */
public class ClientEventDispacher extends EventDispatcher<ServerResponse>{

    @Override
    public Runnable createTask(ServerResponse t) {
        if(t.getResponseType()==ServerResponseType.ALL_USERS) {
           return new Runnable() {
               @Override
               public void run() {
                   
               }
           };
           
        }
        return null;
    }

}
