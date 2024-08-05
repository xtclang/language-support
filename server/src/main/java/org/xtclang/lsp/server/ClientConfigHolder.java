package org.xtclang.lsp.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is holding the latest client configuration.
 */
public class ClientConfigHolder {
    private final Gson gson = new Gson();
    private static final LanguageServerContext.Key<ClientConfigHolder> CLIENT_CONFIG_HOLDER_KEY =
            new LanguageServerContext.Key<>();

    private final List<ClientConfigListener> listeners = new ArrayList<>();

    // Init ballerina client configuration with defaults
    private ClientConfig clientConfig = ClientConfig.getDefault();
    private JsonElement clientConfigJson = gson.toJsonTree(this.clientConfig).getAsJsonObject();

    private ClientConfigHolder(LanguageServerContext serverContext) {
        serverContext.put(CLIENT_CONFIG_HOLDER_KEY, this);
    }

    public static ClientConfigHolder getInstance(LanguageServerContext serverContext) {
        ClientConfigHolder lsClientConfigHolder = serverContext.get(CLIENT_CONFIG_HOLDER_KEY);
        if (lsClientConfigHolder == null) {
            lsClientConfigHolder = new ClientConfigHolder(serverContext);
        }

        return lsClientConfigHolder;
    }

    public ClientConfig getConfig() {
        return this.clientConfig;
    }

    /**
     * Returns current client configuration.
     *
     * @return {@link T}
     */
    public <T> T getConfigAs(Class<T> type) {
        return gson.fromJson(clientConfigJson, (Type) type);
    }

    /**
     * Register config listener.
     *
     * @param listener Config change listener to register
     */
    public void register(ClientConfigListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregister config listener.
     *
     * @param listener Config change listener to unregister
     */
    public void unregister(ClientConfigListener listener) {
        listeners.remove(listener);
    }

    /**
     * Update current client configuration.
     *
     * @param newClientConfigJson {@link JsonElement} new configuration
     */
    public void updateConfig(JsonElement newClientConfigJson) {
        ClientConfig newClientConfig = gson.fromJson(newClientConfigJson, ClientConfig.class);
        // Update config
        ClientConfig oldClientConfig = this.clientConfig;
        JsonElement oldClientConfigJson = this.clientConfigJson;
        this.clientConfig = newClientConfig;
        this.clientConfigJson = newClientConfigJson;
        // Notify listeners
        this.listeners.forEach(listener -> listener.didChangeConfig(oldClientConfig, newClientConfig));
        this.listeners.forEach(listener -> listener.didChangeJson(oldClientConfigJson, newClientConfigJson));
    }
}
