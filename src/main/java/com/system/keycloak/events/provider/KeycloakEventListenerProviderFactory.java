package com.system.keycloak.events.provider;

import com.system.keycloak.events.application.RegistrationEventProcessor;
import com.system.keycloak.events.configuration.KafkaPublisherSettings;
import com.system.keycloak.events.configuration.KafkaPublisherSettingsLoader;
import com.system.keycloak.events.infrastructure.KafkaIdentityEventPublisher;
import com.system.keycloak.events.ports.IdentityEventPublisher;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public final class KeycloakEventListenerProviderFactory implements EventListenerProviderFactory {
    private static final String LISTENER_ID = "event-listener";

    private IdentityEventPublisher publisher;
    private RegistrationEventProcessor processor;

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new KeycloakEventListenerProvider(session, processor);
    }

    @Override
    public void init(Config.Scope config) {
        KafkaPublisherSettings settings = KafkaPublisherSettingsLoader.loadFromEnvironment();
        publisher = KafkaIdentityEventPublisher.create(settings);
        processor = new RegistrationEventProcessor(publisher);
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
        if (publisher != null) {
            publisher.close();
        }
    }

    @Override
    public String getId() {
        return LISTENER_ID;
    }
}
