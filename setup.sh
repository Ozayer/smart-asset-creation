#!/bin/bash

echo "🚀 Setting up Heavy Machinery VIN Lookup System..."

# Check Java version
echo "📋 Checking Java version..."
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install Java 17 or higher."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17 or higher required. Current version: $JAVA_VERSION"
    exit 1
fi
echo "✅ Java version OK"

# Check Maven
echo "📋 Checking Maven..."
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven not found. Please install Maven 3.6+"
    exit 1
fi
echo "✅ Maven found"

# Clean and install dependencies
echo "📦 Installing dependencies..."
mvn clean install -q

if [ $? -eq 0 ]; then
    echo "✅ Dependencies installed successfully"
else
    echo "❌ Failed to install dependencies"
    exit 1
fi

# Start the application
echo "🚀 Starting application..."
echo "📍 Application will be available at: http://localhost:8080"
echo "🔧 VIN API endpoint: http://localhost:8080/api/vin/{vinNumber}"
echo "💬 Chat API endpoint: http://localhost:8080/api/chat"
echo ""
echo "Press Ctrl+C to stop the application"
echo ""

mvn spring-boot:run