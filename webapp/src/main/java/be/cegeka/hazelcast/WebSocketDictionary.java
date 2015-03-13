package be.cegeka.hazelcast;

import be.cegeka.websocket.WebSocketClosedEvent;
import be.cegeka.websocket.WebSocketEvent;
import be.cegeka.websocket.WebSocketOpenedEvent;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.TransactionalMap;
import com.hazelcast.core.TransactionalSet;
import com.hazelcast.transaction.TransactionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WebSocketDictionary implements ApplicationListener<WebSocketEvent> {

    private final static String DICTIONARY = "dict";

    @Autowired
    private HazelcastInstance instance;

    private final Map<WebSocketId, SocketInfo> sockets = Collections.synchronizedMap(new HashMap<WebSocketId, SocketInfo>());

    @Override
    public void onApplicationEvent(WebSocketEvent event) {
        if (WebSocketOpenedEvent.class.isInstance(event)) {
            register(event);
            System.out.println("Added entry for " + event.webSocketId());
        } else if (WebSocketClosedEvent.class.isInstance(event)) {
            unRegister(event);
            System.out.println("Removed entry from " + event.webSocketId());
        }
    }

    private boolean unRegister(WebSocketEvent event) {
        return new TxRemoval(event).invoke();
    }

    private boolean register(WebSocketEvent event) {
        return new TxAddition(event).invoke();

    }


    private abstract class TxSetInvocation<T> {

        public boolean invoke() {
            boolean result;
            TransactionContext tx = instance.newTransactionContext();
            tx.beginTransaction();
            TransactionalSet<T> dictionary = tx.getSet(DICTIONARY);
            result = withSet(dictionary);
            tx.commitTransaction();
            return result;
        }

        abstract boolean withSet(TransactionalSet<T> dictionary);
    }

    private abstract class TxMapInvocation<K, V> {

        public boolean invoke() {
            boolean result;
            TransactionContext tx = instance.newTransactionContext();
            tx.beginTransaction();
            TransactionalMap<K, V> dictionary = tx.getMap(DICTIONARY);
            result = withMap(dictionary);
            tx.commitTransaction();
            return result;
        }

        abstract boolean withMap(TransactionalMap<K, V> dictionary);
    }

    private class TxRemoval extends TxMapInvocation<WebSocketId, SocketInfo> {
        private WebSocketEvent event;

        public TxRemoval(WebSocketEvent event) {
            this.event = event;
        }

        @Override
        boolean withMap(TransactionalMap<WebSocketId, SocketInfo> dictionary) {
            if (sockets.containsKey(event)) {
                sockets.remove(event);
            }
            if (dictionary.containsKey(event)) {
                dictionary.delete(event);
            }
            return true;
        }

    }

    private class TxAddition extends TxMapInvocation<WebSocketId, SocketInfo> {
        private WebSocketEvent event;

        public TxAddition(WebSocketEvent event) {
            this.event = event;
        }

        @Override
        boolean withMap(TransactionalMap<WebSocketId, SocketInfo> dictionary) {
            SocketInfo socketInfo = createSocketInfo(event);
            sockets.put(event.webSocketId(), socketInfo);
            dictionary.put(event.webSocketId(), socketInfo);
            return true;
        }

    }

    private SocketInfo createSocketInfo(WebSocketEvent event) {
        return new SocketInfo(event.webSocketId(), instance.getCluster().getLocalMember().getUuid());
    }
}
