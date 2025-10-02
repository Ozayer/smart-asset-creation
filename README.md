# Heavy Machinery VIN Lookup System

A Spring Boot application that provides VIN lookup and AI-powered suggestions for heavy machinery equipment.

## Features

- VIN lookup for heavy machinery (Caterpillar, John Deere, Komatsu, Case, Volvo, Nissan, etc.)
- AI-powered chat assistant for equipment information
- Web interface for VIN queries and chat interactions
- Extensive database of equipment models and specifications

## Quick Setup

Run the setup script:
```bash
chmod +x setup.sh
./setup.sh
```

## Manual Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation
1. Clone the repository
2. Install dependencies:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Access
- Web Interface: http://localhost:8080
- VIN API: http://localhost:8080/api/vin/{vinNumber}
- Chat API: http://localhost:8080/api/chat

## API Endpoints

### VIN Lookup
```
GET /api/vin/{vinNumber}
```

### Chat
```
POST /api/chat
Content-Type: application/json
{
  "message": "your message here"
}
```

## Database

The application includes pre-loaded data for:
- **Caterpillar**: 200+ models including excavators, bulldozers, wheel loaders
- **Nissan**: 75+ heavy machinery models
- **John Deere**: Agricultural and construction equipment
- **Komatsu**: Construction machinery
- **Case**: Construction equipment
- **Volvo**: Construction machinery
- **Bobcat**: Compact equipment
- **Hitachi**: Construction machinery