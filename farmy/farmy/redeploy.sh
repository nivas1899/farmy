#!/bin/bash

# Quick Redeploy Script for FARMY
# Use this to quickly rebuild and redeploy after code changes

set -e

echo "🔄 Redeploying FARMY Application..."
echo ""

cd "$(dirname "$0")"

# Stop Tomcat
echo "⏸️  Stopping Tomcat..."
sudo systemctl stop tomcat10

# Clean old files
echo "🧹 Cleaning old deployment..."
sudo rm -rf /var/lib/tomcat10/webapps/farmy*
rm -rf backend/classes/*
rm -rf build/*

# Compile Java code
echo "🔨 Compiling Java code..."
cd backend
mkdir -p classes
find src -name "*.java" > sources.txt
javac -cp "lib/*" -d classes @sources.txt
rm sources.txt
echo "✅ Compilation complete"
cd ..

# Create WAR file
echo "📦 Creating WAR file..."
mkdir -p build/farmy/WEB-INF/classes
mkdir -p build/farmy/WEB-INF/lib

cp -r backend/classes/* build/farmy/WEB-INF/classes/
cp backend/lib/*.jar build/farmy/WEB-INF/lib/
cp backend/web.xml build/farmy/WEB-INF/
cp -r frontend/* build/farmy/

cd build
jar -cvf farmy.war -C farmy . > /dev/null
echo "✅ WAR file created"
cd ..

# Deploy to Tomcat
echo "🚀 Deploying to Tomcat..."
sudo cp build/farmy.war /var/lib/tomcat10/webapps/
sudo chown tomcat:tomcat /var/lib/tomcat10/webapps/farmy.war

# Start Tomcat
echo "▶️  Starting Tomcat..."
sudo systemctl start tomcat10

echo ""
echo "✅ Deployment Complete!"
echo ""
echo "🌐 Application URL: http://localhost:8080/farmy/"
echo "⏱️  Wait 10-15 seconds for Tomcat to fully deploy"
echo ""
echo "📋 Check logs: sudo tail -f /var/log/tomcat10/catalina.out"
echo ""
