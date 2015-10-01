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
public class ServerResponse implements Serializable {

    private ServerResponseType responseType;

    private Serializable value;

    public ServerResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ServerResponseType responseType) {
        this.responseType = responseType;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }
    
    
}
