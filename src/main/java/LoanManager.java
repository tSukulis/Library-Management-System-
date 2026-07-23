import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all loans in the library
 * Provides methods for adding, searching, and managing loans.
 */
public class LoanManager {
    private List<Loan> loans;

    /**
     * Creates a new loan manager
     */
    public LoanManager(){
        loans = new ArrayList<>();
    }

    /**
     *  Returns all loans.
     *
     * @return a list containing all loans
     */
    public List<Loan> getLoans() {
        return loans;
    }


    /**
     * Adds a new loan to the library
     * @param loan the loan to add
     */
    public void addLoan(Loan loan){
        this.loans.add(loan);
    }


    /**
     *  Returns the number of active loans for a specific member.
     *
     * @param memberId the id of the member
     * @return the number of active loans of the member with id memberId
     */
    public int getNumberOfActiveLoansByMemberId(int memberId){
        int counter = 0;
        for(Loan loan:loans) {
            if (!loan.isReturned()) {
                if (loan.getMember().getId() == memberId)
                    counter++;
            }
        }
        return counter;
    }

    /**
     * Finds loans that have been returned by the member with id memberId.
     *
     * @param memberId the id of the member
     * @return a list with the returned loans of the specified member
     */
    public List<Loan> getReturnedLoansByMemberId(int memberId){
        List<Loan> returnedLoans = new ArrayList<>();
        for(Loan loan:loans) {
            if (loan.isReturned()) {
                if (loan.getMember().getId() == memberId)
                    returnedLoans.add(loan);
            }
        }
        return returnedLoans;
    }

    /**
     * Finds active loans for the member with id memberId
     *
     * @param memberId the id of the member
     * @return a list containing all the active loans of the specified member
     */
    public List<Loan> getActiveLoansByMemberId(int memberId){
        List<Loan> activeLoans = new ArrayList<>();
        for(Loan loan:loans) {
            if (!loan.isReturned()) {
                if (loan.getMember().getId() == memberId)
                    activeLoans.add(loan);
            }
        }
        return activeLoans;
    }

    /**
     * Finds all loans for the member with id memberId
     *
     * @param memberId the id of the member
     * @return a list containing all the loans of the specified member
     */
    public List<Loan> getLoansByMemberId(int memberId){
        List<Loan> loansByMemberId = new ArrayList<>();
        for(Loan loan: loans){
            if(loan.getMember().getId() == memberId)
                loansByMemberId.add(loan);
        }
        return loansByMemberId;
    }

    /**
     * Finds all active loans
     *
     * @return a list containing all the active loans
     */
    public List<Loan> getActiveLoans(){
        List<Loan> activeLoans = new ArrayList<>();
        for(Loan loan: loans){
            if(!loan.isReturned())
                activeLoans.add(loan);
        }
        return activeLoans;
    }

    /**
     * Finds all returned loans
     *
     * @return a list containing all the returned loans
     */
    public List<Loan> getReturnedLoans(){
        List<Loan> returnedLoans = new ArrayList<>();
        for(Loan loan: loans){
            if(loan.isReturned()){
                returnedLoans.add(loan);
            }
        }
        return returnedLoans;
    }


    /**
     * Finds all overdue loans
     *
     * @return a list containing all overdue loans
     */
    public List<Loan> getOverdueLoans(){
        List<Loan> overdueLoans = new ArrayList<>();
        for(Loan loan: loans){
            if (!loan.isReturned() && LocalDate.now().isAfter(loan.getDueDate())){
                overdueLoans.add(loan);
            }
        }
        return overdueLoans;
    }


    /**
     * Finds all borrowed books
     *
     * @return a list containing all borrowed books
     */
    public List<Book> getBorrowedBooks(){
        List<Loan> activeLoans = getActiveLoans();
        List<Book> borrowedBooks = new ArrayList<>();
        for(Loan loan: activeLoans){
            if(!borrowedBooks.contains(loan.getBook())) {
                borrowedBooks.add(loan.getBook());
            }
        }
        return borrowedBooks;
    }

    /**
     * Finds loan for a specific book by a specific member
     *
      * @param bookId the book id
     * @param memberId the member id
     * @return the matching active loan, or null if no loan is found
     */
    public Loan getActiveLoanByBookIdAndMemberId(int bookId, int memberId){
        for(Loan loan: loans){
            if(loan.getBook().getId() == bookId && loan.getMember().getId() == memberId
                && !loan.isReturned()){
                return loan;
            }
        }
        return null;
    }

    /**
     * Borrows a book for a member.
     *
     * @param bookId the id of the book
     * @param memberId the id of the member
     * @param bm the book manager
     * @param mm the member manager
     *
     * @throws IllegalArgumentException if the book or member does not exist
     * @throws IllegalStateException if the book has no available copies
     * @throws IllegalStateException if the member already has five active loans
     */
    public void borrowBook(int bookId, int memberId, BookManager bm, MemberManager mm){
        Book book = bm.findBookById(bookId);
        Member member = mm.findMemberById(memberId);
        if (book == null)
            throw new IllegalArgumentException("Book with id: " + bookId + " could not be found");

        if (member == null)
            throw new IllegalArgumentException("Member with Id: " + memberId + " could not be found");

        if (book.getAvailableCopies() <= 0)
            throw new IllegalStateException("No available copies for books with Id: " + bookId);

        if (getNumberOfActiveLoansByMemberId(memberId) >= 5)
            throw new IllegalStateException("Member has already 5 loans.");

        LocalDate dueDate = LocalDate.now().plusDays(14);
        Loan loan = new Loan(book, member, LocalDate.now(), dueDate, false);
        loans.add(loan);
        book.setAvailableCopies(book.getAvailableCopies() - 1);

    }

    /**
     * Marks a borrowed book as returned.
     *
     * @param bookId the book id
     * @param memberId the member id
     * @throws IllegalArgumentException if the loan could not be found
     */
    public void returnBook(int bookId, int memberId){
        Loan loan = getActiveLoanByBookIdAndMemberId(bookId, memberId);
        if(loan == null)
            throw new IllegalArgumentException("Loan could not be found");

        loan.getBook().setAvailableCopies(loan.getBook().getAvailableCopies() + 1);
        loan.setReturned(true);
    }


}
