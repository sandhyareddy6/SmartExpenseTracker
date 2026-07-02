package SmartExpenseTracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based menu-driven application.
 */
public class Main {
    private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager("expenses.txt");
        Scanner sc = new Scanner(System.in);

        System.out.println("==============================");
        System.out.println(" Smart Expense Tracker (Core Java)");
        System.out.println("==============================");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt(sc, "Enter your choice: ");

            switch (choice) {
                case 1:
                    addExpenseFlow(sc, manager);
                    break;
                case 2:
                    viewAllFlow(manager);
                    break;
                case 3:
                    showTotalFlow(manager);
                    break;
                case 4:
                    filterFlow(sc, manager);
                    break;
                case 5:
                    showMonthlyTotalFlow(manager);
                    break;
                case 6:
                    deleteFlow(sc, manager);
                    break;
                case 7:
                    manager.saveToFile();
                    System.out.println("Saved to expenses.txt. Bye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 1-7.");
            }
        }

        sc.close();
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("1. Add a new expense");
        System.out.println("2. View all expenses");
        System.out.println("3. Calculate total expenses");
        System.out.println("4. Filter expenses by category");
        System.out.println("5. Monthly total (current month)");
        System.out.println("6. Delete an expense");
        System.out.println("7. Exit");
    }

    private static void addExpenseFlow(Scanner sc, ExpenseManager manager) {
        System.out.println("\n--- Add Expense ---");

        String category = readNonEmptyString(sc, "Enter category (e.g., Food): ");
        double amount = readNonNegativeDouble(sc, "Enter amount (no negative): ");

        // Optional: date input. If user enters blank, use today's date.
        System.out.print("Enter date (YYYY-MM-DD) or press Enter for today: ");
        String dateInput = sc.nextLine().trim();

        LocalDate date;
        if (dateInput.isEmpty()) {
            date = LocalDate.now();
        } else {
            try {
                date = LocalDate.parse(dateInput, INPUT_DATE_FORMAT);
            } catch (Exception e) {
                System.out.println("Invalid date format. Using today's date instead.");
                date = LocalDate.now();
            }
        }

        try {
            manager.addExpense(category, amount, date);
            manager.saveToFile();
            System.out.println("Expense added successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void viewAllFlow(ExpenseManager manager) {
        System.out.println("\n--- All Expenses ---");

        List<Expense> expenses = manager.getAllExpenses();
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        System.out.println("Idx | Category         |    Amount | Date");
        System.out.println("----+------------------+------------+------------");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.printf("%3d | %s\n", i, expenses.get(i).toDisplayString());
        }
    }

    private static void showTotalFlow(ExpenseManager manager) {
        System.out.println("\n--- Total Expenses ---");
        double total = manager.calculateTotal();
        System.out.printf("Total = %.2f\n", total);
    }

    private static void showMonthlyTotalFlow(ExpenseManager manager) {
        System.out.println("\n--- Monthly Total (Current Month) ---");
        double total = manager.calculateMonthlyTotal(LocalDate.now());
        System.out.printf("Monthly Total = %.2f\n", total);
    }

    private static void filterFlow(Scanner sc, ExpenseManager manager) {
        System.out.println("\n--- Filter by Category ---");
        String category = readNonEmptyString(sc, "Enter category to filter: ");

        List<Expense> filtered = manager.filterByCategory(category);
        if (filtered.isEmpty()) {
            System.out.println("No expenses found for category: " + category);
            return;
        }

        System.out.println("Category matches: " + category);
        System.out.println("Idx | Category         |    Amount | Date");
        System.out.println("----+------------------+------------+------------");

        for (int i = 0; i < filtered.size(); i++) {
            System.out.printf("%3d | %s\n", i, filtered.get(i).toDisplayString());
        }
    }

    private static void deleteFlow(Scanner sc, ExpenseManager manager) {
        System.out.println("\n--- Delete Expense ---");

        if (manager.size() == 0) {
            System.out.println("No expenses to delete.");
            return;
        }

        viewAllFlow(manager);

        int index = readInt(sc, "Enter the index (Idx) of the expense to delete: ");

        boolean deleted = manager.deleteExpense(index);
        if (deleted) {
            manager.saveToFile();
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Invalid index. No expense deleted.");
        }
    }

    // ----------------- Input helpers with validation -----------------

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double readNonNegativeDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try {
                double val = Double.parseDouble(line.trim());
                if (val < 0) {
                    System.out.println("Amount cannot be negative.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private static String readNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (s != null && !s.trim().isEmpty()) {
                return s.trim();
            }
            System.out.println("Input cannot be empty.");
        }
    }
}

