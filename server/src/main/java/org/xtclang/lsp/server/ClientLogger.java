package org.xtclang.lsp.server;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.nio.charset.StandardCharsets;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;

public class ClientLogger {
    private static final LanguageServerContext.Key<ClientLogger> CLIENT_LOGGER_KEY =
            new LanguageServerContext.Key<>();

    private LanguageClient languageClient;
    private ClientConfigHolder configHolder;
    private boolean isInitializedOnce;

    public static ClientLogger getInstance(final LanguageServerContext serverContext) {
        final var logger = serverContext.get(CLIENT_LOGGER_KEY);
        return logger != null ? logger : new ClientLogger(serverContext);
    }

    private ClientLogger(final LanguageServerContext serverContext) {
        serverContext.put(CLIENT_LOGGER_KEY, this);
    }

    /**
     * Initializes the client logger.
     *
     * @param languageClient {@link LanguageClient}
     */
    public void initialize(LanguageClient languageClient, LanguageServerContext serverContext) {
        this.languageClient = languageClient;
        this.isInitializedOnce = true;
        this.configHolder = ClientConfigHolder.getInstance(serverContext);
    }

    public void notifyUser(String operation, Throwable error) {
        if (!this.isInitializedOnce) {
            return;
        }
        if (languageClient != null) {
            languageClient.showMessage(
                    new MessageParams(MessageType.Error, operation + " failed, " + error.getMessage()));
        }
    }

    /**
     * Logs the error message through the LSP protocol.
     *
     * @param message    log message
     * @param error      {@link Throwable}
     * @param identifier text document
     * @param pos        pos
     */
    public void logError(Operation operation, String message, Throwable error, TextDocumentIdentifier identifier, Position... pos) {
        if (!this.isInitializedOnce || this.languageClient == null) {
            return;
        }
        ClientConfig config = this.configHolder.getConfig();
        /*
        if (config.isEnableTelemetry()) {
            this.languageClient.telemetryEvent(LSErrorTelemetryEvent.from(operation, message, error));
        }*/
        String details = getErrorDetails(identifier, error, pos);
        if (config.isDebugLogEnabled()) {
            var baos = new ByteArrayOutputStream();
            var ps = new PrintStream(baos, true, StandardCharsets.UTF_8);
            error.printStackTrace(ps);
            this.languageClient.logMessage(
                    new MessageParams(MessageType.Error, message + " " + details + "\n" + baos));
        }
    }

    /**
     * Logs the trace message through the LSP protocol.
     *
     * @param message log message
     */
    public void logTrace(String message) {
        if (!this.isInitializedOnce) {
            return;
        }
        if (this.configHolder.getConfig().isTraceLogEnabled() && this.languageClient != null) {
            this.languageClient.logMessage(new MessageParams(MessageType.Info, message));
        }
    }

    /**
     * Logs a warning log through LSP protocol. Logs only when trace logs are enabled.
     *
     * @param message log message
     */
    public void logWarning(String message) {
        if (!this.isInitializedOnce) {
            return;
        }
        if (this.configHolder.getConfig().isTraceLogEnabled() && this.languageClient != null) {
            this.languageClient.logMessage(new MessageParams(MessageType.Warning, message));
        }
    }

    /**
     * Logs an info message through the LSP protocol.
     *
     * @param message log message
     */
    public void logMessage(String message) {
        if (!this.isInitializedOnce) {
            return;
        }
        if (this.languageClient != null) {
            this.languageClient.logMessage(new MessageParams(MessageType.Log, message));
        }
    }

    /**
     * Sends a telemetry event to the client. Though this is doesn't do any logging directly, sending telemetry events
     * via the client is related to this context.
     */
    /*
    public void telemetryEvent(LSTelemetryEvent event) {
        if (!this.isInitializedOnce || this.languageClient == null) {
            return;
        }

        if (this.configHolder.getConfig().isEnableTelemetry()) {
            this.languageClient.telemetryEvent(event);
        }
    }*/

    private static String getErrorDetails(TextDocumentIdentifier identifier, Throwable error, Position... position) {
        String msg = error.getMessage();
        StringBuilder result = new StringBuilder("{");
        if (identifier != null) {
            result.append("uri: '").append(identifier.getUri().replaceFirst("file://", "")).append("'");
        }
        if (position != null && position.length > 0 && position[0] != null) {
            if (position.length == 2) {
                // Range
                result.append(", [").append(position[0].getLine() + 1)
                        .append(":").append(position[0].getCharacter() + 1).append("]");
                result.append("- [").append(position[1].getLine() + 1)
                        .append(":").append(position[1].getCharacter() + 1).append("]");
            } else {
                // Position
                result.append(", [").append(position[0].getLine() + 1)
                        .append(":").append(position[0].getCharacter() + 1).append("]");
            }
        }
        if (msg != null && !msg.isEmpty()) {
            result.append(", error: '").append(msg);
        } else {
            result.append(", error: '").append(error.toString());
            for (StackTraceElement elm : error.getStackTrace()) {
                if (elm.getClassName().startsWith("org.wso2.")) {
                    result.append(", ").append(elm.toString());
                    break;
                }
            }
        }
        result.append("'}");
        return result.toString();
    }
}
