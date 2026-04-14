# Quick Start Guide - Debian/Ubuntu Linux

## Automated Deployment (Recommended)

Run the deployment script:

```bash
cd /home/nivx/farmy/farmy
./deploy.sh
```

**The script will:**
1. Install Java JDK 11
2. Install MariaDB Server
3. Install Apache Tomcat 9
4. Download required JAR dependencies
5. Setup database
6. Compile Java code
7. Create and deploy WAR file

**Follow the prompts** to enter:
- Your sudo password
- MySQL root password
- Database credentials

---

## Manual Installation (If Script Fails)

### Step 1: Install Dependencies

```bash
# Update package list
sudo apt-get update

# Install Java JDK 11
sudo apt-get install -y openjdk-11-jdk

# Install MariaDB
sudo apt-get install -y mariadb-server mariadb-client

# Install Tomcat 10
sudo apt-get install -y tomcat10 tomcat10-admin

# Install build tools
sudo apt-get install -y wget curl unzip
```

### Step 2: Start Services

```bash
# Start and enable MariaDB
sudo systemctl start mariadb
sudo systemctl enable mariadb

# Start and enable Tomcat
sudo systemctl start tomcat10
sudo systemctl enable tomcat10
```

### Step 3: Secure MySQL (Optional but Recommended)

```bash
sudo mysql_secure_installation
```

### Step 4: Setup Database

```bash
cd /home/nivx/farmy/farmy

# Create database (without password)
sudo mysql -u root < database/farmy.sql

# OR with password
mysql -u root -p < database/farmy.sql
```

### Step 5: Download JAR Dependencies

```bash
cd /home/nivx/farmy/farmy
mkdir -p backend/lib

# Download MySQL Connector
wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar \
  -O backend/lib/mysql-connector-java-8.0.33.jar

# Download Servlet API
wget https://repo1.maven.org/maven2/jakarta/servlet/jakarta.servlet-api/5.0.0/jakarta.servlet-api-5.0.0.jar \
  -O backend/lib/jakarta.servlet-api-5.0.0.jar

# Download JSON library
wget https://repo1.maven.org/maven2/org/json/json/20230227/json-20230227.jar \
  -O backend/lib/json-20230227.jar
```

### Step 6: Configure Database Credentials

Edit `backend/src/db/DBConnection.java`:

```java
private static final String USER = "root";  // Your MySQL username
private static final String PASSWORD = "";  // Your MySQL password
```

### Step 7: Compile Java Code

```bash
cd /home/nivx/farmy/farmy/backend

# Create output directory
mkdir -p classes

# Compile all Java files
find src -name "*.java" > sources.txt
javac -cp "lib/*" -d classes @sources.txt
rm sources.txt
```

### Step 8: Create WAR File

```bash
cd /home/nivx/farmy/farmy

# Create build structure
mkdir -p build/farmy/WEB-INF/classes
mkdir -p build/farmy/WEB-INF/lib

# Copy compiled classes
cp -r backend/classes/* build/farmy/WEB-INF/classes/

# Copy JAR libraries
cp backend/lib/*.jar build/farmy/WEB-INF/lib/

# Copy web.xml
cp backend/web.xml build/farmy/WEB-INF/

# Copy frontend files
cp -r frontend/* build/farmy/

# Create WAR file
cd build
jar -cvf farmy.war -C farmy .
```

### Step 9: Deploy to Tomcat

```bash
# Stop Tomcat
sudo systemctl stop tomcat10

# Remove old deployment
sudo rm -rf /var/lib/tomcat10/webapps/farmy*

# Copy WAR file
sudo cp /home/nivx/farmy/farmy/build/farmy.war /var/lib/tomcat10/webapps/

# Set permissions
sudo chown tomcat:tomcat /var/lib/tomcat10/webapps/farmy.war

# Start Tomcat
sudo systemctl start tomcat10
```

### Step 10: Access Application

Wait 10-15 seconds for Tomcat to deploy, then open:

```
http://localhost:8080/farmy/
```

---

## Testing

### Check Services Status

```bash
# Check Java
java -version

# Check MariaDB
sudo systemctl status mariadb

# Check Tomcat
sudo systemctl status tomcat10

# Check if port 8080 is listening
sudo netstat -tlnp | grep 8080
```

### View Tomcat Logs

```bash
sudo tail -f /var/log/tomcat10/catalina.out
```

### Test Database Connection

```bash
mysql -u root -p
```

Then run:
```sql
USE farmy;
SHOW TABLES;
SELECT COUNT(*) FROM animals;
EXIT;
```

---

## Default Login

- **Username:** admin
- **Password:** admin123

OR

- **Username:** farmer1
- **Password:** password

---

## Troubleshooting

### Tomcat Won't Start

```bash
# Check logs
sudo journalctl -u tomcat10 -n 50

# Check if port is already in use
sudo lsof -i :8080
```

### Database Connection Failed

1. Check MySQL is running: `sudo systemctl status mariadb`
2. Verify credentials in `DBConnection.java`
3. Test connection: `mysql -u root -p farmy`

### 404 Error

1. Check WAR is deployed: `ls /var/lib/tomcat10/webapps/`
2. Wait for deployment: `sudo tail -f /var/log/tomcat10/catalina.out`
3. Restart Tomcat: `sudo systemctl restart tomcat10`

### Compilation Errors

Make sure all JAR files are in `backend/lib/`:
```bash
ls -lh backend/lib/
```

Should show:
- mysql-connector-java-8.0.33.jar
- jakarta.servlet-api-5.0.0.jar
- json-20230227.jar

---

## Quick Commands Reference

```bash
# Restart Tomcat
sudo systemctl restart tomcat10

# View logs
sudo tail -f /var/log/tomcat10/catalina.out

# Check deployment
ls /var/lib/tomcat10/webapps/

# Access database
mysql -u root -p farmy

# Check services
sudo systemctl status mariadb tomcat10
```
