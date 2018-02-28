/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachlor.websocketlearn.Backend;

import javax.websocket.Session;

/**
 *
 * @author karim
 */
public class Client {
    
    private Session session;
    private String name;

    public Client(Session session) {
        this.session = session;
    }
    
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
