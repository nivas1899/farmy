# FARMY - Farm Management & Biosecurity System

🌾 A comprehensive web-based farm management system to digitally manage animal batches, health status, vaccinations, visitor biosecurity, and farm analytics through a centralized dashboard.

## 📋 Features

- **🔐 User Authentication**: Secure login and registration system
- **🐖 Animal Management**: Track animal batches, individual health status, and batch grouping
- **💉 Vaccination Scheduling**: Schedule and track vaccinations with status monitoring
- **🚶 Visitor Biosecurity**: Log and track farm visitors for biosecurity compliance
- **📊 Dashboard Analytics**: Real-time metrics with Chart.js visualizations
- **📑 Comprehensive Reports**: Generate and export farm reports

## 🏗️ Architecture

```
├── Frontend (HTML/CSS/JavaScript)
│   ├── index.html - Landing page
│   ├── login.html - Authentication
│   ├── dashboard.html - Main dashboard
│   ├── pages/
│   │   ├── animals.html
│   │   ├── vaccination.html
│   │   ├── visitors.html
│   │   └── reports.html
│   ├── css/styles.css - Design system
│   └── js/
│       ├── api.js - API utilities
│       └── dashboard.js - Dashboard logic
│
├── Backend (Java Servlets)
│   ├── servlet/ - RESTful endpoints
│   ├── dao/ - Data access layer
│   ├── model/ - Entity models
│   ├── db/ - Database connection
│   └── web.xml - Servlet configuration
│
└── Database (MySQL/MariaDB)
    └── farmy.sql - Database schema
```

## 🚀 Installation & Setup

### Prerequisites

- **Java Development Kit (JDK)** 8 or higher
- **Apache Tomcat** 10.0 or higher
- **MySQL** or **MariaDB** 5.7+
- **Maven** (optional, for dependency management)

### Step 1: Database Setup

1. **Install MySQL/MariaDB** (if not already installed)

2. **Create the database**:
   ```bash
   mysql -u root -p < database/farmy.sql
   ```

3. **Verify database creation**:
   ```bash
   mysql -u root -p
   mysql> USE farmy;
   mysql> SHOW TABLES;
   ```

4. **Update database credentials** in `DBConnection.java`:
   ```java
   private static final String USER = "root";
   private static final String PASSWORD = "your_password_here";
   ```

### Step 2: Backend Setup

