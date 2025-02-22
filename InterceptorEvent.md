

## **📌 1. `onSave()` – Validating Data Before Saving**  
🛠 **Scenario:**  
Imagine an **employee management system** where a new employee is being added to the database. However, we want to **validate** that the salary cannot be less than ₹10,000 before saving the entity.

### **🔄 Workflow:**
1️⃣ A user tries to add a new employee with a salary of ₹5,000.  
2️⃣ The **`onSave()` interceptor** is triggered **before Hibernate inserts the record**.  
3️⃣ The interceptor checks if the salary is valid.  
4️⃣ If the salary is **less than ₹10,000**, the interceptor prevents the save operation.  
5️⃣ If valid, Hibernate proceeds with inserting the record into the database.  

### **✅ Code Example:**
```java
@Override
public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
    if (entity instanceof Employee) {
        Employee emp = (Employee) entity;
        if (emp.getSalary() < 10000) {
            throw new RuntimeException("❌ Salary cannot be less than ₹10,000!");
        }
    }
    return false; // No modification, just validation
}
```
⏩ **Result:** If someone tries to save an employee with a salary below ₹10,000, Hibernate **throws an error**, preventing the invalid data from being stored.

---

## **📌 2. `onFlushDirty()` – Auditing Changes to Track Who Modified Data**  
🛠 **Scenario:**  
In a **banking application**, every time a user updates their **account balance**, we need to track **who made the change and when**.  

### **🔄 Workflow:**
1️⃣ A user updates their account balance.  
2️⃣ The **`onFlushDirty()` interceptor** is triggered before Hibernate updates the record.  
3️⃣ The interceptor logs the **old balance, new balance, timestamp, and user ID**.  
4️⃣ The change is saved into a separate **audit log table** for tracking.  

### **✅ Code Example:**
```java
@Override
public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
    if (entity instanceof BankAccount) {
        double oldBalance = (double) previousState[1]; // Assuming balance is at index 1
        double newBalance = (double) currentState[1];

        System.out.println("🔍 Audit Log: Account ID: " + id + " | Old Balance: ₹" + oldBalance + " | New Balance: ₹" + newBalance);
    }
    return false; 
}
```
⏩ **Result:** Every time a user's account balance is updated, the change is logged for **auditing**.

---

## **📌 3. `onDelete()` – Logging or Preventing Unauthorized Deletions**  
🛠 **Scenario:**  
In an **e-commerce application**, we want to prevent users from deleting **orders that are already shipped**.

### **🔄 Workflow:**
1️⃣ A user tries to delete an **Order** entity.  
2️⃣ The **`onDelete()` interceptor** is triggered before Hibernate executes the delete query.  
3️⃣ The interceptor checks the **order status**.  
4️⃣ If the order is **already shipped**, deletion is blocked.  
5️⃣ If the order is still pending, it is deleted.  

### **✅ Code Example:**
```java
@Override
public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
    if (entity instanceof Order) {
        Order order = (Order) entity;
        if (order.getStatus().equals("Shipped")) {
            throw new RuntimeException("🚨 Cannot delete an order that has already been shipped!");
        }
    }
}
```
⏩ **Result:** If someone tries to delete a shipped order, Hibernate **throws an exception**, preventing data loss.

---

## **📌 4. `onLoad()` – Implementing Role-Based Security on Data Access**  
🛠 **Scenario:**  
In an **HR Management System**, only **HR managers** should be able to view employees’ **salary details**. If a regular employee tries to access the salary field, it should be hidden.

### **🔄 Workflow:**
1️⃣ An employee logs in and tries to fetch employee details.  
2️⃣ The **`onLoad()` interceptor** is triggered when the entity is loaded from the database.  
3️⃣ The interceptor checks if the user **has HR manager privileges**.  
4️⃣ If the user is **not an HR manager**, the salary field is hidden (set to `null`).  
5️⃣ If the user **is an HR manager**, the salary is displayed.  

### **✅ Code Example:**
```java
@Override
public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
    if (entity instanceof Employee) {
        String currentUserRole = getCurrentUserRole(); // Assume this gets the role from the session
        if (!currentUserRole.equals("HR_MANAGER")) {
            for (int i = 0; i < propertyNames.length; i++) {
                if ("salary".equals(propertyNames[i])) {
                    state[i] = null; // Hide salary details for unauthorized users
                }
            }
        }
    }
    return true;
}
```
⏩ **Result:** If an employee tries to view salary details **without HR permissions**, the salary will appear as **null**.

---

## **📌 5. `preFlush()` – Ensuring Business Rules Are Followed Before Commit**  
🛠 **Scenario:**  
In a **loan application system**, a rule states that a loan **cannot exceed 80% of the applicant’s annual salary**. We want to enforce this **before any data is saved**.

### **🔄 Workflow:**
1️⃣ A user applies for a loan.  
2️⃣ The **`preFlush()` interceptor** is triggered before Hibernate commits the transaction.  
3️⃣ The interceptor calculates if the loan amount is **greater than 80% of the applicant’s salary**.  
4️⃣ If the rule is violated, an error is thrown, and the transaction is **rolled back**.  
5️⃣ If valid, the loan application is saved successfully.  

### **✅ Code Example:**
```java
@Override
public void preFlush(Iterator entities) {
    while (entities.hasNext()) {
        Object entity = entities.next();
        if (entity instanceof LoanApplication) {
            LoanApplication loan = (LoanApplication) entity;
            double maxLoanAmount = loan.getApplicant().getAnnualSalary() * 0.8;
            
            if (loan.getLoanAmount() > maxLoanAmount) {
                throw new RuntimeException("❌ Loan amount exceeds the allowed limit!");
            }
        }
    }
}
```
⏩ **Result:** If an applicant applies for a loan that **exceeds 80% of their salary**, Hibernate **blocks the transaction**.

---

## **🚀 Summary: Hibernate Interceptor Event Scenarios**
| **Event**        | **Scenario** | **Action Performed** |
|-----------------|-------------|----------------------|
| `onSave()`      | Prevent saving invalid salary | Throws an error if salary < ₹10,000 |
| `onFlushDirty()` | Audit balance updates | Logs old vs. new balance |
| `onDelete()`    | Prevent deleting shipped orders | Blocks deletion if order is shipped |
| `onLoad()`      | Hide salary from unauthorized users | Sets salary to `null` for non-HR users |
| `preFlush()`    | Enforce loan approval rules | Blocks loan > 80% of salary |

