
---

### **🚀 Optimized HikariCP Configuration for MySQL in `application.properties`**
```properties
# ✅ MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/mydatabase
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ✅ HikariCP Connection Pool Settings
spring.datasource.hikari.minimum-idle=5                 # Minimum idle connections
spring.datasource.hikari.maximum-pool-size=20           # Maximum connections in the pool
spring.datasource.hikari.idle-timeout=30000             # Close idle connections after 30 seconds
spring.datasource.hikari.connection-timeout=30000       # Maximum wait time for a connection (30 sec)
spring.datasource.hikari.max-lifetime=1800000           # Maximum lifetime of a connection (30 min)
spring.datasource.hikari.leak-detection-threshold=2000  # Detect connection leaks if a connection is open for more than 2 sec
spring.datasource.hikari.validation-timeout=5000        # Time before validating a connection (5 sec)
spring.datasource.hikari.connection-test-query=SELECT 1 # Ensure connections are valid

# ✅ Disable Auto-Commit for Transaction Management
spring.datasource.hikari.auto-commit=false

# ✅ Hibernate Settings
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true

# ✅ Additional Hibernate Performance Optimizations
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.properties.hibernate.use_sql_comments=true

# ✅ Enable Spring Boot Actuator for Monitoring HikariCP
management.metrics.export.enabled=true
management.endpoints.web.exposure.include=metrics
```

---

### **📌 Explanation of Key Properties**
| **Property** | **Description** |
|-------------|---------------|
| `spring.datasource.hikari.minimum-idle=5` | Keeps at least 5 connections open, preventing cold starts |
| `spring.datasource.hikari.maximum-pool-size=20` | Limits max connections to 20 (avoid excessive database load) |
| `spring.datasource.hikari.idle-timeout=30000` | Closes idle connections after 30 seconds |
| `spring.datasource.hikari.connection-timeout=30000` | Waits up to 30 seconds for a new connection |
| `spring.datasource.hikari.max-lifetime=1800000` | Closes connections older than 30 minutes to prevent stale connections |
| `spring.datasource.hikari.leak-detection-threshold=2000` | Logs warnings for unclosed connections after 2 seconds |
| `spring.datasource.hikari.validation-timeout=5000` | Validates connections before using them |
| `spring.datasource.hikari.connection-test-query=SELECT 1` | Ensures the connection is alive before usage |
| `spring.datasource.hikari.auto-commit=false` | Prevents automatic commits, improving transaction control |

---

### **🚀 Next Steps**
- ✅ **Monitor Connection Pool Usage** via **Spring Boot Actuator**
  - Visit:  
    ```
    http://localhost:8080/actuator/metrics/hikaricp.connections
    ```
  - Get real-time stats on HikariCP.

- ✅ **Further Optimization**
  - If your system has **high concurrency**, increase `maximum-pool-size`.
  - Reduce `idle-timeout` if **connections stay idle for too long**.
