package be.cegeka.hazelcast;

import java.io.Serializable;

public class WebSocketId implements Serializable {

    private static final long serialVersionUID = -1948235589378605710L;

    private final String id;

    public WebSocketId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WebSocketId that = (WebSocketId) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WebSocketId{");
        sb.append("id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
