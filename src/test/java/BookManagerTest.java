import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookManagerTest {
    private BookManager bm;
    private LoanManager lm;

    @BeforeEach
    void setUp(){
        bm = new BookManager();
        lm = new LoanManager();
    }


    @Test
    void addBook_nullArgument_throwsIllegalArgumentException(){
        Book firstBook = null;
        assertThrows(IllegalArgumentException.class, () -> {bm.addBook(firstBook);});
    }

    @Test
    void addBook_duplicatedId_throwsIllegalArgumentException(){
        Book firstBook = new Book(10, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(10, "Efficient Code", 2018, 11, 10);

        bm.addBook(firstBook);
        assertThrows(IllegalArgumentException.class, () -> {bm.addBook(secondBook);});
    }

    @Test
    void addBook_validBook_addBookToList(){
        Book book = new Book(10, "Clean code", 2015, 10, 8);
        bm.addBook(book);
        assertTrue(bm.getBooks().contains(book));
        assertEquals(1, bm.getBooks().size());
    }

    @Test
    void deleteBookById_invalidId_throwsIllegalArgumentException(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "different book ", 2018, 11, 10);
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        assertThrows(IllegalArgumentException.class, () -> {bm.deleteBookById(10, lm);});
        assertEquals(2, bm.getBooks().size());
    }

    @Test
    void deleteBookById_loanNotReturned_throwsIllegalStateException(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "different book ", 2018, 11, 10);
        bm.addBook(firstBook);
        bm.addBook(secondBook);

        Member firstMember = new Member(100, "Nick", "@mail");

        Loan loan = new Loan(firstBook, firstMember, LocalDate.now(), LocalDate.now().plusDays(14), false);
        lm.addLoan(loan);

        assertThrows(IllegalStateException.class, () -> {bm.deleteBookById(1, lm) ;});
        assertEquals(2, bm.getBooks().size());
        assertEquals(1, lm.getLoans().size());

    }
    @Test
    void deleteBookById_returnedLoans_removesBooksAndRelatedLoans(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "different book ", 2018, 11, 10);
        bm.addBook(firstBook);
        bm.addBook(secondBook);

        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");

        Loan firstLoan = new Loan(firstBook, firstMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        Loan secondLoan = new Loan(secondBook, secondMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);

        bm.deleteBookById(1, lm);

        assertFalse(bm.getBooks().contains(firstBook));
        assertFalse(lm.getLoans().contains(firstLoan));
        assertTrue(bm.getBooks().contains(secondBook));
        assertTrue(lm.getLoans().contains(secondLoan));
        assertEquals(1, bm.getBooks().size());
        assertEquals(1, lm.getLoans().size());
    }

    @Test
    void deleteBookById_multipleReturnedLoans_removesBooksAndMultipleRelatedLoans(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "different book ", 2018, 11, 10);
        bm.addBook(firstBook);
        bm.addBook(secondBook);

        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        Member thirdMember = new Member(102, "David", "@mail");
        Member fourthMember = new Member(103, "Pablo", "@mail");

        Loan firstLoan = new Loan(firstBook, firstMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        Loan secondLoan = new Loan(firstBook, secondMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        Loan thirdLoan = new Loan(firstBook,thirdMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        Loan fourthLoan = new Loan(secondBook, fourthMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        lm.addLoan(thirdLoan);
        lm.addLoan(fourthLoan);

        bm.deleteBookById(1, lm);

        assertFalse(bm.getBooks().contains(firstBook));
        assertTrue(bm.getBooks().contains(secondBook));
        assertEquals(1, bm.getBooks().size());
        assertFalse(lm.getLoans().contains(firstLoan));
        assertFalse(lm.getLoans().contains(secondLoan));
        assertFalse(lm.getLoans().contains(thirdLoan));
        assertTrue(lm.getLoans().contains(fourthLoan));
        assertEquals(1, lm.getLoans().size());
    }

    @Test
    void lookUpBooksByTitle_nonValidTitle_ReturnsEmptyList(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "different book ", 2018, 11, 10);
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        List<Book> matches = bm.lookUpBooksByTitle("java");
        assertTrue(matches.isEmpty());
    }

    @Test
    void lookUpBooksByTitle_validLookUp_ReturnsMatch(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "different book ", 2018, 11, 10);
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        List<Book> matches = bm.lookUpBooksByTitle("Code");
        assertEquals(1, matches.size());
        assertTrue(matches.contains(firstBook));
    }

    @Test
    void lookUpByTitle_validLookUp_ReturnsMultipleMatches(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "Efficient code", 2018, 11, 10);
        Book thirdBook = new Book(3, "Modern Java", 2025, 10, 0);
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        bm.addBook(thirdBook);
        List<Book> matches = bm.lookUpBooksByTitle("Code");
        assertEquals(2, matches.size());
        assertTrue(matches.contains(firstBook));
        assertTrue(matches.contains(secondBook));
        assertFalse(matches.contains(thirdBook));
    }

    @Test
    void getBooksWithAvailableCopies_validMatches_returnsListWithMatches(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "Efficient code", 2018, 11, 10);
        Book thirdBook = new Book(3, "Modern Java", 2025, 10, 0);
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        bm.addBook(thirdBook);

        List<Book> matches = bm.getBooksWithAvailableCopies();

        assertEquals(2, matches.size());
        assertTrue(matches.contains(firstBook));
        assertTrue(matches.contains(secondBook));
        assertFalse(matches.contains(thirdBook));
    }

    @Test
    void getBooksWithAvailableCopies_returnsEmptyList(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 0);
        Book secondBook = new Book(2, "Efficient code", 2018, 11, 0);
        bm.addBook(firstBook);
        bm.addBook(secondBook);

        List<Book> matches = bm.getBooksWithAvailableCopies();

        assertTrue(matches.isEmpty());
    }

    @Test
    void findBookById_MatchesId_returnsMatchedBook(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "Efficient code", 2018, 11, 10);
        bm.addBook(firstBook);
        bm.addBook(secondBook);

        assertEquals(firstBook, bm.findBookById(1));
    }
     @Test
    void findBookById_NoMatch_returnsNull() {
         Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
         Book secondBook = new Book(2, "Efficient code", 2018, 11, 10);
         bm.addBook(firstBook);
         bm.addBook(secondBook);

         assertNull(bm.findBookById(10));
     }

     @Test
    void sortBooksByTitle_sortsBooksAlphabetically(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "Efficient code", 2018, 11, 10);
        Book thirdBook = new Book(3, "AI code", 2025, 10, 0);
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        bm.addBook(thirdBook);

        bm.sortBooksByTitle();

        assertEquals(thirdBook, bm.getBooks().get(0));
        assertEquals(firstBook, bm.getBooks().get(1));
        assertEquals(secondBook, bm.getBooks().get(2));
    }

}
