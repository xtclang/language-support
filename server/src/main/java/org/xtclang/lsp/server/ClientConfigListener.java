package org.xtclang.lsp.server;

import com.google.gson.JsonElement;

/**
 * Represents a ballerina client config change listener.
 */
public interface ClientConfigListener {
    /**
     * Callback method for configuration changes.
     *
     * @param oldConfig old configuration
     * @param newConfig new configuration
     */
    default void didChangeConfig(ClientConfig oldConfig, ClientConfig newConfig) {
        // do nothing
    }

    /**
     * Callback method for configuration changes.
     *
     * @param oldConfigJson old configuration json
     * @param newConfigJson new configuration json
     */
    default void didChangeJson(JsonElement oldConfigJson, JsonElement newConfigJson) {
        // do nothing
    }
}
