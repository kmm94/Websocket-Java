package bachlor.websocketlearn.Backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author karim
 */
@ServerEndpoint("/chat")
public class TestEndpoint {

    static Map<String, Client> sessions = new HashMap();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("someone connected: " + session.getId());
        Client clinet = new Client(session);
        this.sessions.put(session.getId(), clinet);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(session.getId() + " Has disconnected");
        sessions.remove(session.getId());
    }

    @OnMessage
    public void onTextMessage(String message) {
        System.out.println("Besked: " + message);
    }

    public void sendMessageToAll(String message) {
        try {
            for (Client c : sessions.values()) {
                if(c.getSession().isOpen())
                c.getSession().getBasicRemote().sendText(message);
            }
        } catch (IOException ex) {
            Logger.getLogger(TestEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
