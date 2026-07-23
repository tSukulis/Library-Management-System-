import java.nio.file.Path;
import java.util.Scanner;

/**
 * Starts the Library Management System and coordinates data loading,
 * menu execution, and data storage.
 */
public class Main {
    private static final Path DATA_DIRECTORY = Path.of("src", "main", "resources");

    private static final Path BOOKS_PATH = DATA_DIRECTORY.resolve("books.json");

    private static final Path MEMBERS_PATH = DATA_DIRECTORY.resolve("members.json");

    private static final Path LOANS_PATH = DATA_DIRECTORY.resolve("loans.json");

    /**
     * Starts the Library Management System.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args){
        BookManager bm = new BookManager();
        MemberManager mm = new MemberManager();
        LoanManager lm = new LoanManager();
        DataLoader.loadBooks(bm, BOOKS_PATH);
        DataLoader.loadMembers(mm, MEMBERS_PATH);
        DataLoader.loadLoansJson(lm, bm, mm, LOANS_PATH);
        System.out.println("Data loaded from JSON files");
        DataDisplay.displayBooks(bm.getBooks(), "Books");
        DataDisplay.displayMembers(mm.getMembers());
        DataDisplay.displayLoans(lm.getLoans(), "Loans");


        Scanner scanner = new Scanner(System.in);
        Menu.run(scanner, bm, mm, lm);
        DataStore.storeBooks(bm, BOOKS_PATH);
        DataStore.storeMembers(mm, MEMBERS_PATH);
        DataStore.storeLoans(lm, LOANS_PATH);
        scanner.close();
    }
}
