# Logging Concepts & Definitions

## 1. Log Levels
Logs are categorized by their severity:

| Level  | Purpose                  | Example                                    |
|--------|--------------------------|--------------------------------------------|
| TRACE  | Detailed debugging info  | "Entering method processOrder()"          |
| DEBUG  | General debugging        | "Fetching user details for ID: 123"       |
| INFO   | General system info      | "User logged in successfully"             |
| WARN   | Potential issues         | "Memory usage exceeding 80%"              |
| ERROR  | Serious errors           | "Database connection failed"              |
| FATAL  | Critical system failure  | "System shutting down unexpectedly!"      |

✅ **Best Practice:** Use the right level for each situation. Avoid logging sensitive information (e.g., passwords).

## 2. Log Format
A log entry typically contains:

```
[Timestamp] [Level] [Component] - Message
```

✅ **Example:**
```
[2025-02-22 10:15:30] INFO [UserService] - User 'john_doe' logged in
```
✅ **Best Practice:** Always include timestamps, log levels, and meaningful messages.

---

# How to Implement Logging in Java

## 1️⃣ Using SLF4J and Logback (Default in Spring Boot)

### ✅ Step 1: Add Dependencies
Spring Boot already includes SLF4J and Logback, so no extra setup is needed.
If you're using a non-Spring project, add this to `pom.xml`:

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.7</version>
</dependency>
```

### ✅ Step 2: Use Logging in Code
Use SLF4J in your Java class:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        return "User Data";
    }

    @PostMapping
    public String createUser(@RequestBody String user) {
        logger.warn("Creating new user: {}", user);
        return "User Created";
    }
}
```

✅ **Explanation:**
- `logger.info()` → Logs normal events.
- `logger.warn()` → Logs warnings.
- `logger.error()` → Logs errors.

✅ **Best Practice:** Use placeholders (`{}`) instead of string concatenation for better performance.

### ✅ Step 3: Configure Log Levels (`application.properties`)
Define log levels in `src/main/resources/application.properties`:

```properties
# Set log level (DEBUG, INFO, WARN, ERROR)
logging.level.root=INFO
logging.level.com.example=DEBUG

# Save logs to a file
logging.file.name=app.log
```

✅ **Best Practice:** Keep logs structured and consistent for easier debugging.

---

## 2️⃣ Using Log4j2 (Alternative to Logback)

### ✅ Step 1: Add Log4j2 Dependency

```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.17.1</version>
</dependency>
```

### ✅ Step 2: Create Log4j2 Configuration (`log4j2.xml`)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="INFO">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%p] %c - %m%n"/>
        </Console>
        <File name="File" fileName="logs/app.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%p] %c - %m%n"/>
        </File>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```

✅ **Best Practice:** Store logs in a separate `logs/` directory for easy access.

---

# Advanced Logging Techniques

## 1️⃣ Logging to a Database
Instead of saving logs in a file, you can store them in PostgreSQL or MySQL.

### ✅ Step 1: Add Logback Database Appender (`logback.xml`)

```xml
<appender name="DB" class="ch.qos.logback.classic.db.DBAppender">
    <connectionSource class="ch.qos.logback.core.db.DriverManagerConnectionSource">
        <driverClass>org.postgresql.Driver</driverClass>
        <url>jdbc:postgresql://localhost:5432/logs</url>
        <user>postgres</user>
        <password>password</password>
    </connectionSource>
</appender>
```

✅ **Best Practice:** Use a separate database for logs to avoid slowing down the main system.

---

## 2️⃣ Logging User Activity (Audit Logs)
Track who did what and when for security compliance.

```java
logger.info("User '{}' updated profile at {}", username, LocalDateTime.now());
```

✅ **Example Log Output:**
```
[2025-02-22 14:10:45] INFO [AuditService] - User 'john_doe' updated profile at 2025-02-22 14:10:45
```

---

## 3️⃣ External Log Monitoring with ELK Stack
For large applications, use **ELK (Elasticsearch, Logstash, Kibana)** to store and analyze logs.

✅ **Flow:**
1. Application Logs → Logstash (Collects logs)
2. Logstash → Elasticsearch (Stores logs)
3. Kibana (Visualizes logs in a dashboard)

---

# Summary & Best Practices

| Best Practice | Why? |
|--------------|------|
| Use appropriate log levels | Prevent unnecessary logs from cluttering |
| Store logs in a separate file/database | Improves system performance |
| Use structured logs | Makes debugging easier |
| Don’t log sensitive data | Prevents security leaks |
| Implement log rotation | Avoids huge log files slowing down storage |

✅ **Real-World Example:**

**E-commerce System Logs**
```
INFO: "User added item to cart"
WARN: "High traffic detected, response time slow"
ERROR: "Payment failed due to timeout"
```

