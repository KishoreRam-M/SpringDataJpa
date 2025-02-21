

### **Understanding REQUIRES_NEW (🚖 Renting a New Taxi)**
Think of **REQUIRES_NEW** like booking a **new Uber ride** separately.  

Even if something bad happens to your main work, the Uber ride **remains booked** because it is handled separately.

---

### **Real-World Scenario: Banking System (Money Transfer & Logging)**  

💡 **Problem:**  
A bank wants to **log every money transfer** in a **TransactionLog table**.  
Even if the money transfer **fails**, the log **must still be saved**.

📌 **Example Scenario:**  
1️⃣ You **transfer ₹5000** from **Account A** to **Account B**.  
2️⃣ The system **deducts ₹5000** from Account A.  
3️⃣ The system **adds ₹5000** to Account B.  
4️⃣ The system **logs** the transaction using `logTransaction()`.  
5️⃣ **If the money transfer fails (Step 2 or 3 fails), the log entry should still be saved.**  

---

### **How @Transactional(propagation = Propagation.REQUIRES_NEW) Helps**
- The `logTransaction()` method **must always save the log**, even if the money transfer **fails**.  
- We use `REQUIRES_NEW`, which **creates a separate transaction** just for logging.  
- If the main money transfer fails, the log transaction **is NOT rolled back**.

---

### **Code Example**
```java
@Service
public class LoggingService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)  // Always runs in a separate transaction
    public void logTransaction(Long accountId, String status) {
        // Save log entry in the database
        System.out.println("Logging transaction for Account ID: " + accountId + " with Status: " + status);
    }
}
```

### **How the Code Works**
1. **Main Transaction (Money Transfer) Starts**  
   - Deduct ₹5000 from Account A  
   - Add ₹5000 to Account B  
2. **Logging is done using logTransaction()**  
   - Since `REQUIRES_NEW` is used, it starts **a new, independent transaction**.  
3. **If Money Transfer Fails, Log Still Exists**  
   - Even if Step 1 **fails**, logging **is not rolled back**.

---

### **🔹 Real-World Example: Uber Booking**
Imagine:  
- You **book an Uber ride** while your bank transaction is processing.  
- **If the bank transaction fails**, your **Uber ride is still booked** because Uber runs on a **separate system**.  

Similarly, `REQUIRES_NEW` makes sure that **logging always happens**, even if the money transfer fails.

---

### **Key Takeaways**
✅ `REQUIRES_NEW` **always starts a separate transaction**.  
✅ If the **main transaction fails**, the **separate transaction is NOT rolled back**.  
✅ Useful for **logging, audit trails, notifications, etc.**  

