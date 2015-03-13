package be.cegeka.websocket;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketOpenedEvent extends WebSocketEvent {
    private String sessionId;

    public WebSocketOpenedEvent(WebSocketSession source) {
        super(source);
        this.sessionId = source.getId();
    }
}
