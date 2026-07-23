import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoanManagerTest {
    private BookManager bm;
    private MemberManager mm;
    private LoanManager lm;
    private Book firstBook;
    private Book secondBook;
    private Member firstMember;
    private Member secondMember;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    @BeforeEach
    void setUp(){
        bm = new BookManager();
        mm = new MemberManager();
        lm = new LoanManager();
        firstBook = new Book(1, "Clean code", 2015, 10, 8);
        secondBook = new Book(2, "different book ", 2018, 11, 10);
        firstMember = new Member(100, "Nick", "@mail");
        secondMember = new Member(101, "Tom", "@mail");
        borrowDate = LocalDate.of(2026, 2, 1);
        dueDate = LocalDate.of(2026, 2, 15);
    }
    @Test
    void getNumberOfActiveLoansByMemberId_memberHasOnlyReturnedLoans_returnsZero(){
        Loan firstLoan = new Loan(firstBook, firstMember, borrowDate, dueDate, true);
        Loan secondLoan = new Loan(secondBook, secondMember, borrowDate, dueDate, false);
        Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, false);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        lm.addLoan(thirdLoan);

        assertEquals(0, lm.getNumberOfActiveLoansByMemberId(100));
    }

    @Test
    void getNumberOfActiveLoansByMemberId_validId_returnsNumberOfActiveLoans(){
        Loan firstLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, true);
        Loan secondLoan = new Loan(secondBook, secondMember, borrowDate, dueDate, false);
        Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, false);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        lm.addLoan(thirdLoan);

        assertEquals(2, lm.getNumberOfActiveLoansByMemberId(101));
    }

    @Test
    void getReturnedLoansByMemberId_noReturnedLoans_returnsEmptyList(){
        Loan firstLoan = new Loan(firstBook, firstMember, borrowDate, dueDate, true);
        Loan secondLoan = new Loan(secondBook, secondMember, borrowDate, dueDate, false);
        Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, false);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        lm.addLoan(thirdLoan);
        List<Loan> returnedLoans = lm.getReturnedLoansByMemberId(101);

        assertTrue(returnedLoans.isEmpty());
    }

    @Test
    void getReturnedLoansByMemberId_multipleReturnedLoans_returnsListWithLoans(){
        Loan firstLoan = new Loan(firstBook, firstMember, borrowDate, dueDate, true);
        Loan secondLoan = new Loan(secondBook, secondMember, borrowDate, dueDate, true);
        Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, true);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        lm.addLoan(thirdLoan);
        List<Loan> returnedLoans = lm.getReturnedLoansByMemberId(101);

        assertEquals(2, returnedLoans.size());
        assertTrue(returnedLoans.contains(secondLoan));
        assertTrue(returnedLoans.contains(thirdLoan));
        assertFalse(returnedLoans.contains(firstLoan));
    }

    @Test
    void getActiveLoansByMemberId_noActiveLoans_returnsEmptyList(){
        Loan firstLoan = new Loan(firstBook, firstMember, borrowDate, dueDate, true);
        Loan secondLoan = new Loan(secondBook, secondMember,borrowDate, dueDate, true);
        Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, true);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        lm.addLoan(thirdLoan);

        List<Loan> listOne = lm.getActiveLoansByMemberId(100);

        assertTrue(listOne.isEmpty());

        List<Loan> listTwo = lm.getActiveLoansByMemberId(101);
        assertTrue(listTwo.isEmpty());
    }

    @Test
    void getActiveLoansByMemberId_activeLoans_returnsListOfActiveLoans(){
        Loan firstLoan = new Loan(firstBook, firstMember, borrowDate, dueDate, true);
        Loan secondLoan = new Loan(secondBook, secondMember, borrowDate, dueDate, false);
        Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, false);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        lm.addLoan(thirdLoan);

        List<Loan> list = lm.getActiveLoansByMemberId(101);

        assertEquals(2, list.size());
        assertTrue(list.contains(secondLoan));
        assertTrue(list.contains(thirdLoan));
        assertFalse(list.contains(firstLoan));
    }
     @Test
    void getLoansByMemberId_noLoans_returnsEmptyList(){
         Loan firstLoan = new Loan(firstBook,  secondMember, borrowDate, dueDate, true);
         Loan secondLoan = new Loan(secondBook, secondMember, borrowDate, dueDate, false);
         Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, false);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Loan> list = lm.getLoansByMemberId(100);
         assertTrue(list.isEmpty());
     }

     @Test
    void getLoansByMemberId_hasLoans_returnsListWithLoans(){
         Loan firstLoan = new Loan(firstBook,  secondMember, borrowDate, dueDate, true);
         Loan secondLoan = new Loan(secondBook, firstMember, borrowDate, dueDate, false);
         Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, false);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Loan> loans = lm.getLoansByMemberId(101);

         assertEquals(2, loans.size());
         assertTrue(loans.contains(firstLoan));
         assertTrue(loans.contains(thirdLoan));
         assertFalse(loans.contains(secondLoan));
     }

     @Test
    void getActiveLoans_noActiveLoans_returnsEmptyList(){
         Loan firstLoan = new Loan(firstBook,  secondMember, borrowDate, dueDate, true);
         Loan secondLoan = new Loan(secondBook, firstMember, borrowDate, dueDate, true);
         Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, true);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Loan> activeLoans = lm.getActiveLoans();

         assertTrue(activeLoans.isEmpty());
     }

     @Test
    void getActiveLoans_hasActiveLoans_returnsListWithActiveLoans(){
         Loan firstLoan = new Loan(firstBook,  secondMember, borrowDate, dueDate, false);
         Loan secondLoan = new Loan(secondBook, firstMember, borrowDate, dueDate, true);
         Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, false);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Loan> activeLoans = lm.getActiveLoans();

         assertEquals(2, activeLoans.size());
         assertTrue(activeLoans.contains(firstLoan));
         assertTrue(activeLoans.contains(thirdLoan));
         assertFalse(activeLoans.contains(secondLoan));
     }

     @Test
    void getReturnedLoans_noReturnedLoans_returnsEmptyList(){
         Loan firstLoan = new Loan(firstBook,  secondMember, borrowDate, dueDate, false);
         Loan secondLoan = new Loan(secondBook, firstMember, borrowDate, dueDate, false);
         Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, false);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Loan> returnedLoans = lm.getReturnedLoans();

         assertTrue(returnedLoans.isEmpty());
     }

     @Test
    void getReturnedLoans_hasLoans_returnsListWithReturnedLoans(){
         Loan firstLoan = new Loan(firstBook,  secondMember, borrowDate, dueDate, true);
         Loan secondLoan = new Loan(secondBook, firstMember, borrowDate, dueDate, false);
         Loan thirdLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, true);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Loan> returnedLoans = lm.getReturnedLoans();

         assertEquals(2, returnedLoans.size());
         assertTrue(returnedLoans.contains(firstLoan));
         assertTrue(returnedLoans.contains(thirdLoan));
         assertFalse(returnedLoans.contains(secondLoan));
     }

     @Test
    void getOverdueLoans_noOverdueLoans_returnsEmptyList(){
         Loan firstLoan = new Loan(firstBook,  secondMember, LocalDate.of(2026,2,16), LocalDate.of(2026,3,2), true);
         Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.now() ,LocalDate.now().plusDays(14), false);
         Loan thirdLoan = new Loan(firstBook, secondMember, LocalDate.of(2026, 1,10) ,LocalDate.of(2026, 1, 24), true);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Loan> overdueLoans = lm.getOverdueLoans();

         assertTrue(overdueLoans.isEmpty());
     }

     @Test
    void getOverdueLoans_hasOverdueLoans_returnsListWithOverdueLoans(){
         Loan firstLoan = new Loan(firstBook,  secondMember, LocalDate.now().minusDays(20), LocalDate.now().minusDays(6), false);
         Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.of(2026, 7, 1) ,LocalDate.of(2026, 7, 15), true);
         Loan thirdLoan = new Loan(firstBook, secondMember, LocalDate.now().minusDays(40), LocalDate.now().minusDays(26), false);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Loan> overdueLoans = lm.getOverdueLoans();

         assertEquals(2, overdueLoans.size());
         assertTrue(overdueLoans.contains(firstLoan));
         assertTrue(overdueLoans.contains(thirdLoan));
         assertFalse(overdueLoans.contains(secondLoan));
     }


     @Test
    void getBorrowedBooks_noBorrowedBooks_returnsEmptyList(){
         Loan firstLoan = new Loan(firstBook,  secondMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), true);
         Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.now().minusDays(15),LocalDate.now().minusDays(1), true);
         Loan thirdLoan = new Loan(firstBook, secondMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), true);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Book> borrowedBooks = lm.getBorrowedBooks();

         assertTrue(borrowedBooks.isEmpty());
     }

    @Test
    void getBorrowedBooks_multipleActiveLoansForSameBook_returnsBookOnce() {
        Loan firstLoan = new Loan(firstBook, firstMember, borrowDate, dueDate, false);

        Loan secondLoan = new Loan(firstBook, secondMember, borrowDate, dueDate, false);

        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);

        List<Book> borrowedBooks = lm.getBorrowedBooks();

        assertEquals(1, borrowedBooks.size());
        assertTrue(borrowedBooks.contains(firstBook));
    }


     @Test
    void getBorrowedBooks_hasBorrowedBooks_returnsListWithBorrowedBooks(){
         Book thirdBook = new Book(3, "Efficient Java", 2020, 7, 3);
         Loan firstLoan = new Loan(firstBook,  secondMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
         Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.now().minusDays(15),LocalDate.now().minusDays(1), true);
         Loan thirdLoan = new Loan(thirdBook, secondMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);
         lm.addLoan(thirdLoan);

         List<Book> borrowedBooks = lm.getBorrowedBooks();

         assertEquals(2, borrowedBooks.size());
         assertTrue(borrowedBooks.contains(firstBook));
         assertTrue(borrowedBooks.contains(thirdBook));
         assertFalse(borrowedBooks.contains(secondBook));
     }

     @Test
    void getActiveLoanByBookIdAndMemberId_noActiveLoanMatch_returnsNull(){
         Loan firstLoan = new Loan(firstBook,  secondMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
         Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.now().minusDays(15),LocalDate.now().minusDays(1), true);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);

         Loan loan = lm.getActiveLoanByBookIdAndMemberId(1,100);

         assertNull(loan);
     }



     @Test
     void getActiveLoanByBookIdAndMemberId_hasMatchedLoan_returnsLoan(){
         Loan firstLoan = new Loan(firstBook,  secondMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
         Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.now().minusDays(15),LocalDate.now().minusDays(1), true);
         lm.addLoan(firstLoan);
         lm.addLoan(secondLoan);

         Loan loan = lm.getActiveLoanByBookIdAndMemberId(1,101);

         assertEquals(firstLoan, loan);
         assertEquals(1, loan.getBook().getId());
         assertEquals(101, loan.getMember().getId());
     }

    @Test
    void getActiveLoanByBookIdAndMemberId_matchingReturnedLoan_returnsNull() {
        Loan returnedLoan = new Loan(
                firstBook,
                firstMember,
                LocalDate.now().minusDays(20),
                LocalDate.now().minusDays(6),
                true
        );

        lm.addLoan(returnedLoan);

        assertNull(lm.getActiveLoanByBookIdAndMemberId(1, 100));
    }

    @Test
    void borrowBook_notMatchingBookId_throwsIllegalArgumentException(){
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        mm.addMember(firstMember);
        mm.addMember(secondMember);


        int loansBefore = lm.getLoans().size();
        int firstBookAvailableCopies = firstBook.getAvailableCopies();
        int secondBookAvailableCopies = secondBook.getAvailableCopies();

        assertThrows(IllegalArgumentException.class, () -> lm.borrowBook(10, 100, bm, mm));
        assertEquals(loansBefore, lm.getLoans().size());
        assertEquals(firstBookAvailableCopies, firstBook.getAvailableCopies());
        assertEquals(secondBookAvailableCopies, secondBook.getAvailableCopies());
    }

    @Test
    void borrowBook_noMatchingMember_throwsIllegalArgumentException(){
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        mm.addMember(firstMember);
        mm.addMember(secondMember);

        int loansBefore = lm.getLoans().size();
        int firstBookAvailableCopies = firstBook.getAvailableCopies();
        int secondBookAvailableCopies = secondBook.getAvailableCopies();


        assertThrows(IllegalArgumentException.class, () ->  lm.borrowBook(1, 102, bm, mm));
        assertEquals(loansBefore, lm.getLoans().size());
        assertEquals(firstBookAvailableCopies, firstBook.getAvailableCopies());
        assertEquals(secondBookAvailableCopies, secondBook.getAvailableCopies());
    }

    @Test
    void borrowBook_noAvailableCopies_throwsIllegalStateException(){
        Book thirdBook = new Book(3, "Embedded  Systems", 2020, 5, 0);
        bm.addBook(thirdBook);
        mm.addMember(firstMember);

        int loansBefore = lm.getLoans().size();
        int thirdBookAvailableCopies = thirdBook.getAvailableCopies();

        assertThrows(IllegalStateException.class, () -> lm.borrowBook(3, 100, bm, mm));
        assertEquals(loansBefore, lm.getLoans().size());
        assertEquals(thirdBookAvailableCopies, thirdBook.getAvailableCopies());
    }

    @Test
    void borrowBook_memberHasFiveActiveLoans_throwsIllegalStateException(){
        Book thirdBook = new Book(3, "Embedded  Systems", 2020, 5, 2);
        Book fourthBook = new Book(4, "C++ Tour", 2022, 5, 3);
        Book fifthBook = new Book(5, "Linux Guide", 2023, 6, 3);
        Book sixthBook = new Book(6, "DevOps Guide", 2025, 3, 2);
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        bm.addBook(thirdBook);
        bm.addBook(fourthBook);
        bm.addBook(fifthBook);
        bm.addBook(sixthBook);
        mm.addMember(firstMember);
        mm.addMember(secondMember);

        Loan firstLoan = new Loan(firstBook,  firstMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
        Loan secondLoan = new Loan(secondBook,  firstMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
        Loan thirdLoan = new Loan(thirdBook,  firstMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
        Loan fourthLoan = new Loan(fourthBook,  firstMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
        Loan fifthLoan = new Loan(fifthBook,  firstMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        lm.addLoan(thirdLoan);
        lm.addLoan(fourthLoan);
        lm.addLoan(fifthLoan);

        int numOfLoansBefore = lm.getLoans().size();
        int sixthBookAvailableCopies = sixthBook.getAvailableCopies();

        assertThrows(IllegalStateException.class, () -> lm.borrowBook(6, 100, bm, mm));
        assertEquals(sixthBookAvailableCopies, sixthBook.getAvailableCopies());
        assertEquals(numOfLoansBefore, lm.getLoans().size());
    }

    @Test
    void borrowBook_validLoan_addsNewLoanAndReducesAvailableBookCopies(){
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        mm.addMember(firstMember);
        mm.addMember(secondMember);

        int numOfLoansBefore = lm.getLoans().size();
        int firstBookAvailableCopiesBefore = firstBook.getAvailableCopies();

       lm.borrowBook(1, 100, bm, mm);


       assertEquals(numOfLoansBefore + 1, lm.getLoans().size());
       assertEquals(firstBookAvailableCopiesBefore - 1, firstBook.getAvailableCopies());
       assertEquals(firstBook, lm.getLoans().getFirst().getBook());
       assertEquals(firstMember, lm.getLoans().getFirst().getMember());
    }

    @Test
    void returnBook_noActiveLoanWithMatchingBookId_throwsIllegalArgumentException(){
        Loan firstLoan = new Loan(firstBook,  firstMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
        Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.now().minusDays(15),LocalDate.now().minusDays(1), true);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);

        int numOfActiveLoansBefore = lm.getActiveLoans().size();

        assertThrows(IllegalArgumentException.class, () -> lm.returnBook(3, 100));
        assertEquals(numOfActiveLoansBefore, lm.getActiveLoans().size());
    }


    @Test
    void returnBook_noActiveLoanWithMatchingMemberId_throwsIllegalArgumentException(){
        Loan firstLoan = new Loan(firstBook,  firstMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
        Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.now().minusDays(15),LocalDate.now().minusDays(1), false);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);

        int numOfActiveLoansBefore = lm.getActiveLoans().size();
        int availableCopiesBefore = secondBook.getAvailableCopies();


        assertThrows(IllegalArgumentException.class, () -> lm.returnBook(2, 105));
        assertEquals(numOfActiveLoansBefore, lm.getActiveLoans().size());
        assertEquals(availableCopiesBefore, secondBook.getAvailableCopies());
    }

    @Test
    void returnBook_validReturn_ActiveLoansReducedByOne(){
        Loan firstLoan = new Loan(firstBook,  firstMember, LocalDate.now().minusDays(10), LocalDate.now().plusDays(4), false);
        Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.now().minusDays(15),LocalDate.now().minusDays(1), true);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);

        int numOfActiveLoansBefore = lm.getActiveLoans().size();
        int availableCopiesBefore = firstBook.getAvailableCopies();

        lm.returnBook(1, 100);

        assertTrue(firstLoan.isReturned());
        assertEquals(numOfActiveLoansBefore - 1, lm.getActiveLoans().size());
        assertEquals(availableCopiesBefore + 1, firstBook.getAvailableCopies());
    }
}

