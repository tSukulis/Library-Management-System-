import java.time.LocalDate;

/**
 * Represents the raw loan data loaded from JSON
 * before it is converted into a Loan object
 */
public class LoanJson {
    private int bookId;
    private int  memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;

    /**
     * Creates a new empty LoanJson.
     */
    public LoanJson() {
    }

    /**
     * Creates a LoanJson and populates its fields
     *
     * @param bookId the book id
     * @param memberId the member id
     * @param borrowDate the date of the loan
     * @param dueDate the due date of the loan
     * @param returned whether the loan has been returned
     */
    public LoanJson(int bookId, int memberId, LocalDate borrowDate, LocalDate dueDate, boolean returned) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    /**
     * Returns the book ID.
     *
     * @return the book ID
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Returns the member id that borrowed the book.
     * @return the member id
     */
    public int getMemberId() {
        return memberId;
    }

    /**
     * Returns the borrow date.
     * @return the borrow date
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /**
     * Returns the due date of the loan.
     * @return the due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Returns whether the loan has been returned.
     * @return true if the loan has been returned, false otherwise
     */
    public boolean isReturned() {
        return returned;
    }
}
