#!/bin/bash

# Replace YOUR_GITHUB_URL with your actual repository URL
GITHUB_URL="YOUR_GITHUB_URL"

echo "🔗 Connecting to GitHub repository..."

# Add remote origin
git remote add origin $GITHUB_URL

# Push to main branch
git branch -M main
git push -u origin main

echo "✅ Successfully connected and pushed to GitHub!"
echo "🌐 Your repository is now available at: $GITHUB_URL"