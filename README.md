# Smart Expense Tracker

A console-based Java application to help you manage your daily expenses. It uses OOP concepts, stores data in a text file (`expenses.txt`), and provides totals and monthly summaries using `LocalDate`.

---

## Project Details

- **Language:** Java (Core Java)
- **Type:** Console-based application
- **Data storage:** Text file (`expenses.txt`)
- **Concepts used:** OOP, `ArrayList`, File Handling, `LocalDate`

---

## Project Structure (IMPORTANT – match exactly)

SmartExpenseTracker/

- `Expense.java`
- `ExpenseManager.java`
- `Main.java`
- `expenses.txt`

> Note: `.class` files are generated automatically after compilation and should NOT be included in explanation.

---

## Features

- **Add expense** (category, amount, date)
- **View all expenses**
- **Search expenses by category**
- **Delete expense**
- **Calculate total expense**
- **Calculate monthly expense**
- **Save expenses to file**
- **Load expenses from file at startup**

---

## Description

This project helps users track expenses using a simple, menu-driven console interface. All expenses are saved persistently in a text file (`expenses.txt`), so previously added expenses remain available when you restart the program.

---

## Technologies Used

- Java
- OOP concepts (classes, objects)
- `ArrayList`
- File Handling (`BufferedReader`, `FileWriter`)
- `LocalDate` API

---

## How to Run (IMPORTANT – correct commands)

1. Open a terminal in the project folder:
   - `SmartExpenseTracker/`
2. Compile:
   ```bash
   javac *.java
   ```
3. Run:
   ```bash
   java Main
   ```

---

## Sample Output

Example menu:

1. Add Expense
2. View Expenses
3. Search by Category
4. Delete Expense
5. Monthly Total
6. Total Expense
7. Exit

---

## Important Notes

- The `expenses.txt` file is used to store data.
- Data is automatically loaded when the program starts.
- Ensure `expenses.txt` is in the same directory as the program (or it will be created when you save data).

---

## Future Enhancements

- GUI using Java Swing
- Database integration (MySQL)
- Charts and reports

---

## Author

(Add your name here)

