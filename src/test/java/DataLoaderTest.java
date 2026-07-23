import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataLoaderTest {
    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }
    @TempDir
    private Path tempDirectory;

    @Test
    void loadBooks_validBooks_loadsBooksFromJson() throws IOException {
        Path booksPath = tempDirectory.resolve("books.json");
        List<Book> books = new ArrayList<>();
        Book firstBook = new Book(10, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(11, "Efficient Code", 2018, 11, 10);
        books.add(firstBook);
        books.add(secondBook);

        File file = booksPath.toFile();
        mapper.writeValue(file, books);

        BookManager bm = new BookManager();
        DataLoader.loadBooks(bm, booksPath);

        assertEquals(books.size(), bm.getBooks().size());
        for(int i = 0; i < books.size(); i++){
            Book actualBook = bm.getBooks().get(i);
            Book expectedBook = books.get(i);

            assertEquals(expectedBook.getId(), actualBook.getId());
            assertEquals(expectedBook.getTitle(), actualBook.getTitle());
            assertEquals(expectedBook.getYear(), actualBook.getYear());
            assertEquals(expectedBook.getTotalCopies(), actualBook.getTotalCopies());
            assertEquals(expectedBook.getAvailableCopies(), actualBook.getAvailableCopies());
        }
    }

    @Test
    void loadMembers_validMembers_loadsMembersFromJson() throws IOException {
        Path membersPath = tempDirectory.resolve("members.json");
        List<Member> members = new ArrayList<>();
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        members.add(firstMember);
        members.add(secondMember);

        File file = membersPath.toFile();
        mapper.writeValue(file, members);

        MemberManager mm = new MemberManager();
        DataLoader.loadMembers(mm, membersPath);

        assertEquals(members.size(), mm.getMembers().size());
        for(int i = 0; i < members.size(); i++){
            Member expectedMember = members.get(i);
            Member actualMember = mm.getMembers().get(i);
            assertEquals(expectedMember.getId(), actualMember.getId());
            assertEquals(expectedMember.getName(), actualMember.getName());
            assertEquals(expectedMember.getEmail(), actualMember.getEmail());
        }
    }

    @Test
    void loadLoansJson_validLoans_loadsLoansFromJson() throws IOException {
        Path loanPath = tempDirectory.resolve("loans.json");

        Book firstBook = new Book(10, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(11, "Efficient Code", 2018, 11, 10);
        BookManager bm = new BookManager();
        bm.addBook(firstBook);
        bm.addBook(secondBook);

        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        MemberManager mm = new MemberManager();
        mm.addMember(firstMember);
        mm.addMember(secondMember);

        LocalDate borrowDate = LocalDate.of(2026, 2, 1);
        LocalDate dueDate = LocalDate.of(2026, 2, 15);

        LoanJson firstLoanJson = new LoanJson(firstBook.getId(), firstMember.getId(), borrowDate, dueDate, true);
        LoanJson secondLoanJson = new LoanJson(secondBook.getId(), secondMember.getId(), borrowDate, dueDate, false);

        File file = loanPath.toFile();
        List<LoanJson> list = new ArrayList<>();
        list.add(firstLoanJson);
        list.add(secondLoanJson);
        mapper.writeValue(file, list);

        LoanManager lm = new LoanManager();
        DataLoader.loadLoansJson(lm, bm, mm, loanPath);

        assertEquals(list.size(), lm.getLoans().size());

        for(int i = 0; i < list.size(); i++){
            LoanJson expectedLoanJson = list.get(i);
            LoanJson actualLoanJson = new LoanJson(lm.getLoans().get(i).getBook().getId(), lm.getLoans().get(i).getMember().getId(), lm.getLoans().get(i).getBorrowDate(), lm.getLoans().get(i).getDueDate(), lm.getLoans().get(i).isReturned());

            assertEquals(expectedLoanJson.getBookId(), actualLoanJson.getBookId());
            assertEquals(expectedLoanJson.getMemberId(), actualLoanJson.getMemberId());
            assertEquals(expectedLoanJson.getBorrowDate(), actualLoanJson.getBorrowDate());
            assertEquals(expectedLoanJson.getDueDate(), actualLoanJson.getDueDate());
            assertEquals(expectedLoanJson.isReturned(), actualLoanJson.isReturned());
        }
    }

    @Test
    void loadLoansJson_missingBook_skipsLoan() throws IOException {
        Path loanPath = tempDirectory.resolve("loans.json");

        BookManager bm = new BookManager();

        Member firstMember = new Member(100, "Nick", "@mail");
        MemberManager mm = new MemberManager();
        mm.addMember(firstMember);
        LoanManager lm = new LoanManager();

        LocalDate borrowDate = LocalDate.of(2026, 2, 1);
        LocalDate dueDate = LocalDate.of(2026, 2, 15);

        LoanJson firstLoanJson = new LoanJson(10, firstMember.getId(), borrowDate, dueDate, true);
        List<LoanJson> list = List.of(firstLoanJson);
        mapper.writeValue(loanPath.toFile(), list);

        DataLoader.loadLoansJson(lm, bm, mm, loanPath);

        assertTrue(lm.getLoans().isEmpty());
    }

    @Test
    void loadLoansJson_missingMember_skipsLoan() throws IOException {
        Path loanPath = tempDirectory.resolve("loans.json");
        Book firstBook = new Book(10, "Clean code", 2015, 10, 8);

        BookManager bm = new BookManager();
        bm.addBook(firstBook);

        MemberManager mm = new MemberManager();
        LoanManager lm = new LoanManager();

        LocalDate borrowDate = LocalDate.of(2026, 2, 1);
        LocalDate dueDate = LocalDate.of(2026, 2, 15);

        LoanJson firstLoanJson = new LoanJson(firstBook.getId(), 100, borrowDate, dueDate, true);
        List<LoanJson> list = List.of(firstLoanJson);
        mapper.writeValue(loanPath.toFile(), list);

        DataLoader.loadLoansJson(lm, bm, mm, loanPath);

        assertTrue(lm.getLoans().isEmpty());
    }

    @Test
    void loadLoansJson_dueDateIsNotAfterBorrowDate_skipsLoan() throws IOException {
        Path loanPath = tempDirectory.resolve("loans.json");

        Book firstBook = new Book(10, "Clean code", 2015, 10, 8);
        BookManager bm = new BookManager();
        bm.addBook(firstBook);

        Member firstMember = new Member(100, "Nick", "@mail");
        MemberManager mm = new MemberManager();
        mm.addMember(firstMember);

        LoanManager lm = new LoanManager();

        LocalDate borrowDate = LocalDate.of(2026, 2, 1);
        LocalDate dueDate = LocalDate.of(2026, 1, 15);

        LoanJson firstLoanJson = new LoanJson(firstBook.getId(), firstMember.getId(), borrowDate, dueDate, false);
        List<LoanJson> list = List.of(firstLoanJson);
        mapper.writeValue(loanPath.toFile(), list);

        DataLoader.loadLoansJson(lm, bm, mm, loanPath);

        assertTrue(lm.getLoans().isEmpty());
    }


    @Test
    void loadLoansJson_memberHasFiveLoans_skipsLoan() throws IOException {
        Path loanPath = tempDirectory.resolve("loans.json");

        Book firstBook = new Book(10, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(11, "Efficient Code", 2018, 11, 10);
        Book thirdBook = new Book(3, "Embedded  Systems", 2020, 5, 2);
        Book fourthBook = new Book(4, "C++ Tour", 2022, 5, 3);
        Book fifthBook = new Book(5, "Linux Guide", 2023, 6, 3);
        Book sixthBook = new Book(20, "another book", 2020, 10, 3);
        BookManager bm = new BookManager();
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        bm.addBook(thirdBook);
        bm.addBook(fourthBook);
        bm.addBook(fifthBook);
        bm.addBook(sixthBook);

        Member firstMember = new Member(100, "Nick", "@mail");
        MemberManager mm = new MemberManager();
        mm.addMember(firstMember);

        LoanManager lm = new LoanManager();

        LocalDate borrowDate = LocalDate.of(2026, 2, 1);
        LocalDate dueDate = LocalDate.of(2026, 2, 15);

        LoanJson firstLoanJson = new LoanJson(firstBook.getId(), firstMember.getId(), borrowDate, dueDate, false);
        LoanJson secondLoanJson = new LoanJson(secondBook.getId(), firstMember.getId(), borrowDate, dueDate, false);
        LoanJson thirdLoanJson = new LoanJson(thirdBook.getId(), firstMember.getId(), borrowDate, dueDate, false);
        LoanJson fourthLoanJson = new LoanJson(fourthBook.getId(),  firstMember.getId(), borrowDate, dueDate, false);
        LoanJson fifthLoanJson = new LoanJson(fifthBook.getId(),  firstMember.getId(), borrowDate, dueDate, false);
        LoanJson sixthLoanJson = new LoanJson(sixthBook.getId(), firstMember.getId(), borrowDate, dueDate, false);
        List<LoanJson> list = List.of(firstLoanJson, secondLoanJson, thirdLoanJson, fourthLoanJson, fifthLoanJson, sixthLoanJson);
        mapper.writeValue(loanPath.toFile(), list);

        DataLoader.loadLoansJson(lm, bm, mm, loanPath);

        assertEquals(5, lm.getLoans().size());

    }
}

