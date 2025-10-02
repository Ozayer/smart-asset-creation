# Git Repository Setup Instructions

## Step 1: Create GitHub Repository
1. Go to https://github.com/new
2. Create a new repository named `heavy-machinery-vin-lookup` (or your preferred name)
3. Don't initialize with README (we already have one)

## Step 2: Push to GitHub
Replace `YOUR_USERNAME` and `REPO_NAME` with your actual GitHub username and repository name:

```bash
# Add remote origin
git remote add origin https://github.com/YOUR_USERNAME/REPO_NAME.git

# Push to main branch
git branch -M main
git push -u origin main
```

## Alternative: Using SSH (if you have SSH keys set up)
```bash
git remote add origin git@github.com:YOUR_USERNAME/REPO_NAME.git
git branch -M main
git push -u origin main
```

## Quick Commands for Future Updates
```bash
# Add changes
git add .

# Commit changes
git commit -m "Your commit message"

# Push changes
git push
```

## Repository is Ready!
Your code is now committed locally with:
- ✅ Complete Spring Boot application
- ✅ Expanded database with 270+ Caterpillar models
- ✅ 75+ Nissan heavy machinery models  
- ✅ Setup script for easy installation
- ✅ Comprehensive README
- ✅ Proper .gitignore file