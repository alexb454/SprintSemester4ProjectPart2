package httpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.Scanner;

public class MainHttpClientMembership {
    private static Scanner scanner;
    private static int input;
    private static int get;
    private static int deleted;
    private static int put;
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
                    httpPostMembership();
                    break;
                case 2:
                    httpGetMembership();
                    break;
                case 3:
                    httpPutMembership();
                    break;
                case 4:
                    httpDeleteMembership();
                    break;
            }
        }
    }
    public static void httpPostMembership() throws IOException, InterruptedException {
        Map<Object, Object> people = new HashMap<>();
        people.put("personId", 3);
        people.put("startDate", "12/12/2000");
        people.put("duration", "12/12/2008");
        people.put("membershipTypeId", 1);
        people.put("currentTournamentId", 1);
        people.put("pastTournamentId", 2);
        people.put("upcomingTournamentId", 3);

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted.writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println("Posted Membership : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpGetMembership() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the id number: ");
        get = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership/" + get))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("The Membership you requested : " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void httpPutMembership() throws IOException, InterruptedException {
        System.out.println("Type Id of the person to update: ");
        put = scanner.nextInt();
        Map<Object, Object> people = new HashMap<>();
        people.put("personId", 6);
        people.put("startDate", "12/12/2004");
        people.put("duration", "12/12/2016");
        people.put("membershipTypeId", 1);
        people.put("currentTournamentId", 2);
        people.put("pastTournamentId", 5);
        people.put("upcomingTournamentId", 3);

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership/" + put))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                System.out.println("Updated Membership : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpDeleteMembership() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type Id of the membership to delete: ");
        deleted = scanner.nextInt();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership/" + deleted))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Deleted Membership of " + deleted + " successful");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
