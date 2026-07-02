package SmartExpenseTracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of Expense objects.
 * Provides CRUD operations, totals, filtering, and file persistence.
 */
public class ExpenseManager {
    private final ArrayList<Expense> expenses;
    private final String filePath;

    /**
     * Creates a manager and loads expenses from the given file (if it exists).
     *
     * @param filePath path of the file to load/save
     */
    public ExpenseManager(String filePath) {
        this.expenses = new ArrayList<>();
        this.filePath = filePath;
        loadFromFile();
    }

    public ExpenseManager() {
        this("expenses.txt");
    }

    // ----------------- Core operations -----------------

    /**
     * Adds a new expense.
     *
     * @param category category name (non-empty)
     * @param amount amount (non-negative)
     * @param date date (if null, today's date is used)
     */
    public void addExpense(String category, double amount, LocalDate date) {
        Expense e = new Expense(category, amount, date);
        expenses.add(e);
    }

    /**
     * Returns a copy of all expenses.
     */
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    /**
     * Deletes an expense by its index (0-based).
     *
     * @param index index in the internal ArrayList (0-based)
     * @return true if deleted, false otherwise
     */
    public boolean deleteExpense(int index) {
        if (index < 0 || index >= expenses.size()) {
            return false;
        }
        expenses.remove(index);
        return true;
    }

    /**
     * Calculates total expense amount.
     */
    public double calculateTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    /**
     * Calculates total expenses for the given month.
     * The month/year are taken from anyDateInMonth.
     */
    public double calculateMonthlyTotal(LocalDate anyDateInMonth) {
        if (anyDateInMonth == null) {
            anyDateInMonth = LocalDate.now();
        }
        int targetYear = anyDateInMonth.getYear();
        int targetMonth = anyDateInMonth.getMonthValue();

        double total = 0;
        for (Expense e : expenses) {
            LocalDate d = e.getDate();
            if (d != null && d.getYear() == targetYear && d.getMonthValue() == targetMonth) {
                total += e.getAmount();
            }
        }
        return total;
    }

    /**
     * Filters expenses by category (case-insensitive).
     *
     * @param category category to match
     * @return list of matching expenses
     */
    public List<Expense> filterByCategory(String category) {
        ArrayList<Expense> result = new ArrayList<>();
        if (category == null) return result;

        String target = category.trim().toLowerCase();
        if (target.isEmpty()) return result;

        for (Expense e : expenses) {
            if (e.getCategory().toLowerCase().equals(target)) {
                result.add(e);
            }
        }
        return result;
    }

    // ----------------- File handling -----------------


    /**
     * Loads expenses from the file into the ArrayList.
     * Each line format: category|amount|date
     */
    private void loadFromFile() {
        File f = new File(filePath);
        if (!f.exists()) {
            // No file yet; start with empty list.
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Expense e = Expense.fromFileLine(line);
                if (e != null) {
                    expenses.add(e);
                }
            }
        } catch (Exception ex) {
            // If file is corrupted, we just ignore and start fresh.
            // (Beginner-friendly behavior.)
        }
    }

    /**
     * Saves current expenses to the file.
     */
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Expense e : expenses) {
                bw.write(e.toFileLine());
                bw.newLine();
            }
            bw.flush();
        } catch (Exception ex) {
            // In a beginner console app, we keep it simple.
            // If saving fails, the program will still continue.
        }
    }

    /**
     * Exposes current list size.
     */
    public int size() {
        return expenses.size();
    }
}

