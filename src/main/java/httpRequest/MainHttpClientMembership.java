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
    private static int input2;
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
        people.put("firstName", "Alex");
        people.put("lastName", "Bristow");
        people.put("address", "House");
        people.put("email", "alexb@hotmail.com");
        people.put("phoneNumber", 5555555);
        people.put("startDate", "12/12/2000");
        people.put("duration", "12/12/2008");
        people.put("type", "normal");
        people.put("currentId", "1");
        people.put("pastId", "5");
        people.put("upcomingId", "4");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Posted Membership : " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpGetMembership() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the id number: ");
        input2 = scanner.nextInt();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership/" + input2))
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
        Map<Object, Object> people = new HashMap<>();
        people.put("firstName", "Joe");
        people.put("lastName", "Phelan");
        people.put("address", "House");
        people.put("email", "joep@hotmail.com");
        people.put("phoneNumber", "5555556");
        people.put("startDate", "12/12/2004");
        people.put("duration", "12/12/2016");
        people.put("type", "trial");
        people.put("currentId", "5");
        people.put("pastId", "2");
        people.put("upcomingId", "3");

        ObjectMapper posted = new ObjectMapper();
        String requestBody = posted
                .writeValueAsString(people);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/membership"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
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
            if (response.statusCode() == 200) {
                System.out.println("Deleted Membership of " + deleted + " successful");
                System.out.println(response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
