package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ChatAssistantService {
    
    @Autowired
    private VehicleSuggestionService suggestionService;
    
    @Autowired
    private HeavyMachineryDB heavyMachineryDB;
    
    public enum ChatState {
        WELCOME, VIN_INPUT_METHOD, VIN_ENTRY, DATA_REVIEW, FIELD_COMPLETION, FINAL_REVIEW
    }
    
    public static class ChatSession {
        private ChatState state = ChatState.WELCOME;
        private VehicleData vehicleData = new VehicleData();
        private String sessionId;
        
        public ChatState getState() { return state; }
        public void setState(ChatState state) { this.state = state; }
        public VehicleData getVehicleData() { return vehicleData; }
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    }
    
    public static class ChatResponse {
        private String message;
        private List<String> options = new ArrayList<>();
        private Map<String, Object> data = new HashMap<>();
        private ChatState nextState;
        
        public ChatResponse(String message) { this.message = message; }
        
        public String getMessage() { return message; }
        public List<String> getOptions() { return options; }
        public Map<String, Object> getData() { return data; }
        public ChatState getNextState() { return nextState; }
        public void setNextState(ChatState nextState) { this.nextState = nextState; }
        public void addOption(String option) { this.options.add(option); }
        public void addData(String key, Object value) { this.data.put(key, value); }
    }
    
    private Map<String, ChatSession> sessions = new HashMap<>();
    
    public ChatResponse processMessage(String sessionId, String userInput, ChatState currentState) {
        ChatSession session = sessions.computeIfAbsent(sessionId, k -> new ChatSession());
        session.setSessionId(sessionId);
        
        switch (currentState) {
            case WELCOME: return handleWelcome(session);
            case VIN_INPUT_METHOD: return handleVinInputMethod(session, userInput);
            case VIN_ENTRY: return handleVinEntry(session, userInput);
            case DATA_REVIEW: return handleDataReview(session, userInput);
            case FIELD_COMPLETION: return handleFieldCompletion(session, userInput);
            case FINAL_REVIEW: return handleFinalReview(session, userInput);
            default: return new ChatResponse("Let's start over. How can I help you create a vehicle asset?");
        }
    }
    
    private ChatResponse handleWelcome(ChatSession session) {
        ChatResponse response = new ChatResponse("👋 Hi! I'll help you create a vehicle asset. How would you like to start?");
        response.addOption("Enter VIN manually");
        response.addOption("Upload VIN image");
        response.addOption("Describe vehicle (e.g., 2018 Ford F-150)");
        response.setNextState(ChatState.VIN_INPUT_METHOD);
        session.setState(ChatState.VIN_INPUT_METHOD);
        return response;
    }
    
    private ChatResponse handleVinInputMethod(ChatSession session, String choice) {
        if (choice.toLowerCase().contains("manual") || choice.equals("1")) {
            ChatResponse response = new ChatResponse("📝 Please enter your VIN number (17 characters):");
            response.setNextState(ChatState.VIN_ENTRY);
            session.setState(ChatState.VIN_ENTRY);
            return response;
        } else if (choice.toLowerCase().contains("upload") || choice.toLowerCase().contains("image")) {
            ChatResponse response = new ChatResponse("📷 Please upload an image containing the VIN number:");
            response.addData("showFileUpload", true);
            response.setNextState(ChatState.VIN_ENTRY);
            session.setState(ChatState.VIN_ENTRY);
            return response;
        } else {
            ChatResponse response = new ChatResponse("📝 Please describe your vehicle (e.g., '2018 Ford F-150, black, 120k miles'):");
            response.setNextState(ChatState.VIN_ENTRY);
            session.setState(ChatState.VIN_ENTRY);
            return response;
        }
    }
    
    private ChatResponse handleVinEntry(ChatSession session, String input) {
        VehicleData data = session.getVehicleData();
        
        // Check if input looks like a VIN (17 alphanumeric characters)
        if (input.matches("[A-Z0-9]{17}")) {
            data.setVin(input);
            
            // Try to decode VIN using existing services
            try {
                VehicleData heavyData = heavyMachineryDB.lookupVin(input);
                if (heavyData != null) {
                    data.setMake(heavyData.getMake());
                    data.setModel(heavyData.getModel());
                    data.setYear(heavyData.getYear());
                    data.setVehicleType(heavyData.getVehicleType());
                } else {
                    data = decodeVinFromNHTSA(input, data);
                }
            } catch (Exception e) {
                System.out.println("VIN decoding error: " + e.getMessage());
            }
            
            String foundInfo = "Make: " + (data.getMake() != null ? data.getMake() : "(missing)") +
                              "\nModel: " + (data.getModel() != null ? data.getModel() : "(missing)") +
                              "\nYear: " + (data.getYear() != null ? data.getYear() : "(missing)") +
                              "\nVehicle Type: " + (data.getVehicleType() != null ? data.getVehicleType() : "(missing)");
            
            ChatResponse response = new ChatResponse("✅ Great! I found this information for VIN: " + input + "\n\n" + foundInfo + "\n\nLet me help you complete the missing fields.");
            
            response.setNextState(ChatState.DATA_REVIEW);
            session.setState(ChatState.DATA_REVIEW);
            return handleDataReview(session, "");
        } else {
            // Try to parse natural language description
            parseNaturalLanguage(input, data);
            
            String foundInfo = "Year: " + (data.getYear() != null ? data.getYear() : "(missing)") +
                              "\nMake: " + (data.getMake() != null ? data.getMake() : "(missing)") +
                              "\nModel: " + (data.getModel() != null ? data.getModel() : "(missing)") +
                              "\nVehicle Type: " + (data.getVehicleType() != null ? data.getVehicleType() : "(missing)");
            
            ChatResponse response = new ChatResponse("🔍 I extracted this information from your description:\n\n" + foundInfo + "\n\nLet me help you complete the missing fields.");
            
            response.setNextState(ChatState.DATA_REVIEW);
            session.setState(ChatState.DATA_REVIEW);
            return handleDataReview(session, "");
        }
    }
    
    private ChatResponse handleDataReview(ChatSession session, String choice) {
        VehicleData data = session.getVehicleData();
        
        // Check if all required fields are complete
        if (isDataComplete(data)) {
            ChatResponse response = new ChatResponse("✨ Perfect! All fields are complete. Here's your vehicle asset:\n\n" +
                "Year: " + data.getYear() + "\n" +
                "Make: " + data.getMake() + "\n" +
                "Model: " + data.getModel() + "\n" +
                "Vehicle Type: " + data.getVehicleType());
            
            response.addData("finalData", data);
            response.addData("completeChat", true);
            response.addOption("Complete & Fill Form");
            response.setNextState(ChatState.FINAL_REVIEW);
            session.setState(ChatState.FINAL_REVIEW);
            return response;
        }
        
        // Show suggestions for missing fields
        Map<String, List<String>> suggestions = suggestionService.generateSuggestions(data);
        
        StringBuilder missingFields = new StringBuilder();
        if (data.getMake() == null || data.getMake().isEmpty()) missingFields.append("Make, ");
        if (data.getModel() == null || data.getModel().isEmpty()) missingFields.append("Model, ");
        if (data.getYear() == null || data.getYear().isEmpty()) missingFields.append("Year, ");
        if (data.getVehicleType() == null || data.getVehicleType().isEmpty()) missingFields.append("Vehicle Type, ");
        
        String missing = missingFields.toString().replaceAll(", $", "");
        if (missing.isEmpty()) {
            // All fields present, go to completion
            return handleDataReview(session, choice);
        }
        
        ChatResponse response = new ChatResponse("🔍 Missing: " + missing + ". Please select from suggestions below:");
        
        if (suggestions.containsKey("model")) {
            response.addData("modelSuggestions", suggestions.get("model"));
        }
        if (suggestions.containsKey("year")) {
            response.addData("yearSuggestions", suggestions.get("year"));
        }
        if (suggestions.containsKey("vehicleType")) {
            response.addData("typeSuggestions", suggestions.get("vehicleType"));
        }
        
        response.setNextState(ChatState.FIELD_COMPLETION);
        session.setState(ChatState.FIELD_COMPLETION);
        return response;
    }
    
    private ChatResponse handleFieldCompletion(ChatSession session, String input) {
        VehicleData data = session.getVehicleData();
        
        // Parse user selection from input
        if (input.toLowerCase().contains("model:") || input.toLowerCase().contains("select model:")) {
            String selectedModel = input.replaceAll("(?i).*model:\s*", "").trim();
            data.setModel(selectedModel);
        } else if (input.toLowerCase().contains("year:") || input.toLowerCase().contains("select year:")) {
            String selectedYear = input.replaceAll("(?i).*year:\s*", "").trim();
            data.setYear(selectedYear);
        } else if (input.toLowerCase().contains("type:") || input.toLowerCase().contains("select type:")) {
            String selectedType = input.replaceAll("(?i).*type:\s*", "").trim();
            data.setVehicleType(selectedType);
        } else {
            // Try to detect what field they're filling based on content
            if (input.matches("\\d{4}")) {
                data.setYear(input);
            } else if (input.toLowerCase().contains("sedan") || input.toLowerCase().contains("suv") || 
                      input.toLowerCase().contains("truck") || input.toLowerCase().contains("equipment")) {
                data.setVehicleType(input);
            } else {
                data.setModel(input);
            }
        }
        
        // Check if complete now, otherwise continue with suggestions
        return handleDataReview(session, "");
    }
    
    private ChatResponse handleFinalReview(ChatSession session, String choice) {
        VehicleData data = session.getVehicleData();
        ChatResponse response = new ChatResponse("🎉 Great! I've filled the form with your vehicle data. You can now review and submit it.");
        response.addData("completeChat", true);
        response.addData("finalData", data);
        return response;
    }
    
    private boolean isDataComplete(VehicleData data) {
        return data.getMake() != null && !data.getMake().isEmpty() &&
               data.getModel() != null && !data.getModel().isEmpty() &&
               data.getYear() != null && !data.getYear().isEmpty() &&
               data.getVehicleType() != null && !data.getVehicleType().isEmpty();
    }
    
    public ChatSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
    
    private VehicleData decodeVinFromNHTSA(String vin, VehicleData data) {
        try {
            String url = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevin/" + vin + "?format=json";
            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode root = mapper.readTree(response);
            com.fasterxml.jackson.databind.JsonNode results = root.get("Results");
            
            if (results != null && results.isArray()) {
                for (com.fasterxml.jackson.databind.JsonNode result : results) {
                    String variable = result.get("Variable").asText();
                    String value = result.get("Value").asText();
                    
                    if ("Make".equals(variable) && value != null && !value.isEmpty()) {
                        data.setMake(value);
                    } else if ("Model".equals(variable) && value != null && !value.isEmpty()) {
                        data.setModel(value);
                    } else if ("Model Year".equals(variable) && value != null && !value.isEmpty()) {
                        data.setYear(value);
                    } else if ("Vehicle Type".equals(variable) && value != null && !value.isEmpty()) {
                        data.setVehicleType(value);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("NHTSA API error: " + e.getMessage());
        }
        return data;
    }
    
    private void parseNaturalLanguage(String input, VehicleData data) {
        String inputLower = input.toLowerCase();
        
        // Extract year (4-digit number between 1990-2024)
        java.util.regex.Pattern yearPattern = java.util.regex.Pattern.compile("\\b(19[9][0-9]|20[0-2][0-9])\\b");
        java.util.regex.Matcher yearMatcher = yearPattern.matcher(input);
        if (yearMatcher.find()) {
            data.setYear(yearMatcher.group(1));
        }
        
        // Extract make (common car manufacturers)
        String[] makes = {"suzuki", "ford", "chevrolet", "chevy", "toyota", "honda", "nissan", "bmw", "mercedes", "audi", "volkswagen", "vw", "hyundai", "kia", "mazda", "subaru", "jeep", "dodge", "ram", "gmc", "cadillac", "lexus", "acura", "infiniti", "tesla", "volvo", "jaguar", "land rover", "porsche", "ferrari", "lamborghini", "maserati", "bentley", "rolls royce", "mitsubishi", "isuzu", "daihatsu"};
        
        for (String make : makes) {
            if (inputLower.contains(make)) {
                // Capitalize first letter
                String capitalizedMake = make.substring(0, 1).toUpperCase() + make.substring(1);
                if (make.equals("chevy")) capitalizedMake = "Chevrolet";
                if (make.equals("vw")) capitalizedMake = "Volkswagen";
                data.setMake(capitalizedMake);
                break;
            }
        }
        
        // Extract model (look for words after make or common model patterns)
        if (data.getMake() != null) {
            String makePattern = data.getMake().toLowerCase();
            int makeIndex = inputLower.indexOf(makePattern);
            if (makeIndex != -1) {
                String afterMake = input.substring(makeIndex + makePattern.length()).trim();
                String[] words = afterMake.split("\\s+");
                if (words.length > 0 && !words[0].isEmpty()) {
                    StringBuilder model = new StringBuilder();
                    for (int i = 0; i < Math.min(2, words.length); i++) {
                        String word = words[i].replaceAll("[^a-zA-Z0-9-]", "");
                        if (word.length() > 0 && !isStopWord(word.toLowerCase()) && !word.matches("\\d{4}")) {
                            if (model.length() > 0) model.append(" ");
                            model.append(word);
                        } else if (word.matches("\\d{4}")) {
                            break; // Stop at year
                        }
                    }
                    if (model.length() > 0) {
                        data.setModel(model.toString());
                    }
                }
            }
        } else {
            // If no make found, try to extract model from common patterns
            String[] commonModels = {"alto", "swift", "baleno", "vitara", "jimny", "camry", "corolla", "civic", "accord", "f-150", "mustang", "focus"};
            for (String modelName : commonModels) {
                if (inputLower.contains(modelName)) {
                    data.setModel(modelName.substring(0, 1).toUpperCase() + modelName.substring(1));
                    // Try to infer make from model
                    if (modelName.equals("alto") || modelName.equals("swift") || modelName.equals("baleno") || modelName.equals("vitara") || modelName.equals("jimny")) {
                        data.setMake("Suzuki");
                    }
                    break;
                }
            }
        }
        
        // Extract vehicle type based on keywords
        if (inputLower.contains("truck") || inputLower.contains("pickup")) {
            data.setVehicleType("Truck");
        } else if (inputLower.contains("suv") || inputLower.contains("crossover")) {
            data.setVehicleType("SUV");
        } else if (inputLower.contains("sedan") || inputLower.contains("car")) {
            data.setVehicleType("Sedan");
        } else if (inputLower.contains("coupe")) {
            data.setVehicleType("Coupe");
        } else if (inputLower.contains("hatchback")) {
            data.setVehicleType("Hatchback");
        } else if (inputLower.contains("convertible")) {
            data.setVehicleType("Convertible");
        }
    }
    
    private boolean isStopWord(String word) {
        String[] stopWords = {"black", "white", "red", "blue", "green", "silver", "gray", "grey", "yellow", "orange", "purple", "brown", "miles", "km", "kilometers", "salvage", "clean", "title", "damage", "damaged", "good", "excellent", "fair", "poor", "condition", "with", "and", "the", "a", "an", "in", "on", "at", "for", "to", "from", "by", "of", "is", "are", "was", "were", "has", "have", "had"};
        for (String stopWord : stopWords) {
            if (word.equals(stopWord)) return true;
        }
        return false;
    }
}