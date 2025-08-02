package com.email.writer.Service;


import com.email.writer.Model.EmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmailService {

    private  final WebClient webClient;
    private final String apikey;

    public EmailService(WebClient.Builder webClientBuilder,
                        @Value("${gemini.api.url}") String baseUrl,
                        @Value("${gemini.api.key}") String apikey) {

        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.apikey = apikey;
    }

    public String generateEmailReply(EmailRequest emailRequest) {
        //build prompt
        String prompt=buildPrompt(emailRequest);

        //prepare raw json body
        String requestBody= String.format("""
                {
                    "contents": [
                      {
                        "parts": [
                          {
                            "text": "%s"
                          }
                        ]
                      }
                    ]
                  }
                """, prompt);


        //send request to OpenAI API
        String response=webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/v1beta/models/gemini-2.5-flash:generateContent").build())
                .header("X-goog-api-key",apikey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block(); //blocking call to wait for the response


        //receive response from OpenAI API
        return  ExtractRepsponse(response);

    }

    private String ExtractRepsponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root=mapper.readTree(response);

            return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an email assistant. Your task is to generate a reply to the following email content(don't write subject):\n\n");
        if(emailRequest.getTone()!=null && !emailRequest.getTone().isEmpty()) {
            prompt.append("Tone: ").append(emailRequest.getTone()).append("\n\n");
        }
        prompt.append("Email Content: ").append(emailRequest.getEmailContent()).append("\n\n");
        prompt.append("Please generate a professional and appropriate response.\n");

        return prompt.toString();
    }
}
