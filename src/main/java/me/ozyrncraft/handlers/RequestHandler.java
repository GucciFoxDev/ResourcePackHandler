package me.ozyrncraft.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.ozyrncraft.PackHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // http://example.org/<REQUEST HERE>
        String request = exchange.getRequestURI().toASCIIString().substring(1); // ASCII string replaces "%20" with " " for example. subtring removes first "/"
        String response = PackHandler.requestHandler.handle(exchange, request);
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
        writer.write(response);
        writer.close();
    }
}
