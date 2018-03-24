package bachlor.websocketlearn.Backend;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
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
    public void onTextMessage(Session session, String message) {
        JsonParser jsonPaser = new JsonParser();
        JsonObject jsonObject = jsonPaser.parse(message).getAsJsonObject();
        String action = jsonObject.get("action").getAsString();
        if(action.equals("name")){
            String name = jsonObject.get("name").getAsString();
            System.out.println(session.getId() + " Now has a name: " + name);
            sessions.get(session.getId()).setName(name);
            sendMessageToAll("\nID: " + session.getId() + " \n now have a name: " + name);
        } else if(action.equals("msg")){
            String name;
            Client clinet = sessions.get(session.getId()); 
            if( clinet.getName() != null ){
                name = clinet.getName();
            } else {
                name = clinet.getSession().getId();
            }
            String msg = "\n"+ name + ": "+ jsonObject.get("message").getAsString();
            System.out.println("A message is sent: "+ msg);
            sendMessageToAll(msg);
        }
    }

    public void sendMessageToAll(String message) {
        try {
            for (Client c : sessions.values()) {
                if(c.getSession().isOpen())
                c.getSession().getBasicRemote().sendText( message);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private void sendMessageToAllFromUser(String msg, Session SenderSession) {
        try {
            for (Client c : sessions.values()) {
                if(c.getSession().isOpen() && c.getSession() != SenderSession)
                c.getSession().getBasicRemote().sendText(msg);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
