### **📌 Difference Between Named Queries and JPQL Queries in Spring Data JPA**

Both **Named Queries** and **JPQL Queries** are used in **Spring Data JPA** to retrieve data from the database, but they have key differences in their usage and definition.

---

## **1️⃣ What is JPQL Query (`@Query`)?**
JPQL (Java Persistence Query Language) is used in **Spring Data JPA** to query entities and is written directly inside the **repository interface** using the `@Query` annotation.

### **✅ Example: JPQL Query using `@Query`**
```java
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = :email")
    Student findByEmail(@Param("email") String email);
}
```
✔ **Uses `@Query` directly inside the repository.**  
✔ **Works at runtime**, so every time the method is called, the query is executed.  
✔ Easier to use for simple queries but **can make the repository interface look messy**.  

---

## **2️⃣ What is a Named Query (`@NamedQuery`)?**
A **Named Query** is a **predefined JPQL query** that is defined inside the **entity class** using `@NamedQuery` or `@NamedQueries` annotations.  

### **✅ Example: Named Query in the Entity Class**
```java
@Entity
@NamedQuery(name = "Student.findByEmail", query = "SELECT s FROM Student s WHERE s.email = :email")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    private String firstName;
    private String lastName;
}
```

### **✅ Calling the Named Query in Repository**
```java
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    @Query(name = "Student.findByEmail")
    Student findByEmail(@Param("email") String email);
}
```
✔ **Predefined query stored in the entity class.**  
✔ Improves **code organization** because queries are **separated from the repository.**  
✔ **Slightly faster** because queries are **compiled once at startup** instead of runtime.  

---

## **3️⃣ Key Differences: JPQL (`@Query`) vs Named Queries (`@NamedQuery`)**
| Feature | JPQL Query (`@Query`) | Named Query (`@NamedQuery`) |
|---------|----------------|----------------|
| **Definition Location** | Defined inside the repository interface | Defined inside the entity class |
| **Performance** | Compiled at runtime | Compiled at application startup (slightly faster) |
| **Code Readability** | May clutter the repository with long queries | Keeps repository cleaner |
| **Flexibility** | More flexible, can be changed easily | Fixed at startup, requires a restart to modify |
| **Best Use Case** | Simple queries or queries that change frequently | Complex or frequently used queries |

---

## **4️⃣ When to Use JPQL vs Named Queries?**
✅ **Use `@Query` (JPQL) when:**  
- You have **simple queries** that don’t repeat often.  
- The query **might change frequently** (easier to modify).  
- You want to **keep queries close to the repository** for better readability.  

✅ **Use `@NamedQuery` when:**  
- The query is **complex** and used in **multiple places**.  
- You want **precompiled queries** for **slightly better performance**.  
- You prefer **cleaner repository interfaces** (especially for large applications).  

---

## **5️⃣ Example: Named Query vs JPQL Side by Side**
#### **🔹 Named Query Approach**
📌 **Inside Entity:**
```java
@Entity
@NamedQuery(name = "Student.findByEmail", query = "SELECT s FROM Student s WHERE s.email = :email")
public class Student { ... }
```
📌 **Inside Repository:**
```java
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(name = "Student.findByEmail")
    Student findByEmail(@Param("email") String email);
}
```

---

#### **🔹 JPQL Query (`@Query`) Approach**
📌 **Inside Repository (Directly Written Query)**
```java
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.email = :email")
    Student findByEmail(@Param("email") String email);
}
```

---

### **🚀 Final Verdict**
| Use Case | **Choose** |
|----------|------------|
| Simple, infrequent queries | **JPQL (`@Query`)** |
| Queries that need **precompilation & better performance** | **Named Query (`@NamedQuery`)** |
| You want to **separate queries from the repository** for better structure | **Named Query (`@NamedQuery`)** |
| Queries that **might change frequently** | **JPQL (`@Query`)** |

