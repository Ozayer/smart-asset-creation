import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class TestVinApi {
    public static void main(String[] args) throws Exception {
        String vin = "1HGBH41JXMN109186";
        String url = "https://vpic.nhtsa.dot.gov/api/vehicles/DecodeVin/" + vin + "?format=json";
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
            
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("API Response:");
        System.out.println(response.body());
    }
}