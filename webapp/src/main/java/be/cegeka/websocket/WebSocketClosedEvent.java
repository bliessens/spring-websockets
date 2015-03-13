package be.cegeka.websocket;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketClosedEvent extends WebSocketEvent {
    private String sessionId;

    public WebSocketClosedEvent(WebSocketSession source) {
        super(source);
        this.sessionId = source.getId();
    }
}
