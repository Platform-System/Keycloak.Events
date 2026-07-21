package com.system.keycloak.events.configuration;

public record KafkaPublisherSettings(
    String bootstrapServers,
    String topic,
    String clientId,
    String securityProtocol,
    String saslMechanism,
    String saslUsername,
    String saslPassword
) {
}
