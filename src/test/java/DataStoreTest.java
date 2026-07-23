import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataStoreTest {
    @TempDir
    private Path tempDirectory;


    @Test
    void storeBooks_validBooks_savesData() throws IOException {
        Path booksTemp = tempDirectory.resolve("books_temp.json");
        Book firstBook = new Book(10, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(11, "Efficient Code", 2018, 11, 10);
        BookManager bm = new BookManager();
        bm.addBook(firstBook);
        bm.addBook(secondBook);
        DataStore.storeBooks(bm, booksTemp);
        assertTrue(Files.exists(booksTemp));

        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = booksTemp.toFile();
        List<Book> books = mapper.readValue(jsonFile, new TypeReference<List<Book>>(){});

        assertEquals(bm.getBooks().size(), books.size());
        for(int i = 0; i < bm.getBooks().size(); i++){
            Book expectedBooks = bm.getBooks().get(i);
            Book actualBooks = books.get(i);

            assertEquals(expectedBooks.getId(), actualBooks.getId());
            assertEquals(expectedBooks.getTitle(), actualBooks.getTitle());
            assertEquals(expectedBooks.getYear(), actualBooks.getYear());
            assertEquals(expectedBooks.getTotalCopies(), actualBooks.getTotalCopies());
            assertEquals(expectedBooks.getAvailableCopies(), actualBooks.getAvailableCopies());
        }
    }

    @Test
    void storeMembers_validMembers_storesMembers() throws IOException {
        Path membersPath = tempDirectory.resolve("members.json");
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        MemberManager mm = new MemberManager();
        mm.addMember(firstMember);
        mm.addMember(secondMember);
        DataStore.storeMembers(mm, membersPath);
        assertTrue(Files.exists(membersPath));

        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = membersPath.toFile();
        List<Member> loadedMembers = mapper.readValue(jsonFile, new TypeReference<List<Member>>() {});

        assertEquals(mm.getMembers().size(), loadedMembers.size());
        for(int i = 0; i < mm.getMembers().size(); i++){
            Member expectedMember = mm.getMembers().get(i);
            Member actualMember = loadedMembers.get(i);
            assertEquals(expectedMember.getId(), actualMember.getId());
            assertEquals(expectedMember.getName(), actualMember.getName());
            assertEquals(expectedMember.getEmail(), actualMember.getEmail());
        }
    }

    @Test
    void storeLoans_validLoans_storesLoansAsJson() throws IOException {
        Path loanPath = tempDirectory.resolve("loans.json");

        Book firstBook = new Book(10, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(11, "Efficient Code", 2018, 11, 10);

        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");

        LocalDate borrowDate = LocalDate.of(2026, 2, 1);
        LocalDate dueDate = LocalDate.of(2026, 2, 15);

        Loan firstLoan = new Loan(firstBook, firstMember, borrowDate, dueDate, true);
        Loan secondLoan = new Loan(secondBook, secondMember, borrowDate, dueDate, false);
        LoanManager lm = new LoanManager();
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        DataStore.storeLoans(lm, loanPath);
        assertTrue(Files.exists(loanPath));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File jsonFile = loanPath.toFile();
        List<LoanJson> rawLoans = mapper.readValue(jsonFile, new TypeReference<List<LoanJson>>(){});

        assertEquals(lm.getLoans().size(), rawLoans.size());
        for(int i = 0; i < lm.getLoans().size(); i++){
            Loan expectedLoan = lm.getLoans().get(i);
            LoanJson actualLoan = rawLoans.get(i);
            assertEquals(expectedLoan.getBook().getId(), actualLoan.getBookId());
            assertEquals(expectedLoan.getMember().getId(), actualLoan.getMemberId());
            assertEquals(expectedLoan.getBorrowDate(), actualLoan.getBorrowDate());
            assertEquals(expectedLoan.getDueDate(), actualLoan.getDueDate());
            assertEquals(expectedLoan.isReturned(), actualLoan.isReturned());

        }
    }
}
