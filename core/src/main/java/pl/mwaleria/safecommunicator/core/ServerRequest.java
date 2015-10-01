/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mwaleria.safecommunicator.core;

import java.io.Serializable;

/**
 *
 * @author mwaleria
 */
public class ServerRequest implements Serializable{
    
    private ServerRequestType requestType;
    
    private Serializable value;

    public ServerRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(ServerRequestType requestType) {
        this.requestType = requestType;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }
    
    


}
