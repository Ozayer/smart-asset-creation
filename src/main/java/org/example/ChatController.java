package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    @Autowired
    private ChatAssistantService chatService;
    
    public static class ChatRequest {
        private String sessionId;
        private String message;
        private String state;
        
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
    }
    
    @PostMapping("/start")
    public ResponseEntity<ChatAssistantService.ChatResponse> startChat() {
        String sessionId = UUID.randomUUID().toString();
        ChatAssistantService.ChatResponse response = chatService.processMessage(
            sessionId, "", ChatAssistantService.ChatState.WELCOME
        );
        response.getData().put("sessionId", sessionId);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/message")
    public ResponseEntity<ChatAssistantService.ChatResponse> sendMessage(@RequestBody ChatRequest request) {
        ChatAssistantService.ChatState currentState = ChatAssistantService.ChatState.valueOf(request.getState());
        ChatAssistantService.ChatResponse response = chatService.processMessage(
            request.getSessionId(), request.getMessage(), currentState
        );
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<ChatAssistantService.ChatSession> getSession(@PathVariable String sessionId) {
        ChatAssistantService.ChatSession session = chatService.getSession(sessionId);
        if (session != null) {
            return ResponseEntity.ok(session);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/complete")
    public ResponseEntity<VehicleData> completeChat(@RequestBody ChatRequest request) {
        ChatAssistantService.ChatSession session = chatService.getSession(request.getSessionId());
        if (session != null) {
            return ResponseEntity.ok(session.getVehicleData());
        }
        return ResponseEntity.notFound().build();
    }
    
    public static class ChatImageResponse {
        private String extractedVin;
        private String error;
        
        public ChatImageResponse(String extractedVin) { this.extractedVin = extractedVin; }
        public ChatImageResponse(String error, boolean isError) { this.error = error; }
        
        public String getExtractedVin() { return extractedVin; }
        public String getError() { return error; }
    }
    
    @PostMapping("/upload-image")
    public ResponseEntity<ChatImageResponse> uploadChatImage(@RequestParam("image") org.springframework.web.multipart.MultipartFile file) {
        try {
            java.io.File tempFile = java.io.File.createTempFile("chat_vin_image", ".jpg");
            file.transferTo(tempFile);
            
            String extractedText = performOCR(tempFile);
            System.out.println("Chat OCR extracted text: " + extractedText);
            
            java.util.regex.Pattern vinPattern = java.util.regex.Pattern.compile("[A-Z0-9]{17}");
            java.util.regex.Matcher matcher = vinPattern.matcher(extractedText.toUpperCase());
            
            tempFile.delete();
            
            if (matcher.find()) {
                String extractedVin = matcher.group();
                System.out.println("Chat extracted VIN: " + extractedVin);
                return ResponseEntity.ok(new ChatImageResponse(extractedVin));
            } else {
                return ResponseEntity.ok(new ChatImageResponse("No valid VIN found in image. Try a clearer image or enter VIN manually.", true));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ChatImageResponse("Error processing image: " + e.getMessage(), true));
        }
    }
    
    private String performOCR(java.io.File imageFile) {
        try {
            byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());
            String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);
            
            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
            String ocrUrl = "https://api.ocr.space/parse/image";
            
            org.springframework.util.LinkedMultiValueMap<String, Object> map = new org.springframework.util.LinkedMultiValueMap<>();
            map.add("base64Image", "data:image/jpeg;base64," + base64Image);
            map.add("apikey", "helloworld");
            map.add("language", "eng");
            
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
            
            org.springframework.http.HttpEntity<org.springframework.util.MultiValueMap<String, Object>> requestEntity = 
                new org.springframework.http.HttpEntity<>(map, headers);
            
            String response = restTemplate.postForObject(ocrUrl, requestEntity, String.class);
            
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response);
            com.fasterxml.jackson.databind.JsonNode parsedResults = root.get("ParsedResults");
            
            if (parsedResults != null && parsedResults.size() > 0) {
                return parsedResults.get(0).get("ParsedText").asText();
            }
            
            return "No text found";
        } catch (Exception e) {
            System.out.println("Chat OCR Error: " + e.getMessage());
            return "OCR processing failed: " + e.getMessage();
        }
    }
}