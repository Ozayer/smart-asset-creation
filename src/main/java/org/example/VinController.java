package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Base64;
import java.nio.file.Files;
import java.util.Map;
import java.util.List;

@Controller
public class VinController {
    
    private final VehicleSuggestionService suggestionService;
    private final HeavyMachineryDB heavyMachineryDB;
    
    public VinController(VehicleSuggestionService suggestionService, HeavyMachineryDB heavyMachineryDB) {
        this.suggestionService = suggestionService;
        this.heavyMachineryDB = heavyMachineryDB;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("vehicleData", new VehicleData());
        return "index";
    }
    


    @PostMapping("/decode")
    public String decodeVin(@RequestParam(value = "vin", required = false) String vin,
                           @RequestParam(value = "make", required = false) String make,
                           @RequestParam(value = "model", required = false) String model,
                           @RequestParam(value = "year", required = false) String year,
                           @RequestParam(value = "vehicleType", required = false) String vehicleType,
                           Model modelView) {
        try {
            VehicleData vehicleData = new VehicleData();
            
            // Check if this is VIN decoding or manual data entry
            if (vin != null && !vin.isEmpty() && vin.matches("[A-Z0-9]{17}")) {
                // VIN decoding path
                vehicleData.setVin(vin);
                
                boolean foundData = tryHeavyMachineryDB(vin, vehicleData);
                if (!foundData) {
                    foundData = tryNHTSA(vin, vehicleData);
                }
                
                System.out.println("Data source: " + (foundData ? "API" : "None - will use AI suggestions"));
            } else {
                // Manual data entry path
                if (make != null && !make.isEmpty()) vehicleData.setMake(make);
                if (model != null && !model.isEmpty()) vehicleData.setModel(model);
                if (year != null && !year.isEmpty()) vehicleData.setYear(year);
                if (vehicleType != null && !vehicleType.isEmpty()) vehicleData.setVehicleType(vehicleType);
                if (vin != null && !vin.isEmpty()) vehicleData.setVin(vin);
                
                System.out.println("Data source: Manual entry from chat");
            }
            
            // Generate AI suggestions for missing fields
            Map<String, List<String>> suggestions = suggestionService.generateSuggestions(vehicleData);
            modelView.addAttribute("suggestions", suggestions);
            modelView.addAttribute("vehicleData", vehicleData);
            
            System.out.println("=== FINAL RESULT ===");
            System.out.println("Make: " + vehicleData.getMake());
            System.out.println("Model: " + vehicleData.getModel());
            System.out.println("Year: " + vehicleData.getYear());
            System.out.println("Suggestions: " + suggestions);
            System.out.println("===================");
        } catch (Exception e) {
            modelView.addAttribute("error", "Error processing vehicle data: " + e.getMessage());
            modelView.addAttribute("vehicleData", new VehicleData());
            e.printStackTrace();
        }
        
        return "index";
    }

    @PostMapping("/save")
    public String saveVehicle(@ModelAttribute VehicleData vehicleData, Model model) {
        model.addAttribute("vehicleData", vehicleData);
        model.addAttribute("saved", true);
        return "index";
    }
    


    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file, Model model) {
        try {
            // Save uploaded file temporarily
            File tempFile = File.createTempFile("vin_image", ".jpg");
            file.transferTo(tempFile);
            
            // Simple OCR using OCR.space API (free tier)
            String extractedText = performOCR(tempFile);
            System.out.println("OCR extracted text: " + extractedText);
            
            // Extract VIN using regex (17 alphanumeric characters)
            Pattern vinPattern = Pattern.compile("[A-Z0-9]{17}");
            Matcher matcher = vinPattern.matcher(extractedText.toUpperCase());
            
            if (matcher.find()) {
                String extractedVin = matcher.group();
                System.out.println("Extracted VIN: " + extractedVin);
                
                // Auto-decode the extracted VIN
                return decodeVin(extractedVin, null, null, null, null, model);
            } else {
                model.addAttribute("error", "No valid VIN found in image. Extracted text: " + extractedText);
                model.addAttribute("vehicleData", new VehicleData());
            }
            
            tempFile.delete();
        } catch (Exception e) {
            model.addAttribute("error", "Error processing image: " + e.getMessage());
            model.addAttribute("vehicleData", new VehicleData());
        }
        
        return "index";
    }

    private String performOCR(File imageFile) {
        try {
            // Convert image to base64
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
            // Use OCR.space free API
            RestTemplate restTemplate = new RestTemplate();
            String ocrUrl = "https://api.ocr.space/parse/image";
            
            // Create form data
            org.springframework.util.LinkedMultiValueMap<String, Object> map = new org.springframework.util.LinkedMultiValueMap<>();
            map.add("base64Image", "data:image/jpeg;base64," + base64Image);
            map.add("apikey", "helloworld"); // Free tier key
            map.add("language", "eng");
            
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
            
            org.springframework.http.HttpEntity<org.springframework.util.MultiValueMap<String, Object>> requestEntity = 
                new org.springframework.http.HttpEntity<>(map, headers);
            
            String response = restTemplate.postForObject(ocrUrl, requestEntity, String.class);
            
            // Parse response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode parsedResults = root.get("ParsedResults");
            
            if (parsedResults != null && parsedResults.size() > 0) {
                return parsedResults.get(0).get("ParsedText").asText();
            }
            
            return "No text found";
        } catch (Exception e) {
            System.out.println("OCR Error: " + e.getMessage());
            return "OCR processing failed: " + e.getMessage();
        }
    }
    
    private boolean tryHeavyMachineryDB(String vin, VehicleData vehicleData) {
        try {
            VehicleData dbData = heavyMachineryDB.lookupVin(vin);
            if (dbData != null) {
                System.out.println("Heavy Machinery DB: Found data for VIN");
                vehicleData.setMake(dbData.getMake());
                vehicleData.setModel(dbData.getModel());
                vehicleData.setYear(dbData.getYear());
                vehicleData.setBodyClass(dbData.getBodyClass());
                vehicleData.setEngineSize(dbData.getEngineSize());
                vehicleData.setVehicleType(dbData.getVehicleType());
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Heavy Machinery DB error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean tryNHTSA(String vin, VehicleData vehicleData) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://vpic.nhtsa.dot.gov/api/vehicles/DecodeVin/" + vin + "?format=json";
            String response = restTemplate.getForObject(url, String.class);
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode results = root.get("Results");
            
            boolean foundAnyData = false;
            System.out.println("NHTSA: Processing " + results.size() + " results");
            
            for (JsonNode result : results) {
                String variable = result.get("Variable").asText();
                JsonNode valueNode = result.get("Value");
                String value = valueNode != null ? valueNode.asText() : "";
                
                if (value == null || value.isEmpty() || value.equals("null") || value.equals("")) continue;
                
                foundAnyData = true;
                switch (variable) {
                    case "Make" -> vehicleData.setMake(value);
                    case "Model" -> vehicleData.setModel(value);
                    case "Model Year" -> vehicleData.setYear(value);
                    case "Body Class" -> vehicleData.setBodyClass(value);
                    case "Engine Number of Cylinders" -> vehicleData.setEngineSize(value);
                    case "Vehicle Type" -> vehicleData.setVehicleType(value);
                    case "Manufacturer Name" -> { 
                        if (vehicleData.getMake() == null || vehicleData.getMake().isEmpty()) { 
                            vehicleData.setMake(value); 
                        } 
                    }
                }
            }
            return foundAnyData;
        } catch (Exception e) {
            System.out.println("NHTSA API error: " + e.getMessage());
            return false;
        }
    }
    

}