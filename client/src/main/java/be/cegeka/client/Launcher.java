package be.cegeka.client;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Launcher {

    public Launcher() {
    }

    public void run() throws Exception {
        WebSocketClient client = new WebSocketClient();
        client.start();
        WebSocketClientHandler websocket = new WebSocketClientHandler();
        Future<Session> connect = client.connect(websocket, URI.create("ws://localhost:8080/websockets/client"), new ClientUpgradeRequest());
        Session session = connect.get(30, TimeUnit.SECONDS);
        System.out.println("Connection opened ...");
        while (true) {
            int read = System.in.read();
            if (read == 'q') {
                session.close();
                client.stop();
                System.exit(0);
            }
            session.getRemote().sendString("ping?");
            TimeUnit.SECONDS.sleep(1);
        }

    }


    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }
}
