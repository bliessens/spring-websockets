package be.cegeka.hazelcast;

import java.io.Serializable;

public class SocketInfo implements Serializable {
    private final static long serialVersionUID = 34256789L;
    private final WebSocketId webSocketId;
    private final String uuid;

    public SocketInfo(WebSocketId webSocketId, String uuid) {

        this.webSocketId = webSocketId;
        this.uuid = uuid;
    }
}
