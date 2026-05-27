package dk.sdu.cbse.scoreclient;

import dk.sdu.cbse.common.services.IScoreService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class RemoteScoreService implements IScoreService {
    private static final String BASE_URL = "http://localhost:8081";

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public void addScore(int points) {
        send(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/score"))
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(points)))
                .build());
    }

    @Override
    public int getScore() {
        return Integer.parseInt(send(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/score"))
                .GET()
                .build()));
    }

    @Override
    public void setLives(int lives) {
        send(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/lives"))
                .PUT(HttpRequest.BodyPublishers.ofString(String.valueOf(lives)))
                .build());
    }

    @Override
    public int getLives() {
        return Integer.parseInt(send(HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/lives"))
                .GET()
                .build()));
    }

    @Override
    public void reset() {
        addScore(-getScore());
        setLives(3);
    }

    private String send(HttpRequest req) {
        try {
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (resp.statusCode() / 100 != 2) {
                throw new RuntimeException("ScoreServer returned " + resp.statusCode() + ": " + resp.body());
            }
            return resp.body();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("ScoreServer call failed: " + e.getMessage(), e);
        }
    }
}
