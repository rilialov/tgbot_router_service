package router_service.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import router_service.model.Task;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TasksClient {

    private static final String SERVER_ADDRESS = "http://localhost:8080";
    private static final Gson gson = new Gson();

    public static Task getTask(long id) {
        Task task = new Task();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/tasks/" + id))
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            task = gson.fromJson(response.body(), Task.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return task;
    }

    public static List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/tasks/"))
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            tasks = gson.fromJson(response.body(), new TypeToken<List<Task>>(){}.getType());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static Task createTask(Task task) {
        Task created = new Task();
        try {
            Gson GSON = new GsonBuilder().create();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/tasks/"))
                    .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(task)))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            created = gson.fromJson(response.body(), Task.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return created;
    }

    public static Task updateTask(long id, Task task) {
        Task created = new Task();
        try {
            Gson GSON = new GsonBuilder().create();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/tasks/" + id))
                    .PUT(HttpRequest.BodyPublishers.ofString(GSON.toJson(task)))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            created = gson.fromJson(response.body(), Task.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return created;
    }

    public static Task deleteTask(long id) {
        Task task = new Task();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SERVER_ADDRESS + "/tasks/" + id))
                    .DELETE()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            task = gson.fromJson(response.body(), Task.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return task;
    }

}
