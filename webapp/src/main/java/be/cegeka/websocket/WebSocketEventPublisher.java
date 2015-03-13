package be.cegeka.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class WebSocketEventPublisher implements ApplicationEventPublisher {

    @Autowired
    private ApplicationContext context;

    @Override
    public void publishEvent(ApplicationEvent applicationEvent) {
        context.publishEvent(applicationEvent);
    }
}
