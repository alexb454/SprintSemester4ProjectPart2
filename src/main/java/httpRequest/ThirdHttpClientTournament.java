package httpRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ThirdHttpClientTournament {
    private static Scanner scanner;
    private static int input;
    private static int input2;
    private static int put;
    private static int deleted;
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        while (true) {
            System.out.println("Welcome!, to add press 1, to get press 2, to update press 3, " +
                    "to delete press 4, otherwise press 0 to cancel");
            scanner = new Scanner(System.in);
            input = scanner.nextInt();

            switch(input){
                case 0:
                    System.out.println("Goodbye!");
                    System.exit(0);
                case 1:
                    httpPostTournament();
                    break;
                case 2:
                    httpGetTournament();
                    break;
                case 3:
                    httpPutTournament();
                    break;
                case 4:
                    httpDeleteTournament();
                    break;
            }
        }
    }
    public static void httpPostTournament() throws IOException, InterruptedException {
        Map<Object, Object> people = new HashMap<>();
        people.put("start", "12/12/1990");
        people.put("end", "13/12/1990");
        people.put("location", "Mario golf");
        people.put("fee", 10.0);
        people.put("prize", 55.0);
        people.put("members", "Mario, Luigi, Peach");
        people.put("standings", "1.Peach, 2.Mario, 3.Luigi");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted.writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tournament"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println("Posted Tournament : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpGetTournament() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the id number: ");
        input2 = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tournament/" + input2))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("The tournament you requested : " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void httpPutTournament() throws IOException, InterruptedException {
        System.out.println("Type Id of the tournament to update: ");
        put = scanner.nextInt();
        Map<Object, Object> people = new HashMap<>();
        people.put("start", "12/12/2000");
        people.put("end", "12/12/2001");
        people.put("location", "Mario golf 2");
        people.put("fee", 18.0);
        people.put("prize", 95.0);
        people.put("members", "Mario, Luigi, Peach, Yoshi");
        people.put("standings", "1.Peach, 2.Yoshi, 3.Luigi, 4.Mario");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tournament/" + put))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                System.out.println("Updated Tournament : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpDeleteTournament() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type Id of the tournament to delete: ");
        deleted = scanner.nextInt();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tournament/" + deleted))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Deleted Tournament of id " + deleted + " successful");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
