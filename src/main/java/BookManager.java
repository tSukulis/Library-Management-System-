import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Manages all books in the library.
 * Provides methods for adding, removing, searching, and sorting books.
 */
public class BookManager {
    private List<Book> books;

    /**
     * Creates an empty book manager.
     */
    public BookManager(){
        books = new ArrayList<>();
    }

    /**
     * Returns all books.
     *
     * @return all books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Adds a book to the library.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if the book is null or a book with the same id already exists
     */
    public void addBook(Book book) {
        if (book == null)
            throw new IllegalArgumentException("Book cannot be null");

        if(findBookById(book.getId()) != null)
            throw new IllegalArgumentException("Book with id " + book.getId() + " already exists.");

        books.add(book);
    }

    /**
     * Deletes the book with id bookId and removes its associated loan history
     *
     * @param bookId the book id of the book to delete
     * @param lm the loan manager containing the book's loans
     *
     * @throws IllegalArgumentException if the book with bookId does not exist
     * @throws IllegalStateException if the book has an active loan
     */
    public void deleteBookById(int bookId, LoanManager lm){
        Book book = findBookById(bookId);
        if(book == null)
            throw new IllegalArgumentException("Book with id: " +bookId + " could not be found");


        for(Loan loan: lm.getLoans()){
            if(loan.getBook().equals(book)){
                if(!loan.isReturned())
                    throw new IllegalStateException("Book cannot be deleted, it has active Loans");
            }
        }

        Iterator<Book> bookIterator = books.iterator();
        while (bookIterator.hasNext()) {
            if (bookIterator.next().getId() == bookId) {
                bookIterator.remove();
                break;
            }
        }

        Iterator<Loan> loanIterator = lm.getLoans().iterator();
        while (loanIterator.hasNext()) {
            if (loanIterator.next().getBook().getId() == bookId) {
                loanIterator.remove();
            }
        }
    }


    /**
     * Finds books whose titles contain the given text.
     *
     * @param title the search keyword
     * @return a list of matching books
     */
    public List<Book> lookUpBooksByTitle(String title){
        List<Book> result = new ArrayList<>();
        for(Book book: books){

            if(book.getTitle().toLowerCase().contains(title.toLowerCase())){
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books that have at least one available copy.
     *
     * @return a list with books that have available copies
     */
    public List<Book> getBooksWithAvailableCopies(){
        List<Book> copies = new ArrayList<>();
        for(Book book: books){
            if(book.getAvailableCopies() > 0){
                copies.add(book);
            }
        }
        return copies;
    }

    /**
     * Finds a book by its id.
     *
     * @param id the id of the book
     * @return the matching book, or null if no book is found
     */
    public Book findBookById(int id){
        for(Book book: books){
            if(book.getId() == id)
                return book;
        }
        return null;
    }

    /**
     * Sorts all books alphabetically by title.
     */
    public void sortBooksByTitle(){
        Collections.sort(books);
    }

}



