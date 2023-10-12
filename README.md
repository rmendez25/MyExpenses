# MyExpenses
Simple Spring Boot API to track expenses

**Preconditions for usage:**
- Have Java 17 installed
- Have MySql workbench or other toll to handle the database

Steps to make it work
1- Clone the Repository to the desired folder on your local
git clone https://github.com/rmendez25/MyExpenses.git

2- Open it using Intellij idea or your favorite IDE

3- Let maven install the dependencies or user the maven wrapper command: ./mvnw clean install

4- Run the MyExpensesApplication.class located in this path: src/main/java/com/expenses/MyExpensesApplication.java
The application will start on port 8080 by default

**Note**: Category field is based on a Enum class, anything different from that will fail, update the class as needed.
The class can be found in this path: src/main/java/com/expenses/enums/ExpenseCategory.java

Here are the operations that can be perform:
- **GET** all expenses: /api/expenses
- **GET** single expense by ID: /api/expenses/{id}
- **POST** expense: /api/expenses
- **UPDATE** expense: /api/expenses/{id}
- **DELETE** expense: /api/expenses/{id}
- **GET** expense by category: /api/expenses/byCategory?category=EDUCATION
- **GET** Expenses Total amount: /api/expenses/totalExpensesAmount
- **GET** Expenses by Date Ranges: /api/expenses/betweenDates?startDate=2023-10-01&endDate=2023-10-01
- **GET** expense total amount by category: /api/expenses/totalAmountByCategory?category=TRANSPORTATION
- **GET** expenses between amount ranges: /api/expenses/expensesBetweenRanges?start=5000&end=15000
