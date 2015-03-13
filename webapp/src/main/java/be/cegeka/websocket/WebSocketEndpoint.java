package be.cegeka.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;

public class WebSocketEndpoint extends TextWebSocketHandler {

    @Autowired
    private WebSocketEventPublisher publisher;

    public WebSocketEndpoint() {
        System.out.println("Creating WebSocket instance");
    }

    @PostConstruct
    public void init() {
        System.out.println("Initializing WebSocket instance");
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message " + message.getPayload() + " from " + session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        publisher.publishEvent(new WebSocketOpenedEvent(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        publisher.publishEvent(new WebSocketClosedEvent(session));

    }
}



