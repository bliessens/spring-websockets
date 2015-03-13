package be.cegeka.client;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class WebSocketClientHandler {

    @OnWebSocketConnect
    public void onOpen(Session session) {
        System.out.println("onOpen()");

    }

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) {
        System.out.println("onMessage(" + msg + ")");
    }

    @OnWebSocketClose
    public void close(Session session, int i, String msg) {
        System.out.println("onClose()");
    }

}
