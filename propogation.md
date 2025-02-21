# **🚀 Spring Transaction Propagation - Full Breakdown (Beginner to Advanced)**  

### **📝 What is Transaction Propagation?**
Transaction propagation **defines how a method should behave when an existing transaction is present**. It controls whether a method should **reuse, suspend, or create a new transaction** when invoked inside another transactional method.

---

## **🔹 Different Types of Transaction Propagation**
| 🔢 **Propagation Type** | 🔄 **Behavior** | ✅ **When to Use?** |
|----------------|------------|----------------------|
| **1️⃣ REQUIRED** | Uses existing transaction, or creates a new one if none exists. | Default behavior, suitable for most cases. |
| **2️⃣ REQUIRES_NEW** | Suspends current transaction and creates a new one. | When an operation must always run in its own transaction. |
| **3️⃣ SUPPORTS** | Joins an existing transaction if available; otherwise, runs without a transaction. | Read operations where a transaction is optional. |
| **4️⃣ NOT_SUPPORTED** | Suspends the transaction and runs non-transactionally. | For long-running operations that don’t need a transaction. |
| **5️⃣ NEVER** | Fails if a transaction exists, otherwise runs without one. | Enforce a method to never run in a transaction. |
| **6️⃣ MANDATORY** | Throws an error if no transaction exists. | Requires an already existing transaction. |
| **7️⃣ NESTED** | Runs inside the main transaction but allows partial rollback of its own changes. | Useful when a sub-operation can fail without affecting the main transaction. |

---

## **📝 1️⃣ REQUIRED (Default)**
🔹 **If a transaction exists, join it. If not, create a new one.**  
✅ **Best For:** General-purpose operations.

```java
@Transactional(propagation = Propagation.REQUIRED) 
public void processPayment() {
    orderService.createOrder(); // Runs inside the same transaction
}
```
✔️ **If `createOrder()` fails, the whole transaction rolls back.**  
✔️ **If no transaction exists, one is created.**

📌 **Real-World Example:**  
🔸 **Taking a bus** → If a bus is available, board it. If not, wait for a new one.

---

## **📝 2️⃣ REQUIRES_NEW**
🔹 **Suspends the existing transaction and starts a new one.**  
✅ **Best For:** Logging or operations that must always commit independently.

```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void logTransaction(Long userId, String message) {
    logRepo.save(new LogEntry(userId, message));
}
```
✔️ **Even if the main transaction fails, logs are always saved.**  
❌ **More overhead due to transaction suspension.**

📌 **Real-World Example:**  
🔸 **Sending an email receipt** → Even if payment fails, the email is sent.

---

## **📝 3️⃣ SUPPORTS**
🔹 **Runs within a transaction if one exists, otherwise runs non-transactionally.**  
✅ **Best For:** Read operations where transactions are optional.

```java
@Transactional(propagation = Propagation.SUPPORTS)
public Student getStudent() {
    return studentRepo.findById(1).orElse(null);
}
```
✔️ **If called within a transaction, it joins it.**  
✔️ **If called without a transaction, runs normally.**  
❌ **Does not guarantee rollback.**

📌 **Real-World Example:**  
🔸 **Hitchhiking** → If a car (transaction) is available, ride it; otherwise, walk.

---

## **📝 4️⃣ NOT_SUPPORTED**
🔹 **Suspends the existing transaction and runs the method non-transactionally.**  
✅ **Best For:** Long-running or performance-heavy operations that don’t need a transaction.

```java
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public List<Report> generateReports() {
    return reportRepo.getAllReports();
}
```
✔️ **Prevents blocking database resources.**  
❌ **Any failure cannot be rolled back.**

📌 **Real-World Example:**  
🔸 **Watching a movie at home** → Theaters have schedules (transactions), but at home, no scheduling needed.

---

## **📝 5️⃣ NEVER**
🔹 **Throws an error if a transaction exists. Runs normally otherwise.**  
✅ **Best For:** Ensuring a method **never** runs inside a transaction.

```java
@Transactional(propagation = Propagation.NEVER)
public List<TransactionReport> generateReport() {
    return reportRepo.getTransactionReport();
}
```
✔️ **Fails if a transaction exists.**  
❌ **Prevents rollback if needed.**

📌 **Real-World Example:**  
🔸 **Bicycle-only road** → If you bring a car (transaction), you're not allowed.

---

## **📝 6️⃣ MANDATORY**
🔹 **Must run inside an existing transaction, else throws an error.**  
✅ **Best For:** Methods that **must be called within a transaction**.

```java
@Transactional(propagation = Propagation.MANDATORY)
public void updateStudent() {
    studentRepo.save(new Student("Kishore"));
}
```
✔️ **Ensures the method is always transactional.**  
❌ **Fails if no transaction is active.**

📌 **Real-World Example:**  
🔸 **Visa requirement for traveling** → If you don't have a visa (transaction), you cannot travel.

---

## **📝 7️⃣ NESTED**
🔹 **Runs inside the main transaction but allows partial rollback.**  
✅ **Best For:** Scenarios where sub-operations can fail independently.

```java
@Transactional(propagation = Propagation.NESTED)
public void checkCreditScore(Long customerId) {
    if (!hasGoodCredit(customerId)) {
        throw new RuntimeException("Bad Credit Score");
    }
}
```
✔️ **If `checkCreditScore()` fails, only this part rolls back.**  
✔️ **Main transaction continues.**  
❌ **Requires JDBC savepoints, so not all databases support it.**


## **🎯 Choosing the Right Propagation Type**
| **Use Case** | **Best Propagation** |
|------------|------------------|
| General DB operations (default) | **REQUIRED** |
| Independent operations like logging | **REQUIRES_NEW** |
| Read-only operations (optional transaction) | **SUPPORTS** |
| Long-running tasks (non-transactional) | **NOT_SUPPORTED** |
| Must NEVER run in a transaction | **NEVER** |
| Must ALWAYS run in a transaction | **MANDATORY** |
| Sub-transaction rollback without affecting main transaction | **NESTED** |

---

## **🔥 Summary**
| **Propagation Type** | **Joins Existing?** | **Creates New?** | **Rolls Back Separately?** |
|----------------|----------------|--------------|------------------|
| **REQUIRED** | ✅ Yes | ✅ If none exists | ❌ No |
| **REQUIRES_NEW** | ❌ No (Suspends) | ✅ Always | ✅ Yes |
| **SUPPORTS** | ✅ Yes (if exists) | ❌ No | ❌ No |
| **NOT_SUPPORTED** | ❌ No (Suspends) | ❌ No | ❌ No |
| **NEVER** | ❌ No (Throws error) | ❌ No | ❌ No |
| **MANDATORY** | ✅ Yes | ❌ No (Throws error) | ❌ No |
| **NESTED** | ✅ Yes | ❌ No | ✅ Yes |

---

## **🚀 Final Thought**
- **Propagation helps control transaction behavior efficiently.**
- **Choose the right propagation type** based on your use case.
- **Understanding transaction management will make you a strong backend developer.** 🚀
