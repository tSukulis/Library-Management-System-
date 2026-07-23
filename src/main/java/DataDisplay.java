import java.util.List;

/**
 * Displays library data to the console
 */
public class DataDisplay {

    /**
     * Displays the books contained in the given list
     * @param books a list of books to display
     * @param message the heading displayed before the books
     */
    public static void displayBooks(List<Book> books, String message){
        System.out.println("##########"+message+"##########");
        for(Book book: books){
            System.out.println(book);
        }
    }

    /**
     * Displays the members contained in the given list
     * @param members the list of members to display
     */
    public static void displayMembers(List<Member> members){
        System.out.println();
        System.out.println("##########Members##########");
        for(Member member: members){
            System.out.println(member);
        }
    }

    /**
     * Displays the loans contained in the given list
     * @param loans a list of loans to display
     * @param message the heading displayed before the loans
     */
    public static void displayLoans(List<Loan> loans, String message){
        System.out.println();
        System.out.println("##########"+message+"##########");
        for(Loan loan: loans){
            System.out.println(loan.getBook());
            System.out.println(loan.getMember());
            System.out.println("BorrowDate: " + loan.getBorrowDateToString() + " DueDate: " + loan.getDueDateToString());
            System.out.println("Has been returned: " + loan.isReturned());
            System.out.println();

        }
    }

    /**
     * Displays library statistics
     * @param bm the book manager containing the books
     * @param mm the member manager containing the members
     * @param lm the loan manager containing the loans
     */
    public static void statistics(BookManager bm, MemberManager mm, LoanManager lm){
        System.out.println("##########Library Statistics##########");
        System.out.println("Books: " + bm.getBooks().size());
        System.out.println("Members: " + mm.getMembers().size());
        System.out.println("Loans: " + lm.getLoans().size());
        System.out.println("Active Loans: " + lm.getActiveLoans().size());
        System.out.println("Returned Loans: " + lm.getReturnedLoans().size());
        System.out.println("Overdue Loans: " + lm.getOverdueLoans().size());
        System.out.println("Available Books: " + bm.getBooksWithAvailableCopies().size());
        System.out.println("Borrowed Books: " + lm.getBorrowedBooks().size());
    }
}
