package org.example;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class VehicleSuggestionService {

    public Map<String, List<String>> generateSuggestions(VehicleData vehicleData) {
        Map<String, List<String>> suggestions = new HashMap<>();
        
        // Suggest models based on make and year
        if (vehicleData.getMake() != null && !vehicleData.getMake().isEmpty() && 
            (vehicleData.getModel() == null || vehicleData.getModel().isEmpty())) {
            suggestions.put("model", suggestModels(vehicleData.getMake(), vehicleData.getYear()));
        }
        
        // Suggest years based on make and model
        if (vehicleData.getMake() != null && vehicleData.getModel() != null &&
            (vehicleData.getYear() == null || vehicleData.getYear().isEmpty())) {
            suggestions.put("year", suggestYears(vehicleData.getMake(), vehicleData.getModel()));
        }
        
        // Suggest vehicle type based on make and model
        if (vehicleData.getMake() != null &&
            (vehicleData.getVehicleType() == null || vehicleData.getVehicleType().isEmpty())) {
            suggestions.put("vehicleType", suggestVehicleType(vehicleData.getMake(), vehicleData.getModel()));
        }
        
        return suggestions;
    }
    
    private List<String> suggestModels(String make, String year) {
        try {
            String prompt = String.format("""
You are a vehicle data assistant.
Task: Suggest up to 8 specific vehicle model names for the given make and year.
Provide exact model names, not series names. For BMW, use "320i" not "3 Series".
Return ONLY a valid JSON array of strings, no explanations.

Example:
["Camry","Corolla","Highlander","RAV4"]

Now, suggest specific model names for: Make=%s, Year=%s
""", make, year != null ? year : "Unknown");
            
            String aiResponse = callLocalLLM(prompt);
            return parseLLMResponse(aiResponse);
        } catch (Exception e) {
            return getSmartModelSuggestions(make);
        }
    }
    
    private List<String> suggestYears(String make, String model) {
        try {
            String prompt = String.format("""
Respond with ONLY a JSON array of 4 years as strings. No text, no explanations.

Example: ["2018","2019","2020","2021"]

Years for %s %s:
""", make, model);
            
            String aiResponse = callLocalLLM(prompt);
            return parseLLMResponse(aiResponse);
        } catch (Exception e) {
            List<String> years = new ArrayList<>();
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = currentYear; i >= currentYear - 5; i--) {
                years.add(String.valueOf(i));
            }
            return years;
        }
    }
    
    private List<String> suggestVehicleType(String make, String model) {
        return suggestBodyClass(make, model);
    }
    
    private List<String> suggestBodyClass(String make, String model) {
        try {
            String prompt;
            if (isHeavyMachinery(make)) {
                prompt = String.format("""
You are a vehicle data assistant.
Task: Suggest up to 4 equipment types for the given heavy machinery.
Return ONLY a valid JSON array of strings, no explanations.

Example:
["Excavator","Bulldozer","Loader"]

Now, suggest equipment types for: %s %s
""", make, model);
            } else {
                prompt = String.format("""
You are a vehicle data assistant.
Task: Suggest up to 4 body types for the given vehicle.
Return ONLY a valid JSON array of strings, no explanations.

Example:
["Sedan","SUV","Hatchback"]

Now, suggest body types for: %s %s
""", make, model);
            }
            
            String aiResponse = callLocalLLM(prompt);
            return parseLLMResponse(aiResponse);
        } catch (Exception e) {
            if (isHeavyMachinery(make)) {
                return Arrays.asList("Excavator", "Bulldozer", "Loader", "Crane", "Grader");
            }
            return Arrays.asList("Sedan", "SUV", "Hatchback", "Coupe", "Truck");
        }
    }
    
    private boolean isHeavyMachinery(String make) {
        if (make == null) return false;
        String makeLower = make.toLowerCase();
        return makeLower.contains("cat") || makeLower.contains("caterpillar") ||
               makeLower.contains("john") || makeLower.contains("deere") ||
               makeLower.contains("komatsu") || makeLower.contains("case") ||
               makeLower.contains("volvo") || makeLower.contains("hitachi") ||
               makeLower.contains("liebherr") || makeLower.contains("jcb");
    }
    
    private List<String> getSmartModelSuggestions(String make) {
        if (make == null) return Arrays.asList("Unknown");
        
        String makeLower = make.toLowerCase();
        
        if (makeLower.contains("toyota")) {
            return Arrays.asList("Camry", "Corolla", "RAV4", "Prius", "Highlander", "Yaris");
        } else if (makeLower.contains("honda")) {
            return Arrays.asList("Civic", "Accord", "CR-V", "Pilot", "Fit");
        } else if (makeLower.contains("ford")) {
            return Arrays.asList("F-150", "Escape", "Explorer", "Focus", "Mustang");
        } else if (makeLower.contains("chevrolet")) {
            return Arrays.asList("Silverado", "Equinox", "Malibu", "Cruze", "Tahoe");
        } else if (makeLower.contains("bmw")) {
            return Arrays.asList("320i", "330i", "X3", "X5", "530i");
        } else if (makeLower.contains("tesla")) {
            return Arrays.asList("Model S", "Model 3", "Model X", "Model Y", "Cybertruck");
        }
        
        return Arrays.asList("Model A", "Model B", "Model C", "Model D");
    }
    
    private String callLocalLLM(String prompt) {
        return callOllama(prompt);
    }
    
    private String callOllama(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> request = Map.of(
                "model", "llama3.1:8b",
                "prompt", prompt,
                "temperature", 0.2,
                "max_tokens", 150,
                "stream", false
            );
            
            String response = restTemplate.postForObject(
                "http://localhost:11434/api/generate", 
                request, 
                String.class
            );
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(response);
            return jsonResponse.get("response").asText().trim();
        } catch (Exception e) {
            throw new RuntimeException("Ollama call failed: " + e.getMessage());
        }
    }

    private List<String> parseLLMResponse(String response) {
        try {
            String cleanedResponse = response.trim();
            if (cleanedResponse.startsWith("{") && cleanedResponse.endsWith("}") && !cleanedResponse.contains(":")) {
                cleanedResponse = cleanedResponse.replace("{", "[").replace("}", "]");
            }
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(cleanedResponse);
            
            List<String> values;
            if (root.isArray()) {
                values = mapper.convertValue(root, new TypeReference<List<String>>() {});
            } else {
                values = Arrays.asList("Unknown");
            }
            
            return values.isEmpty() ? Arrays.asList("Unknown") : values;
        } catch (Exception e) {
            return Arrays.asList("Unknown");
        }
    }
}