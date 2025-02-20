## **🔍 Methods & Components Used to Achieve Transactions in Spring Data JPA**  

In **Spring Data JPA**, transactions are managed using **annotations, programmatic transaction management, and database-level configurations**. Here’s a **detailed breakdown** of all the methods and approaches used to achieve transactions.

---

# **📌 1. Methods to Achieve Transactions in Spring Data JPA**  

### **🔹 A. Using `@Transactional` Annotation (Declarative Transactions)**
Spring provides **declarative transaction management** using the `@Transactional` annotation. This is the most common and recommended approach.

#### **✅ Where Can `@Transactional` Be Used?**
| Level | Usage |
|--------|--------------------------------|
| **Method-Level** | Applied to specific methods |
| **Class-Level** | Applied to all public methods in a class |

---

### **📝 Example: Using `@Transactional` at the Method Level**  
```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {

    private final AccountRepository accountRepository;

    public BankService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional  // Ensures all operations succeed together
    public void transferMoney(Long senderId, Long receiverId, double amount) {
        Account sender = accountRepository.findById(senderId).orElseThrow();
        Account receiver = accountRepository.findById(receiverId).orElseThrow();

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }
}
```
✔ **If any step fails, everything is rolled back!**  

---

### **📝 Example: Using `@Transactional` at the Class Level**
```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  // All public methods in this class are transactional
public class OrderService {

    public void placeOrder(Long orderId) {
        // Order placement logic
    }

    public void cancelOrder(Long orderId) {
        // Order cancellation logic
    }
}
```
✔ **Every method inside this class will be transactional automatically.**  

---

### **🔹 B. Using Programmatic Transactions (`PlatformTransactionManager`)**
- Instead of `@Transactional`, we can manually **start, commit, and rollback transactions** using `PlatformTransactionManager`.  
- This approach gives **fine-grained control** over transactions.

---

### **📝 Example: Manually Managing Transactions**
```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class ManualTransactionService {

    private final PlatformTransactionManager transactionManager;

    public ManualTransactionService(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void performManualTransaction() {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            // Your transactional logic here
            System.out.println("Transaction started...");

            // Simulate an error
            if (true) {
                throw new RuntimeException("Something went wrong!");
            }

            transactionManager.commit(status);  // Commit if successful
            System.out.println("Transaction committed.");
        } catch (Exception e) {
            transactionManager.rollback(status);  // Rollback on error
            System.out.println("Transaction rolled back due to error: " + e.getMessage());
        }
    }
}
```
✔ **Useful when you need explicit transaction handling in complex scenarios.**  

---

### **🔹 C. Using Spring Boot Auto-Configuration (No Extra Configuration Needed)**
Spring Boot **automatically enables transactions** when you use Spring Data JPA.  
- You don’t need to manually configure a `TransactionManager` in most cases.  
- Just use `@Transactional`, and Spring Boot will handle the rest.

---

# **📌 2. Key Features & Settings of Transactions**  

### **🔹 1. Read-Only Transactions (`@Transactional(readOnly = true)`)**
- Used to **optimize performance** for **SELECT** queries.
- Prevents Hibernate from **tracking changes**, reducing overhead.

#### **📝 Example: Read-Only Transaction**
```java
@Transactional(readOnly = true)
public List<Account> getAllAccounts() {
    return accountRepository.findAll();
}
```
✔ **Faster execution because no data is modified!**  

---

### **🔹 2. Propagation – Controlling How Transactions Behave**  
Defines how transactions **interact when calling another transactional method**.

| Propagation Type | Behavior |
|-----------------|----------------------------------------------|
| `REQUIRED` (Default) | Uses existing transaction or creates a new one. |
| `REQUIRES_NEW` | Always creates a new transaction, suspending the existing one. |
| `SUPPORTS` | Uses a transaction if available, otherwise runs without one. |
| `MANDATORY` | Requires an existing transaction; throws an error if none exists. |
| `NEVER` | Ensures no transaction exists; throws an error if one does. |
| `NESTED` | Runs inside a nested transaction within the main transaction. |

#### **📝 Example: Using Propagation**
```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void deductBalance(Long accountId, double amount) {
    // This method will always create a NEW transaction
}
```

---

### **🔹 3. Isolation Levels – Handling Concurrent Transactions**  
Isolation levels **prevent data conflicts** in concurrent transactions.

| Isolation Level | Prevents | Best Used For |
|----------------|-------------|-------------------|
| **READ_UNCOMMITTED** | Nothing (Least safe) | Performance over accuracy |
| **READ_COMMITTED** (Default) | Dirty Reads | Most applications |
| **REPEATABLE_READ** | Dirty & Non-repeatable Reads | Banking, Shopping Carts |
| **SERIALIZABLE** | All concurrency issues (Most Strict) | Highest data accuracy |

#### **📝 Example: Setting Isolation Level**
```java
@Transactional(isolation = Isolation.REPEATABLE_READ)
public void updateBalance(Long accountId, double amount) {
    // Ensures consistent data reads
}
```

---

### **🔹 4. Rollback & Exception Handling**  
By default, Spring **only rolls back transactions for unchecked exceptions (`RuntimeException`)**.

#### **📝 Example: Rollback for Checked Exceptions**
```java
@Transactional(rollbackFor = Exception.class)
public void processPayment() throws Exception {
    // Rolls back for any exception
}
```

#### **📝 Example: Preventing Rollback for Specific Exceptions**
```java
@Transactional(noRollbackFor = CustomException.class)
public void processOrder() {
    // Even if CustomException occurs, the transaction will NOT be rolled back
}
```

---

# **📌 3. Summary of Methods & Features to Achieve Transactions in Spring Data JPA**

| Feature | Description | Usage |
|---------|------------|-------|
| `@Transactional` | Automatic transaction management | Most common approach |
| `PlatformTransactionManager` | Manual transaction control | When fine-grained control is needed |
| `readOnly = true` | Optimized for read-only operations | Use for SELECT queries |
| Propagation | Controls how transactions interact | Use for complex workflows |
| Isolation Levels | Prevents concurrency issues | Use for high-consistency applications |
| Rollback Handling | Defines when to roll back | Use for controlled error handling |

---

