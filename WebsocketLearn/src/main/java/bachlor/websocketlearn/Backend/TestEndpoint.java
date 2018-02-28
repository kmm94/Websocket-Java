package bachlor.websocketlearn.Backend;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;



/**
 *
 * @author karim
 */
@ServerEndpoint("/hello")
public class TestEndpoint{
    static Session session;
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("someone connected: " + session.toString());
        
        this.session = session;
    }
    
    @OnMessage
    public void onTextMessage(String message){
        System.out.println("Besked: " + message);
    }
    
    public void sendMessage(String message){
        if(this.session != null && this.session.isOpen()){
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                Logger.getLogger(TestEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
