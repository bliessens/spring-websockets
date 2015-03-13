package be.cegeka.websocket;

import be.cegeka.hazelcast.WebSocketId;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketEvent extends ApplicationEvent {
    private WebSocketId id;

    public WebSocketEvent(WebSocketSession source) {
        super(source);
        this.id = new WebSocketId(source.getId());
    }

    public WebSocketId webSocketId() {
        return id;
    }
}
