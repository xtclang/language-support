package org.xtclang.lsp.launcher;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xtclang.lsp.server.XtcLanguageServer;

public final class LanguageServerLauncher implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(LanguageServerLauncher.class);
    private final XtcLanguageServer server;
    private final Launcher<LanguageClient> launcher;

    private LanguageServerLauncher() {
        this.server = new XtcLanguageServer();
        this.launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);
    }

    @Override
    public void run() {
        logger.info("Starting language server...");
        server.connect(launcher.getRemoteProxy());
        launcher.startListening();
        logger.info("Listening.");
    }

    public static void main(final String[] args) {
        new LanguageServerLauncher().run();
    }
}
