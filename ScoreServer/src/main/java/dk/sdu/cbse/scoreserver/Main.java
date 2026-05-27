package dk.sdu.cbse.scoreserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final int PORT = 8081;
    private static final AtomicInteger score = new AtomicInteger(0);
    private static final AtomicInteger lives = new AtomicInteger(3);

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/score", Main::handleScore);
        server.createContext("/lives", Main::handleLives);

        server.start();
        System.out.println("ScoreServer listening on http://localhost:" + PORT);
    }

    private static void handleScore(HttpExchange ex) throws IOException {
        switch (ex.getRequestMethod()) {
            case "GET" -> respond(ex, 200, String.valueOf(score.get()));
            case "POST" -> {
                int points = readInt(ex);
                int newScore = score.addAndGet(points);
                respond(ex, 200, String.valueOf(newScore));
            }
            default -> respond(ex, 405, "Method not allowed");
        }
    }

    private static void handleLives(HttpExchange ex) throws IOException {
        switch (ex.getRequestMethod()) {
            case "GET" -> respond(ex, 200, String.valueOf(lives.get()));
            case "PUT" -> {
                lives.set(readInt(ex));
                respond(ex, 200, String.valueOf(lives.get()));
            }
            default -> respond(ex, 405, "Method not allowed");
        }
    }

    private static int readInt(HttpExchange ex) throws IOException {
        String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        return Integer.parseInt(body.trim());
    }

    private static void respond(HttpExchange ex, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }
}
