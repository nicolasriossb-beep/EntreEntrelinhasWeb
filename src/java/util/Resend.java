package util;

import java.net.http.*;
import java.net.URI;

/*
O corpo do email é uma string em formato JSON.
https://resend.com/docs/api-reference/emails/send-email
*/
public class Resend {
    public static HttpResponse<String> enviarEmail(String corpoEmail) throws Exception {
        String apiKey = Config.get("resend_api_key");
        String urlString = "https://api.resend.com/emails";

        HttpResponse<String> out = null;
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json; utf-8")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(corpoEmail))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            out = response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }
}
