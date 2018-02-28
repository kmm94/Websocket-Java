package bachlor.websocketlearn.Backend;

import java.util.Scanner;
import javax.websocket.DeploymentException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author karim
 */
public class Server {

    public static void main(String[] args) {
        org.glassfish.tyrus.server.Server server
                = new org.glassfish.tyrus.server.Server("localhost", 8025, "/ws", TestEndpoint.class);
        try {
            server.start();
            Scanner sc = new Scanner(System.in);
            TestEndpoint t = new TestEndpoint();
            System.out.println("enter 'q' to close the server...");
            while (!sc.nextLine().equals("q")) {
                System.out.println("write to the all clinets:");
                t.sendMessageToAll(sc.nextLine());
                System.out.println("enter 'q' to close the server...");
            }

        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }

}
