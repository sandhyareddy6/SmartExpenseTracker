package SmartExpenseTracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single expense.
 * Fields: category, amount, date
 */
public class Expense {
    private String category;
    private double amount;
    private LocalDate date;

    /**
     * Creates an expense with the given category, amount, and date.
     *
     * @param category category of expense
     * @param amount amount of expense (must be non-negative)
     * @param date date of expense
     */
    public Expense(String category, double amount, LocalDate date) {
        setCategory(category);
        setAmount(amount);
        this.date = (date == null) ? LocalDate.now() : date;
    }

    /**
     * Convenience constructor that uses today's date.
     *
     * @param category category of expense
     * @param amount amount of expense (must be non-negative)
     */
    public Expense(String category, double amount) {
        this(category, amount, LocalDate.now());
    }

    // ----------------- Getters & setters (encapsulation) -----------------

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        this.category = category.trim();
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Amount must be non-negative.
     */
    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = (date == null) ? LocalDate.now() : date;
    }

    // ----------------- Utility methods -----------------

    /**
     * Returns a neat string representation for console printing.
     */
    public String toDisplayString() {
        // Example: 01) Food      | 12.50 | 2026-07-02
        return String.format("%-15s | %10.2f | %s", category, amount, date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    /**
     * Converts this object into a line for saving to file.
     * Format: category|amount|date
     */
    public String toFileLine() {
        return category + "|" + amount + "|" + date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Creates an Expense from a file line.
     * Returns null if the line is invalid.
     */
    public static Expense fromFileLine(String line) {
        if (line == null || line.trim().isEmpty()) return null;

        String[] parts = line.split("\\|");
        if (parts.length != 3) return null;

        try {
            String category = parts[0];
            double amount = Double.parseDouble(parts[1]);
            LocalDate date = LocalDate.parse(parts[2], DateTimeFormatter.ISO_LOCAL_DATE);
            return new Expense(category, amount, date);
        } catch (Exception e) {
            return null;
        }
    }
}

