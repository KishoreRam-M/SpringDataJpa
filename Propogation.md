

# **🚀 Understanding Transaction Propagation with Banking System Example**  
Let's say we are building a **banking application** where users can **transfer money** between accounts.  
- The system **deducts money** from one account (`debit`).  
- The system **adds money** to another account (`credit`).  
- The system **logs** the transaction details.  

💡 **The goal** is to understand how different **transaction propagation types** behave in different failure cases.

---

## **1️⃣ REQUIRED (🚗 Carpooling – Use the existing transaction or create a new one)**  
### **Scenario:**  
A user transfers ₹5000 from Account A to Account B.  
- The system **starts a transaction**.  
- It calls the `debitAccount()` method to deduct ₹5000 from Account A.  
- It calls the `creditAccount()` method to add ₹5000 to Account B.  

✅ If everything works, the transaction is **committed**.  
❌ If **either debit or credit fails, the entire transaction is rolled back**.

### **Example Code:**
```java
@Service
public class BankService {
    
    @Transactional(propagation = Propagation.REQUIRED)  // Uses existing transaction or creates a new one
    public void transferMoney(Long fromAccount, Long toAccount, Double amount) {
        debitAccount(fromAccount, amount);  // Deduct money
        creditAccount(toAccount, amount);   // Add money
    }

    public void debitAccount(Long accountId, Double amount) {
        // Deduct money from Account A
    }

    public void creditAccount(Long accountId, Double amount) {
        // Add money to Account B
    }
}
```
📌 **Key Point:**  
- If an **existing transaction is already running**, it **joins it**.  
- Otherwise, it **creates a new transaction**.  

---

## **2️⃣ REQUIRES_NEW (🚖 Renting a new taxi – Always starts a new transaction separately)**  
### **Scenario:**  
- The bank **logs every transaction** in a `TransactionLog` table.  
- Even if money transfer **fails**, the **log entry should still be saved**.  

### **How it Works:**  
1️⃣ `transferMoney()` starts a transaction.  
2️⃣ It **deducts** ₹5000 from Account A.  
3️⃣ It **adds** ₹5000 to Account B.  
4️⃣ It **logs the transaction** using `logTransaction()`.  
5️⃣ If the **money transfer fails**, the log **should not be rolled back**.  

### **Example Code:**
```java
@Service
public class LoggingService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)  // Always creates a new transaction
    public void logTransaction(Long accountId, String status) {
        // Save log entry in the database
    }
}
```
📌 **Key Point:**  
- This method **ALWAYS** runs in a **separate transaction**.  
- If the **main transaction fails**, the **log entry still remains**.  

🔹 **Real-World Example:**  
Imagine you **book an Uber** while your **bank transaction is processing**.  
- If the **transaction fails**, your **Uber ride is still booked** because it’s in a separate system.  

---

## **3️⃣ SUPPORTS (🚶 Hitchhiking – Uses a transaction if available, otherwise runs normally)**  
### **Scenario:**  
- A **reporting service** fetches **account details**.  
- If called **inside** a transaction, it **joins it**.  
- If called **outside**, it runs **without a transaction**.  

### **Example Code:**
```java
@Repository
public class AccountRepository {
    
    @Transactional(propagation = Propagation.SUPPORTS)  // Uses transaction if available, else runs normally
    public Account getAccountDetails(Long accountId) {
        return entityManager.find(Account.class, accountId);
    }
}
```
📌 **Key Point:**  
- If **called inside a transaction**, it **joins** the transaction.  
- If **called alone**, it **runs normally** (without a transaction).  

🔹 **Real-World Example:**  
Imagine you are **hitchhiking** (getting a ride).  
- If a **car (transaction)** is available, you **join it**.  
- If no car is available, you **walk (run without a transaction)**.  

---

## **4️⃣ MANDATORY (🚦 Driver’s License Required – Must have a transaction, or it fails)**  
### **Scenario:**  
- Applying **late fees** should **always** be part of a transaction.  
- If called **outside a transaction**, it should **fail**.  

### **Example Code:**
```java
@Service
public class LoanService {

    @Transactional(propagation = Propagation.MANDATORY)  // Must run inside a transaction
    public void applyLateFee(Long accountId, Double fee) {
        // Apply late fee
    }
}
```
📌 **Key Point:**  
- If **called inside another transaction**, it works fine.  
- If **called alone**, it **throws an error**.  

🔹 **Real-World Example:**  
Imagine you **apply for a driving test**.  
- If you **already have a learner’s license (existing transaction)**, you can proceed.  
- If you **don’t have a learner’s license (no transaction exists)**, you **can’t apply**.  

---

## **5️⃣ NEVER (🚴 Biking on a pedestrian street – Must NOT run inside a transaction)**  
### **Scenario:**  
- Generating a **bank report** should **not** be part of a transaction.  
- If it runs **inside a transaction**, it **should fail**.  

### **Example Code:**
```java
@Service
public class ReportService {

    @Transactional(propagation = Propagation.NEVER)  // Must NOT run inside a transaction
    public List<TransactionReport> generateReport() {
        return reportRepository.getTransactionReport();
    }
}
```
📌 **Key Point:**  
- If a **transaction exists**, it **throws an error**.  
- If **no transaction exists**, it runs normally.  

🔹 **Real-World Example:**  
Imagine a **bicycle-only street**.  
- If you **ride a bicycle (no transaction)**, it’s fine.  
- If you **bring a car (transaction exists)**, you **must not enter**.  

---

## **6️⃣ NESTED (🎢 Roller Coaster in a Theme Park – Runs inside a transaction but can roll back separately)**  
### **Scenario:**  
- **Checking credit score** should be inside the main transaction.  
- If it fails, only the **credit check part rolls back**, not the whole transaction.  

### **Example Code:**
```java
@Service
public class CreditScoreService {

    @Transactional(propagation = Propagation.NESTED)  // Runs inside main transaction, but can roll back separately
    public void checkCreditScore(Long customerId) {
        if (!hasGoodCredit(customerId)) {
            throw new RuntimeException("Bad Credit Score"); // Rolls back only this part
        }
    }
}
```
📌 **Key Point:**  
- If **credit check fails**, only this part rolls back.  
- The **main transaction continues**.  

🔹 **Real-World Example:**  
Imagine a **roller coaster ride** inside a theme park.  
- If **one ride (nested transaction) breaks down**, only **that ride is stopped**.  
- The **entire theme park (main transaction) remains open**.  

---

## **Final Summary Table**
| Propagation Type | Behavior | Real-World Example |
|-----------------|----------|--------------------|
| **REQUIRED** | Uses existing transaction or creates new one | 🚗 Carpooling – Join if available, else start new |
| **REQUIRES_NEW** | Always starts a new transaction | 🚖 Renting a taxi – Independent from others |
| **SUPPORTS** | Uses a transaction if available | 🚶 Hitchhiking – Ride if possible, else walk |
| **MANDATORY** | Must run inside a transaction | 🚦 Driver’s license required – Must have a license |
| **NEVER** | Must run outside a transaction | 🚴 Bicycle-only street – No cars allowed |
| **NESTED** | Runs inside a transaction but can roll back separately | 🎢 Roller coaster in a theme park |
