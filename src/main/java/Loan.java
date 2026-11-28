import java.time.LocalDate;

/**
 * Represents a loan in the library
 */
public class Loan {
    private Book book;
    private Member member;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;

    /**
     * Creates a new Loan and populates its fields
     *
     * @param book the book to be loaned
     * @param member the member borrowing the book
     * @param borrowDate the date of the loan
     * @param dueDate the day that the loan is due
     * @param returned whether the loan has been returned
     *
     * @throws IllegalArgumentException if book or member is null
     */
    public Loan(Book book, Member member, LocalDate borrowDate, LocalDate dueDate, boolean returned) {
        /*if (book == null || member == null) {
            throw new IllegalArgumentException("Loan cannot be done.");
        }*/
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    /**
     * Returns the borrowed book
     *
     * @return the borrowed book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Returns the member associated with this loan
     *
     * @return the member associated with this loan
     */
    public Member getMember() {
        return member;
    }

    /**
     * Returns the due date of the loan
     *
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Returns whether this loan has been returned.
     * @return true if the loan has been returned, false otherwise
     */
    public boolean isReturned() {
        return returned;
    }

    /**
     * Returns the date of the loan
     *
     * @return the date of the loan
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /**
     * Returns the borrow date as a string.
     *
     * @return the date of the loan in String format
     */
    public String getBorrowDateToString() {
        return borrowDate.toString();
    }

    /**
     * Returns the due date as a string.
     *
     * @return the due date of the loan in string format
     */
    public String getDueDateToString() {
        return dueDate.toString();
    }

    /**
     * Sets whether this loan has been returned
     * @param returned the new returned status
     */
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    /**
     * Returns a string representation of this loan
     * @return a string representation of this loan
     */
    @Override
    public String toString() {
        return ("Book: " + book + "\nMember: " + member
                + "\nBorrowDate: " + getBorrowDateToString() + "\nDueDate: " + getDueDateToString());
    }
}
