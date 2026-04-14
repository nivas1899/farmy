#!/bin/bash

# FARMY Deployment Script for Debian/Ubuntu Linux
# This script installs all dependencies and deploys the FARMY application

set -e  # Exit on error

echo "=================================================="
echo "  FARMY Farm Management System - Deployment"
echo "  Debian/Ubuntu Linux Automated Setup"
echo "=================================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if running as root
if [ "$EUID" -ne 0 ]; then 
    echo -e "${YELLOW}This script needs sudo privileges. Please enter your password if prompted.${NC}"
fi

# Function to print status
print_status() {
    echo -e "${GREEN}[✓]${NC} $1"
}

print_error() {
    echo -e "${RED}[✗]${NC} $1"
}

print_info() {
    echo -e "${YELLOW}[i]${NC} $1"
}

# Update package list
echo ""
print_info "Updating package list..."
sudo apt-get update -qq

# Install Java JDK
echo ""
print_info "Installing Java JDK 11..."
if ! command -v java &> /dev/null; then
    sudo apt-get install -y openjdk-11-jdk
    print_status "Java JDK installed"
else
    print_status "Java already installed: $(java -version 2>&1 | head -n 1)"
fi

# Install MySQL/MariaDB
echo ""
print_info "Installing MariaDB Server..."
if ! command -v mysql &> /dev/null; then
    sudo apt-get install -y mariadb-server mariadb-client
    sudo systemctl start mariadb
    sudo systemctl enable mariadb
    print_status "MariaDB installed and started"
else
    print_status "MySQL/MariaDB already installed"
fi

# Install Apache Tomcat 10
echo ""
print_info "Installing Apache Tomcat 10..."
if ! command -v catalina &> /dev/null && [ ! -d "/opt/tomcat" ]; then
    sudo apt-get install -y tomcat10 tomcat10-admin
    sudo systemctl start tomcat10
    sudo systemctl enable tomcat10
    print_status "Tomcat 10 installed and started"
else
    print_status "Tomcat already installed"
fi

# Install wget and unzip if not present
sudo apt-get install -y wget unzip curl

# Create lib directory for JAR files
echo ""
print_info "Setting up project directories..."
cd "$(dirname "$0")"
mkdir -p backend/lib
mkdir -p backend/classes

# Download MySQL Connector/J
echo ""
print_info "Downloading MySQL Connector/J..."
if [ ! -f "backend/lib/mysql-connector-java-8.0.33.jar" ]; then
    cd backend/lib
    wget -q https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar -O mysql-connector-java-8.0.33.jar
    print_status "MySQL Connector downloaded"
    cd ../..
else
    print_status "MySQL Connector already exists"
fi

# Download Jakarta Servlet API
echo ""
print_info "Downloading Jakarta Servlet API..."
if [ ! -f "backend/lib/jakarta.servlet-api-5.0.0.jar" ]; then
    cd backend/lib
    wget -q https://repo1.maven.org/maven2/jakarta/servlet/jakarta.servlet-api/5.0.0/jakarta.servlet-api-5.0.0.jar
    print_status "Servlet API downloaded"
    cd ../..
else
    print_status "Servlet API already exists"
fi

# Download JSON library
echo ""
print_info "Downloading JSON library..."
if [ ! -f "backend/lib/json-20230227.jar" ]; then
    cd backend/lib
    wget -q https://repo1.maven.org/maven2/org/json/json/20230227/json-20230227.jar
    print_status "JSON library downloaded"
    cd ../..
else
    print_status "JSON library already exists"
fi

# Set up MySQL database
echo ""
print_info "Setting up MySQL database..."
print_info "Please enter your MySQL root password (or press Enter if none):"
read -s MYSQL_PASSWORD

if [ -z "$MYSQL_PASSWORD" ]; then
    # Try without password first
    if sudo mysql -u root -e "SELECT 1;" &> /dev/null; then
        sudo mysql -u root < database/farmy.sql
        print_status "Database created successfully"
    else
        print_error "Could not connect to MySQL. Please run manually: sudo mysql -u root < database/farmy.sql"
    fi
else
    mysql -u root -p"$MYSQL_PASSWORD" < database/farmy.sql
    print_status "Database created successfully"
fi

# Update DBConnection.java with credentials
echo ""
print_info "Configuring database connection..."
print_info "Enter MySQL username (default: root):"
read DB_USER
DB_USER=${DB_USER:-root}

print_info "Enter MySQL password (leave empty if none):"
read -s DB_PASS

# Update DBConnection.java
sed -i "s/private static final String USER = \"root\";/private static final String USER = \"$DB_USER\";/" backend/src/db/DBConnection.java
if [ -n "$DB_PASS" ]; then
    sed -i "s/private static final String PASSWORD = \"\";/private static final String PASSWORD = \"$DB_PASS\";/" backend/src/db/DBConnection.java
fi
print_status "Database credentials configured"

# Compile Java files
echo ""
print_info "Compiling Java source files..."
cd backend
find src -name "*.java" > sources.txt
javac -cp "lib/*" -d classes @sources.txt
rm sources.txt
print_status "Java files compiled successfully"
cd ..

# Create WAR file structure
echo ""
print_info "Creating WAR file..."
mkdir -p build/farmy/WEB-INF/classes
mkdir -p build/farmy/WEB-INF/lib

# Copy files
cp -r backend/classes/* build/farmy/WEB-INF/classes/
cp backend/lib/*.jar build/farmy/WEB-INF/lib/
cp backend/web.xml build/farmy/WEB-INF/

# Copy frontend files
cp -r frontend/* build/farmy/

# Create WAR
cd build
jar -cvf farmy.war -C farmy . > /dev/null
print_status "WAR file created: build/farmy.war"
cd ..

# Deploy to Tomcat
echo ""
print_info "Deploying to Tomcat..."
sudo systemctl stop tomcat10 2>/dev/null || true
sleep 2

# Remove old deployment
sudo rm -rf /var/lib/tomcat10/webapps/farmy*

# Copy WAR file
sudo cp build/farmy.war /var/lib/tomcat10/webapps/
sudo chown tomcat:tomcat /var/lib/tomcat10/webapps/farmy.war

# Start Tomcat
sudo systemctl start tomcat10
print_status "Application deployed to Tomcat"

# Wait for deployment
echo ""
print_info "Waiting for Tomcat to deploy the application..."
sleep 10

# Print success message
echo ""
echo "=================================================="
echo -e "${GREEN}  ✓ FARMY Deployment Complete!${NC}"
echo "=================================================="
echo ""
echo "📍 Application URL: http://localhost:8080/farmy/"
echo ""
echo "🔑 Default Login Credentials:"
echo "   Username: admin"
echo "   Password: admin123"
echo ""
echo "   OR"
echo ""
echo "   Username: farmer1"
echo "   Password: password"
echo ""
echo "🔧 Useful Commands:"
echo "   sudo systemctl status tomcat9     - Check Tomcat status"
echo "   sudo systemctl restart tomcat9    - Restart Tomcat"
echo "   sudo tail -f /var/log/tomcat9/catalina.out - View logs"
echo "   mysql -u root -p farmy            - Access database"
echo ""
echo "📚 Documentation: README.md"
echo ""
echo "=================================================="
