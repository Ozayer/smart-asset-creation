# 🚜 Heavy Machinery VIN Lookup & Asset Management System

**An intelligent Spring Boot application that revolutionizes heavy machinery asset management with AI-powered VIN decoding, voice input, OCR image processing, and conversational asset creation.**

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 🌟 Key Features

### 🔍 **Multi-Source VIN Decoding**
- **Heavy Machinery Database**: 500+ pre-loaded models from major manufacturers
- **NHTSA Integration**: Real-time API calls to US Department of Transportation database
- **Intelligent Fallback**: Automatic switching between data sources for maximum coverage
- **17-Character VIN Validation**: Robust pattern matching and validation

### 🎤 **Voice-to-Text Integration**
- **Web Speech API**: Hands-free VIN entry and chat interaction
- **Real-time Recognition**: Instant speech-to-text conversion
- **Auto-focus**: Cursor automatically positioned for immediate submission
- **Cross-browser Support**: Chrome, Edge, Safari compatibility

### 📷 **OCR Image Processing**
- **VIN Image Upload**: Extract VIN numbers from photos
- **OCR.space Integration**: Professional-grade text recognition
- **Multiple Formats**: Support for JPG, PNG, and other image formats
- **Automatic Processing**: Instant VIN extraction and decoding

### 🤖 **AI-Powered Conversational Assistant**
- **Natural Language Processing**: Understand vehicle descriptions like "2018 Ford F-150"
- **Intelligent Suggestions**: Context-aware model, year, and type recommendations
- **Multi-step Conversations**: Guided asset creation process
- **Smart Form Filling**: Automatic population of vehicle data forms

### 🏗️ **Comprehensive Manufacturer Coverage**
- **Construction Equipment**: Caterpillar, Komatsu, Case, Volvo, Hitachi
- **Agricultural Machinery**: John Deere, Case IH, New Holland
- **Compact Equipment**: Bobcat, Kubota, JCB
- **Heavy Trucks**: Nissan, Isuzu, Mitsubishi Fuso
- **500+ Models**: Excavators, bulldozers, wheel loaders, tractors, and more

### 🎯 **Advanced Asset Management**
- **Complete Vehicle Profiles**: Make, model, year, type, VIN, usage tracking
- **Smart Suggestions**: AI-generated recommendations based on partial data
- **Form Auto-completion**: Intelligent field population
- **Data Validation**: Real-time verification and error handling

## 🚀 Quick Start

### One-Command Setup
```bash
chmod +x setup.sh && ./setup.sh
```

### Manual Installation

**Prerequisites:**
- Java 17+ ☕
- Maven 3.6+ 📦

**Steps:**
```bash
# Clone the repository
git clone https://github.com/your-repo/heavy-machinery-vin-lookup.git
cd heavy-machinery-vin-lookup

# Install dependencies
mvn clean install

# Start the application
mvn spring-boot:run
```

**Access Points:**
- 🌐 **Web Interface**: http://localhost:8080
- 🔗 **VIN API**: http://localhost:8080/api/vin/{vinNumber}
- 💬 **Chat API**: http://localhost:8080/api/chat
- 📊 **Chat Interface**: http://localhost:8080/chat.html

## 🛠️ API Documentation

### VIN Decoding Endpoint
```http
GET /api/vin/{vinNumber}
```
**Example:**
```bash
curl http://localhost:8080/api/vin/1HGBH41JXMN109186
```

### Chat Assistant API
```http
POST /api/chat/start
POST /api/chat/message
POST /api/chat/upload-image
```

**Start Chat Session:**
```bash
curl -X POST http://localhost:8080/api/chat/start
```

**Send Message:**
```bash
curl -X POST http://localhost:8080/api/chat/message \
  -H "Content-Type: application/json" \
  -d '{"sessionId":"uuid", "message":"2020 Caterpillar 320", "state":"VIN_ENTRY"}'
```

## 🎯 Usage Examples

### 1. **Voice Input VIN Entry**
1. Click the 🎤 microphone button next to VIN field
2. Say: "1HGBH41JXMN109186"
3. Press Enter to auto-submit

### 2. **Image VIN Extraction**
1. Upload photo containing VIN plate
2. System automatically extracts and decodes VIN
3. Form populates with vehicle data

### 3. **Conversational Asset Creation**
1. Click chat assistant (💬)
2. Say or type: "I have a 2018 Caterpillar excavator"
3. Follow guided conversation to complete asset profile

### 4. **Natural Language Processing**
- "2020 Ford F-150 pickup truck" → Extracts make, model, year, type
- "Caterpillar 320 excavator" → Identifies manufacturer and equipment type
- "John Deere tractor from 2019" → Parses agricultural equipment data

## 🏭 Supported Equipment Database

| Manufacturer | Models | Equipment Types |
|--------------|--------|----------------|
| **Caterpillar** | 200+ | Excavators, Bulldozers, Wheel Loaders, Graders |
| **John Deere** | 150+ | Tractors, Combines, Planters, Sprayers |
| **Komatsu** | 100+ | Excavators, Dump Trucks, Bulldozers |
| **Case** | 75+ | Backhoes, Skid Steers, Tractors |
| **Volvo** | 50+ | Excavators, Wheel Loaders, Articulated Haulers |
| **Nissan** | 75+ | Commercial Trucks, Forklifts |
| **Bobcat** | 60+ | Skid Steers, Compact Excavators, Loaders |
| **Hitachi** | 40+ | Excavators, Dump Trucks, Cranes |

## 🔧 Technical Architecture

### Backend Stack
- **Spring Boot 3.0+**: Modern Java framework
- **Maven**: Dependency management
- **Jackson**: JSON processing
- **RestTemplate**: HTTP client for external APIs

### Frontend Technologies
- **Thymeleaf**: Server-side templating
- **Web Speech API**: Voice recognition
- **Vanilla JavaScript**: Client-side interactions
- **Responsive CSS**: Mobile-friendly design

### External Integrations
- **NHTSA VPIC API**: US vehicle database
- **OCR.space API**: Image text extraction
- **Web Speech API**: Browser-based voice recognition

### Data Sources
1. **Internal Heavy Machinery DB**: Custom database with 500+ models
2. **NHTSA API**: US Department of Transportation vehicle database
3. **AI Suggestion Engine**: Machine learning-based recommendations

## 🚀 Advanced Features

### Intelligent Data Processing
- **Multi-source Fallback**: Tries heavy machinery DB first, then NHTSA
- **Smart Parsing**: Extracts vehicle info from natural language
- **Context-aware Suggestions**: Recommendations based on partial data
- **Real-time Validation**: Instant feedback on data quality

### User Experience Enhancements
- **Voice Commands**: Hands-free operation
- **Image Processing**: Photo-based VIN entry
- **Conversational UI**: Natural language interactions
- **Auto-completion**: Smart form filling
- **Mobile Responsive**: Works on all devices

### Enterprise Features
- **Session Management**: Persistent chat conversations
- **Error Handling**: Graceful failure recovery
- **Logging**: Comprehensive system monitoring
- **Scalable Architecture**: Ready for production deployment

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support, please open an issue on GitHub or contact our team.

---

**Built with ❤️ for the heavy machinery industry**