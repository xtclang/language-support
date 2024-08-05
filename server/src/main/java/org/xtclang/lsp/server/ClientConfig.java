package org.xtclang.lsp.server;

/**
 * Ballerina Client Configuration.
 */
public class ClientConfig {
    private final String home;
    private final boolean allowExperimental;
    private final boolean debugLog;
    //private final CodeLensConfig codeLens;
    private final boolean traceLog;
    private final boolean enableFileWatcher;
    private final boolean enableTelemetry;
    private final boolean enableSemanticHighlighting;

    protected ClientConfig() {
        this.home = "";

        // NOTE: Added reading environmental variables to support IntelliJ plugin
        String balDebugLog = System.getenv("BAL_DEBUG_LOG");
        String balTraceLog = System.getenv("BAL_TRACE_LOG");
        String balExperimental = System.getenv("BAL_EXPERIMENTAL");
        String balFileWatcher = System.getenv("BAL_FILE_WATCHER");
        String balTelemetry = System.getenv("BAL_TELEMETRY");

        this.allowExperimental = Boolean.parseBoolean(balExperimental);
        this.debugLog = Boolean.parseBoolean(balDebugLog);
        this.traceLog = Boolean.parseBoolean(balTraceLog);
        //this.codeLens = new CodeLensConfig();
        this.enableFileWatcher = balFileWatcher == null || Boolean.parseBoolean(balFileWatcher);
        this.enableTelemetry = balTelemetry == null || Boolean.parseBoolean(balTelemetry);
        this.enableSemanticHighlighting = true;
    }

    public static ClientConfig getDefault() {
        return new ClientConfig();
    }

    /**
     * Returns home.
     *
     * @return home
     */
    public String getHome() {
        return home;
    }

    /**
     * Returns True if allow experimental enabled, False otherwise.
     *
     * @return True if enabled, False otherwise
     */
    public boolean isAllowExperimental() {
        return allowExperimental;
    }

    /**
     * Returns True if allow debug log enabled, False otherwise.
     *
     * @return True if enabled, False otherwise
     */
    public boolean isDebugLogEnabled() {
        return debugLog;
    }

    /**
     * Returns True if trace log enabled, False otherwise.
     *
     * @return True if enabled, False otherwise
     */
    public boolean isTraceLogEnabled() {
        return traceLog;
    }

    /**
     * Returns True if file watcher enabled, False otherwise.
     *
     * @return True if enabled, False otherwise
     */
    public boolean isEnableFileWatcher() {
        return enableFileWatcher;
    }

    /**
     * Returns True if ballerina telemetry enabled, False otherwise.
     *
     * @return True if enabled, False otherwise
     */
    public boolean isEnableTelemetry() {
        return enableTelemetry;
    }

    /**
     * Returns True if ballerina semantic highlighting is enabled, False otherwise.
     *
     * @return True if enabled, False otherwise
     */
    public boolean isEnableSemanticHighlighting() {
        return enableSemanticHighlighting;
    }
}
