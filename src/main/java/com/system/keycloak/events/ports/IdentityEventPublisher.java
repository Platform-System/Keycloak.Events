package com.system.keycloak.events.ports;

import com.system.keycloak.events.domain.IdentityUserRegisteredMessage;

public interface IdentityEventPublisher extends AutoCloseable {
    void publish(IdentityUserRegisteredMessage message) throws Exception;

    @Override
    void close();
}