1. **Download required JAR files**:

   Create a `backend/lib` directory and add the following libraries:

   - **MySQL Connector/J**: [Download](https://dev.mysql.com/downloads/connector/j/)
     ```
     mysql-connector-java-8.0.33.jar
     ```

   - **Jakarta Servlet API**: [Download](https://jakarta.ee/specifications/servlet/)
     ```
     jakarta.servlet-api-5.0.0.jar
     ```

   - **JSON Library**: [Download](https://github.com/stleary/JSON-java)
     ```
     json-20230227.jar
     ```

2. **Alternative: Maven setup** (optional)

   Create `pom.xml`:
   ```xml
   <dependencies>
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <version>8.0.33</version>
       </dependency>
       <dependency>
           <groupId>jakarta.servlet</groupId>
           <artifactId>jakarta.servlet-api</artifactId>
           <version>5.0.0</version>
           <scope>provided</scope>
       </dependency>
       <dependency>
           <groupId>org.json</groupId>
           <artifactId>json</artifactId>
           <version>20230227</version>
       </dependency>
   </dependencies>
   ```

3. **Compile Java files**:

   ```bash
   cd backend
   javac -cp "lib/*" -d classes src/**/*.java
   ```

   Or use your IDE (Eclipse, IntelliJ IDEA, NetBeans) to compile.

### Step 3: Create WAR File

1. **Create WAR structure**:
   ```
   farmy.war
   ├── WEB-INF/
   │   ├── web.xml
   │   ├── classes/ (compiled .class files)
   │   └── lib/ (JAR dependencies)
   ├── index.html
   ├── login.html
   ├── dashboard.html
   ├── pages/
   ├── css/
   └── js/
   ```

2. **Package as WAR**:
   ```bash
   cd farmy
   jar -cvf farmy.war *
   ```

   Or use Maven:
   ```bash
   mvn clean package
   ```

### Step 4: Deploy to Tomcat

1. **Copy WAR to Tomcat**:
   ```bash
   cp farmy.war /path/to/tomcat/webapps/
   ```

2. **Start Tomcat**:
   ```bash
   /path/to/tomcat/bin/startup.sh  # Linux/Mac
   # or
   /path/to/tomcat/bin/startup.bat  # Windows
   ```

3. **Access the application**:
   ```
   http://localhost:8080/farmy/
   ```

## 🔧 Configuration

### Database Configuration

Edit `backend/src/db/DBConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/farmy";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

### API Base URL

Edit `frontend/js/api.js` if Tomcat context path is different:

```javascript
const API_BASE_URL = '/farmy'; // Change if needed
```

## 📊 Usage

### 1. First Time Setup

1. Navigate to `http://localhost:8080/farmy/`
2. Click "Get Started" or "Login"
3. Click "Register" and create an account
4. Login with your credentials

### 2. Dashboard

- View real-time farm metrics
- Monitor animal health status with pie charts
- Check pending vaccinations
- Track daily visitors

### 3. Animal Management

- Add animals to batches
- Update health status (Normal, Sick, Isolated, Dead)
- Track individual animal codes
- Delete records as needed

### 4. Vaccination Management

- Schedule vaccinations by batch
- Mark vaccinations as done
- View pending/overdue vaccines
- Delete schedules

### 5. Visitor Tracking

- Log visitor entries
- Record sanitization status
- View today's visitors
- Full visitor history

### 6. Reports

- Comprehensive farm reports
- Print-friendly layouts
- Export data

## 🛠️ Development

### Project Structure

```
farmy/
├── backend/
│   ├── src/
│   │   ├── dao/           # Data Access Objects
│   │   ├── db/            # Database connection
│   │   ├── model/         # Entity models
│   │   └── servlet/       # REST API endpoints
│   ├── lib/               # JAR dependencies
│   └── web.xml            # Servlet configuration
├── frontend/
│   ├── css/styles.css     # Design system
│   ├── js/
│   │   ├── api.js         # API utilities
│   │   └── dashboard.js   # Dashboard logic
│   ├── pages/             # Feature pages
│   ├── index.html         # Landing page
│   ├── login.html         # Authentication
│   └── dashboard.html     # Main dashboard
└── database/
    └── farmy.sql          # Database schema
```

### API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/login` | POST | User authentication |
| `/register` | POST | User registration |
| `/dashboard` | GET | Dashboard metrics |
| `/animals` | GET | List all animals |
| `/animals` | POST | Add new animal |
| `/animals?id=X&status=Y` | PUT | Update status |
| `/animals?id=X` | DELETE | Delete animal |
| `/vaccinations` | GET | List vaccinations |
| `/vaccinations?filter=pending` | GET | Pending vaccines |
| `/vaccinations` | POST | Schedule vaccine |
| `/vaccinations?id=X&status=Done` | PUT | Mark as done |
| `/vaccinations?id=X` | DELETE | Delete schedule |
| `/visitors` | GET | List all visitors |
| `/visitors?filter=today` | GET | Today's visitors |
| `/visitors` | POST | Log visitor entry |

### Testing

1. **Database Connection Test**:
   ```bash
   mysql -u root -p farmy
   mysql> SELECT COUNT(*) FROM animals;
   ```

2. **API Testing** (using curl):
   ```bash
   # Test registration
   curl -X POST http://localhost:8080/farmy/register \
     -H "Content-Type: application/json" \
     -d '{"username":"test","password":"test123"}'
   
   # Test login
   curl -X POST http://localhost:8080/farmy/login \
     -H "Content-Type: application/json" \
     -d '{"username":"test","password":"test123"}'
   
   # Test dashboard
   curl http://localhost:8080/farmy/dashboard
   ```

## 🔒 Security Notes

⚠️ **Important**: This is a demonstration application. For production use:

1. **Replace MD5 hashing** with bcrypt or Argon2
2. **Implement HTTPS** for encrypted communication
3. **Add CSRF protection**
4. **Implement rate limiting**
5. **Add input sanitization**
6. **Use prepared statements** (already implemented)
7. **Configure proper session management**
8. **Add authentication filters** for protected pages

## 🐛 Troubleshooting

### Database Connection Issues

```
Error: Access denied for user 'root'@'localhost'
```
- Check MySQL credentials in `DBConnection.java`
- Verify MySQL service is running: `systemctl status mysql`

### Servlet Not Found

```
HTTP 404 - Not Found
```
- Check `web.xml` servlet mappings
- Verify WAR deployment: `ls /tomcat/webapps/`
- Check Tomcat logs: `tail -f /tomcat/logs/catalina.out`

### CORS Issues

```
Access to fetch blocked by CORS policy
```
- Verify `CorsFilter` is configured in `web.xml`
- Check API_BASE_URL in `api.js`

### Chart.js Not Displaying

- Check browser console for errors
- Verify Chart.js CDN is accessible
- Ensure canvas element has proper ID

## 📝 License

This project is open source and available for educational purposes.

## 👤 Default Login Credentials

For testing with sample data:

- **Username**: `admin`
- **Password**: `admin123`

or

- **Username**: `farmer1`
- **Password**: `password`

## 🤝 Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues.

## 📧 Support

For support and questions, please open an issue in the repository.

---

**Built with ❤️ for modern farm management**
