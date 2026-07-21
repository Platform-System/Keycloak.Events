package com.system.keycloak.events.configuration;

public final class KafkaPublisherSettingsLoader {
    private KafkaPublisherSettingsLoader() {
    }

    public static KafkaPublisherSettings loadFromEnvironment() {
        return new KafkaPublisherSettings(
            requireEnv("KEYCLOAK_EVENTS_KAFKA_BOOTSTRAP_SERVERS"),
            requireEnv("KEYCLOAK_EVENTS_KAFKA_TOPIC"),
            readEnv("KEYCLOAK_EVENTS_KAFKA_CLIENT_ID", "keycloak-events"),
            normalizeKafkaEnum(readEnv("KEYCLOAK_EVENTS_KAFKA_SECURITY_PROTOCOL", "")),
            normalizeKafkaEnum(readEnv("KEYCLOAK_EVENTS_KAFKA_SASL_MECHANISM", "")),
            readEnv("KEYCLOAK_EVENTS_KAFKA_SASL_USERNAME", ""),
            readEnv("KEYCLOAK_EVENTS_KAFKA_SASL_PASSWORD", ""));
    }

    private static String requireEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + key);
        }

        return value;
    }

    private static String readEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null ? defaultValue : value;
    }

    private static String normalizeKafkaEnum(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        return value.trim().replace('-', '_').toUpperCase();
    }
}
