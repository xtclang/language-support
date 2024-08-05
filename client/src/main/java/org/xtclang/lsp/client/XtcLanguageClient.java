/*
 * This source file was generated by the Gradle 'init' task
 */
package org.xtclang.lsp.client;

import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;

import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

// To test the setup, first run the LanguageServerLauncher class to start the server. Then run the SimpleClient class to start the client. You should see log messages from the server indicating that a document was opened.

public class XtcLanguageClient {

    public static void main(String[] args) throws IOException {
        LanguageClient client = new LanguageClient() {
            @Override
            public void telemetryEvent(Object object) {
            }

            @Override
            public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
            }

            @Override
            public void showMessage(MessageParams messageParams) {
                System.out.println("Message from server: " + messageParams.getMessage());
            }

            @Override
            public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
                return CompletableFuture.completedFuture(new MessageActionItem("OK"));
            }

            @Override
            public void logMessage(final MessageParams messageParams) {
                System.out.println("Log from server: " + messageParams.getMessage());
            }
        };

        final Launcher<LanguageServer> launcher = LSPLauncher.createClientLauncher(client, System.in, System.out);
        final var server = launcher.getRemoteProxy();
        launcher.startListening();

        final InitializeParams initParams = new InitializeParams();
        server.initialize(initParams);

        final var textDocumentItem = new TextDocumentItem("file:///example.txt", "plaintext", 0, "Initial content");
        final var didOpenTextDocumentParams = new DidOpenTextDocumentParams(textDocumentItem);
        server.getTextDocumentService().didOpen(didOpenTextDocumentParams);
    }
}
/*

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class SimpleClient {

    public static void main(String[] args) throws IOException {
        LanguageClient client = new LanguageClient() {
            @Override
            public void telemetryEvent(Object object) {
            }

            @Override
            public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
            }

            @Override
            public void showMessage(MessageParams messageParams) {
                System.out.println("Message from server: " + messageParams.getMessage());
            }

            @Override
            public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
                return CompletableFuture.completedFuture(new MessageActionItem("OK"));
            }

            @Override
            public void logMessage(MessageParams messageParams) {
                System.out.println("Log from server: " + messageParams.getMessage());
            }
        };

        Launcher<LanguageServer> launcher = LSPLauncher.createClientLauncher(client, System.in, System.out);
        LanguageServer server = launcher.getRemoteProxy();
        launcher.startListening();

        InitializeParams initParams = new InitializeParams();
        server.initialize(initParams);

        TextDocumentItem textDocumentItem = new TextDocumentItem("file:///example.txt", "plaintext", 0, "Initial content");
        DidOpenTextDocumentParams didOpenTextDocumentParams = new DidOpenTextDocumentParams(textDocumentItem);
        server.getTextDocumentService().didOpen(didOpenTextDocumentParams);
    }
}*/
