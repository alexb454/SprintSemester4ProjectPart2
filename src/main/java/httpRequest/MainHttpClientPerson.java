package httpRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.Scanner;

public class MainHttpClientPerson {
    private static Scanner scanner;
    private static int input;
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
                    //httpPostPerson();
                    break;
                case 2:
                    httpGetPerson();
                    break;
                case 3:
                    //httpPutPerson();
                    //break;
                case 4:
                    httpDeletePerson();
                    break;
            }
        }
    }

    public static void httpGetPerson() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String peoples;
        //System.out.println("Please enter your first name: ");
        System.out.println("Please enter the last name: ");
        //System.out.println("Please enter your address: ");
        //System.out.println("Please enter your email: ");
        //System.out.println("Please enter your phone number: ");
        peoples = scanner.next();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/people/search/findByLastName?lastName=carlcarlson"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println(response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static void httpPostPerson() throws IOException, InterruptedException {
        Map<Object, Object> people = new HashMap<>();
        people.put("firstName", "");
        people.put("lastName", "");
        people.put("address", "");
        people.put("email", "");
        people.put("phoneNumber", "");
    }

    public static void httpDeletePerson() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String deleted;
        System.out.println("Type Id of the person to delete: ");
        deleted = scanner.next();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/people/search/"))
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("File Deleted : " + responseBody);
    }
}
