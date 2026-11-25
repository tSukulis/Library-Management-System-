import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Loads books, members and loans from JSON files
 */
public class DataLoader {
    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Loads books from a JSON file into the book manager.
     * @param bm the book manager
     * @param booksPath the path of the JSON file
     */
    public static void loadBooks(BookManager bm, Path booksPath) {
        try {
            File jsonFile = booksPath.toFile();
            List<Book> books = mapper.readValue(jsonFile, new TypeReference<List<Book>>() { });
            for (Book book: books) {
                bm.addBook(book);
            }
            System.out.println("Books loaded");
        } catch (IOException e) {
            System.out.println("Books could not be loaded");
        }
    }

    /**
     * Loads members from a JSON file into the member manager.
     * @param mm the member manager
     * @param membersPath the path of the JSON file
     */
    public static void loadMembers(MemberManager mm, Path membersPath) {
        try {
            File membersJson = membersPath.toFile();
            List<Member> members = mapper.readValue(membersJson, new TypeReference<List<Member>>() { });
            for (Member member: members) {
                mm.addMember(member);
            }
        } catch (IOException e) {
            System.out.println("Could not load members. Starting with an empty member list.");
        }
    }

    /**
     * Loads loans from a JSON file into the loan manager.
     *
     * @param lm the loan manager
     * @param bm the book manager
     * @param mm the member manager
     * @param loansPath the path to the JSON file
     */
    public static void loadLoansJson(LoanManager lm, BookManager bm, MemberManager mm, Path loansPath) {
        try {
            File loansFile = loansPath.toFile();
            List<LoanJson> loans = mapper.readValue(loansFile, new TypeReference<List<LoanJson>>() { });
            loadLoans(loans, lm, bm, mm);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Converts the given LoanJson objects into loans and adds valid loans
     * to the loan manager.
     *
     * @param loansJson the raw list of LoanJson
     * @param lm the loan manager
     * @param bm the book manager
     * @param mm the member manager
     */
    private static void loadLoans(List<LoanJson> loansJson, LoanManager lm, BookManager bm, MemberManager mm) {
        if (loansJson == null) {
            System.out.println("Could not load Loans. Starting with an empty loan list");
            return;
        }
        for (LoanJson loan: loansJson) {
            Book book = bm.findBookById(loan.getBookId());
            Member member = mm.findMemberById(loan.getMemberId());

            if (book == null || member == null) {
                System.out.println("Loan skipped: referenced book or member was not found.");
                continue;
            }
            if (!loan.getDueDate().isAfter(loan.getBorrowDate())) {
                System.out.println("Invalid loan dates skipped");
                continue;
            }

            if (!loan.isReturned() && lm.getNumberOfActiveLoansByMemberId(loan.getMemberId()) >= 5) {
                   System.out.println("Loan skipped: member already has 5 active loans.");
                   continue;
            }

            try {
                Loan l = new Loan(book, member, loan.getBorrowDate(), loan.getDueDate(), loan.isReturned());
                lm.addLoan(l);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
