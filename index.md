

## **1️⃣ Basic Indexing in Hibernate**
In Hibernate, you can create indexes using the `@Table` annotation with the `@Index` annotation inside the `@Index` array.

### **🔹 Example: Creating an Index in Hibernate**
```java
import jakarta.persistence.*;

@Entity
@Table(name = "students", indexes = {
    @Index(name = "idx_student_name", columnList = "name")
})
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true)
    private String email;

    // Getters and Setters
}
```

✅ **Explanation:**
- `@Table(indexes = { @Index(...) })` → Defines an index on the `name` column.
- `@Index(name = "idx_student_name", columnList = "name")` → Creates an index named `idx_student_name` for faster searches.
- `@Column(unique = true)` → Automatically creates a **unique index** on `email`.

---

## **2️⃣ Creating a Composite Index (Index on Multiple Columns)**
A **composite index** speeds up queries that filter using multiple columns.

### **🔹 Example: Indexing on `name` and `dob`**
```java
@Entity
@Table(name = "students", indexes = {
    @Index(name = "idx_name_dob", columnList = "name, dob")
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dob; // Date of Birth

    // Getters and Setters
}
```

✅ **Explanation:**
- `@Index(name = "idx_name_dob", columnList = "name, dob")` → Optimizes searches that use **both `name` and `dob`** together.

🔹 **Example Query That Benefits from the Index:**
```sql
SELECT * FROM students WHERE name = 'Kishore' AND dob = '2000-05-15';
```
📌 **The index helps fetch results faster instead of scanning the entire table.**

---

## **3️⃣ Creating a Unique Index in Hibernate**
A **unique index** ensures that values in a column remain unique.

### **🔹 Example: Unique Index on `email`**
```java
@Entity
@Table(name = "employees", indexes = {
    @Index(name = "idx_email", columnList = "email", unique = true)
})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String department;
}
```

✅ **Explanation:**
- `@Column(unique = true)` → **Ensures uniqueness at the column level.**
- `@Index(name = "idx_email", columnList = "email", unique = true)` → **Creates a database-level unique index**.

📌 **Best Practice:** Use `@Column(unique = true)` instead of `@Index(unique = true)`, as the database automatically handles unique constraints.

---

## **4️⃣ Creating a Clustered Index (PostgreSQL Specific)**
In PostgreSQL, you can create a **clustered index** using Hibernate.

### **🔹 Example:**
```java
@Entity
@Table(name = "orders", indexes = {
    @Index(name = "idx_order_date", columnList = "order_date")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String order_date;
}
```
Then, run the following SQL command in PostgreSQL:
```sql
CLUSTER students USING idx_order_date;
```
📌 **This makes the index the primary way of sorting the table.**

---

## **5️⃣ Performance Testing: Checking Index Usage in Hibernate**
To **check if the index is being used**, enable Hibernate SQL logging.

### **🔹 Enable SQL Logging in `application.properties` (Spring Boot)**
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
Then, run a query:
```java
List<Student> students = entityManager.createQuery(
    "SELECT s FROM Student s WHERE s.name = :name", Student.class)
    .setParameter("name", "Kishore")
    .getResultList();
```
📌 **Check the generated SQL. If it uses `USING INDEX`, the index is working!**

---

## **🔹 Summary (Key Takeaways)**
✅ **Indexes improve query performance by avoiding full-table scans.**  
✅ **Use `@Index` with `@Table` to create database indexes.**  
✅ **For unique constraints, prefer `@Column(unique = true)`.**  
✅ **Test index usage with SQL logging in Hibernate.**  
✅ **Clustered indexes physically order table data for faster range queries.**  

