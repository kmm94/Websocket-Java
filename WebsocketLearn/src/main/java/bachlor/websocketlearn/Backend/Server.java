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

    private static final String ADDRESS = "localhost";
    private static final int PORT_NR = 8000;

    public static void main(String[] args) {
        boolean isRunning = true;
        org.glassfish.tyrus.server.Server server
                = new org.glassfish.tyrus.server.Server(ADDRESS, PORT_NR, "/ws", TestEndpoint.class);
        try {
            server.start();
            Scanner sc = new Scanner(System.in);
            TestEndpoint t = new TestEndpoint();
            while (isRunning) {
                System.out.println("    ---- Main Menu ----");
                System.out.println("Write 'q' to close the server...");
                System.out.println("Write anything else to message the all clinets:");
                String input = sc.nextLine();
                if (input.equals("q")) {
                    System.out.println("**Terminating server**");
                    isRunning = false;
                } else {
                    t.sendMessageToAll("\nMessage from server: " + input);
                }

            }

        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
            System.out.println("---- Server is off ----");
        }
    }

}
