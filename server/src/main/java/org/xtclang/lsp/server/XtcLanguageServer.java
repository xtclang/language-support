package org.xtclang.lsp.server;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.*;

import java.util.concurrent.CompletableFuture;

public class XtcLanguageServer implements LanguageServer, LanguageClientAware {

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
}