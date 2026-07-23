import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores books, members and loans as JSON
 */
public class DataStore {

    private static final ObjectMapper mapper;
    static{
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }



    /**
     * Stores all books in a JSON file.
     *
     * @param bm the book manager containing the books
     * @param booksPath the destination JSON file
     */
    public static void storeBooks(BookManager bm, Path booksPath){
        try {
            File file = booksPath.toFile();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, bm.getBooks());
            System.out.println("Books stored successfully");
        } catch (IOException e) {
            System.out.println("Books could not be stored");
        }
    }


    /**
     * Stores all members in a JSON file
     *
     * @param mm the member manager containing the members
     * @param membersPath the destination JSON file
     */
    public static void storeMembers(MemberManager mm, Path membersPath){
        try{
            File file = membersPath.toFile();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file,mm.getMembers());
            System.out.println("Members stored  successfully.");
        }catch (IOException e){
            System.out.println("Members could not be stored");
        }
    }

    /**
     * Stores all loans in a JSON file
     *
     * @param lm the loan manager containing the loans
     * @param loansPath the destination of the JSON file
     */
    public static void storeLoans(LoanManager lm, Path loansPath){
        List<LoanJson> list = new ArrayList<>();
        for(Loan loan: lm.getLoans()){
            int bookId = loan.getBook().getId();
            int memberId = loan.getMember().getId();
            LoanJson l = new LoanJson(bookId, memberId, loan.getBorrowDate(), loan.getDueDate(), loan.isReturned());
            list.add(l);
        }
        try{
            File file = loansPath.toFile();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
            System.out.println("Loans stored successfully");
        } catch (IOException e){
            System.out.println("Loans could not be stored");
        }
    }
}
