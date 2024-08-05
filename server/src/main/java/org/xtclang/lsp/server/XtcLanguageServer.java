package org.xtclang.lsp.server;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class XtcLanguageServer implements LanguageServer, LanguageClientAware {

    private final Logger logger = LoggerFactory.getLogger(XtcLanguageServer.class);
    private LanguageClient client;
    private int exitCode;

    @Override
    public void connect(final LanguageClient client) {
        this.client = client;
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(final InitializeParams params) {
        final ServerCapabilities capabilities = new ServerCapabilities();
        capabilities.setTextDocumentSync(TextDocumentSyncKind.Full);

        final InitializeResult result = new InitializeResult(capabilities);
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        exitCode = 0;
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void exit() {
        logger.info("Server is exiting with code {}", exitCode);
        System.exit(exitCode);
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return new TextDocumentService() {
            @Override
            public void didOpen(final DidOpenTextDocumentParams params) {
                client.logMessage(new MessageParams(MessageType.Info, "Document opened: " + params.getTextDocument().getUri()));
            }

            @Override
            public void didChange(final DidChangeTextDocumentParams params) {
                client.logMessage(new MessageParams(MessageType.Info, "Document changed: " + params.getTextDocument().getUri()));
            }

            @Override
            public void didClose(final DidCloseTextDocumentParams params) {
                client.logMessage(new MessageParams(MessageType.Info, "Document closed: " + params.getTextDocument().getUri()));
            }

            @Override
            public void didSave(final DidSaveTextDocumentParams params) {
                client.logMessage(new MessageParams(MessageType.Info, "Document saved: " + params.getTextDocument().getUri()));
            }
        };
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return new WorkspaceService() {
            @Override
            public void didChangeConfiguration(final DidChangeConfigurationParams params) {
                client.logMessage(new MessageParams(MessageType.Info, "Workspace configuration changed."));
            }

            @Override
            public void didChangeWatchedFiles(final DidChangeWatchedFilesParams params) {
                client.logMessage(new MessageParams(MessageType.Info, "Watched files changed."));
            }
        };
    }

    public static void main(final String[] args) {
        new XtcLanguageServer().connect(null);
    }
}
