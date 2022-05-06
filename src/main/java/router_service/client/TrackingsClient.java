package router_service.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import router_service.model.Tracking;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TrackingsClient {

    private static final String SERVER_ADDRESS = "http://localhost:8080";
    private static final Gson gson = new Gson();

    public static Tracking getTracking(long id) {
        Tracking tracking = new Tracking();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/trackings/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            tracking = gson.fromJson(response.body(), Tracking.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return tracking;
    }

    public static List<Tracking> getAllTrackings() {
        List<Tracking> trackings = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/trackings/"))
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            trackings = gson.fromJson(response.body(), new TypeToken<List<Tracking>>(){}.getType());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return trackings;
    }

    public static Tracking createTracking(Tracking tracking) {
        Tracking created = new Tracking();
        try {
            Gson GSON = new GsonBuilder().create();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/trackings/"))
                    .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(tracking)))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            created = gson.fromJson(response.body(), Tracking.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return created;
    }

    public static Tracking updateTracking(long id, Tracking tracking) {
        Tracking created = new Tracking();
        try {
            Gson GSON = new GsonBuilder().create();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/trackings/" + id))
                    .PUT(HttpRequest.BodyPublishers.ofString(GSON.toJson(tracking)))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            created = gson.fromJson(response.body(), Tracking.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return created;
    }

    public static Tracking deleteTracking(long id) {
        Tracking tracking = new Tracking();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/trackings/" + id))
                    .DELETE()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            tracking = gson.fromJson(response.body(), Tracking.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return tracking;
    }

}
