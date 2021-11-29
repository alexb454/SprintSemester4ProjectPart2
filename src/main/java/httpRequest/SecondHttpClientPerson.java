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

public class SecondHttpClientPerson {
    private static Scanner scanner;
    private static int input;
    private static int get;
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
                    httpPostPerson();
                    break;
                case 2:
                    httpGetPerson();
                    break;
                case 3:
                    httpPutPerson();
                    break;
                case 4:
                    httpDeletePerson();
                    break;
            }
        }
    }
    public static void httpPostPerson() throws IOException, InterruptedException {
        Map<Object, Object> people = new HashMap<>();
        people.put("firstName", "Alex");
        people.put("lastName", "Bristow");
        people.put("address", "House");
        people.put("email", "alexb@hotmail.com");
        people.put("phoneNumber", 5555555);

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted.writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/people"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println("Posted Person");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpGetPerson() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the id number: ");
        get = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/people/" + get))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("The person you requested : " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void httpPutPerson() throws IOException, InterruptedException {
        System.out.println("Type Id of the person to update: ");
        put = scanner.nextInt();
        Map<Object, Object> people = new HashMap<>();
        people.put("firstName", "Joe");
        people.put("lastName", "Phelan");
        people.put("address", "House");
        people.put("email", "joep@hotmail.com");
        people.put("phoneNumber", 5555556);

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/people/" + put))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                System.out.println("Updated person : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpDeletePerson() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type Id of the person to delete: ");
        deleted = scanner.nextInt();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/people/" + deleted))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Deleted Person of id " + deleted + " successful");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
