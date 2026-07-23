import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Represents a book in the library
 */
public class Book implements Comparable<Book>{
    private int id;
    private String title;
    private int year;
    private int totalCopies;
    private int availableCopies;


    /**
     * Creates a new book and populates its fields
     *
     * @param id the id of the book
     * @param title the title of the book
     * @param year the publication year of the book
     * @param totalCopies the total copies of the book
     * @param availableCopies the available copies of the book
     *
     * @throws IllegalArgumentException if the id is not positive
     * @throws IllegalArgumentException if the title is blank or null
     * @throws IllegalArgumentException if the year is earlier than 1400 or later than the current year
     * @throws IllegalArgumentException if total copies are negative or zero
     * @throws IllegalArgumentException if available copies are negative or greater than the total copies
     */
    @JsonCreator
    public Book(
            @JsonProperty("id") int id,
            @JsonProperty("title") String title,
            @JsonProperty("year") int year,
            @JsonProperty("totalCopies") int totalCopies,
            @JsonProperty("availableCopies") int availableCopies){
        if(id <= 0)
            throw new IllegalArgumentException("Id should be positive");

        if(title == null || title.isBlank())
            throw new IllegalArgumentException("Title cannot be null or blank");

        int currentYear = LocalDate.now().getYear();
        if(year < 1400 || year > currentYear)
            throw new IllegalArgumentException("Acceptable range of year is between 1400 and " + currentYear);

        if(totalCopies <= 0)
            throw new IllegalArgumentException("Total copies cannot be zero or negative.");

        if(availableCopies < 0 || availableCopies > totalCopies)
            throw new IllegalArgumentException("Available copies must be between 0 and totalCopies");


        this.id = id;
        this.title = title;
        this.year = year;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }


    /**
     * Returns the id of the book
     *
     * @return the id of the book
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the title of the book
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the publication year of the book
     *
     * @return the publication year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the total copies of the book
     *
     * @return the total copies
     */
    public int getTotalCopies() {
        return totalCopies;
    }

    /**
     * Returns the available copies of the book
     *
     * @return the available copies
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * Sets the number of available copies for the book
     *
     * @param availableCopies the number of available copies
     * @throws IllegalArgumentException if available copies are smaller than zero or greater than the total copies
     */
    public void setAvailableCopies(int availableCopies) {
        if(availableCopies < 0 || availableCopies > totalCopies)
            throw new IllegalArgumentException("Available copies must be between 0 and totalCopies");

        this.availableCopies = availableCopies;
    }

    /**
     * Compares the titles of two books alphabetically
     *
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer if this book's
     *     title is less than, equal to, or greater than the other book's title
     */
    @Override
    public int compareTo(Book other){
        return this.title.compareToIgnoreCase(other.title);

    }


    /**
     * Compares this book with another object for equality.
     * Two books are considered equal if they have the same id.
     *
     * @param other the object to compare with
     * @return true if the objects are equal; false otherwise
     */
    @Override
    public boolean equals(Object other){
        if(this == other)
            return true;

        if(other == null)
            return false;

        if(!(other instanceof Book))
            return false;

        Book book = (Book) other;

        return (this.id == book.id);
    }

    /**
     * Returns the hash code for this book
     * @return the hash code
     */
    @Override
    public int hashCode(){
        return Integer.hashCode(id);
    }

    /**
     * Returns a string representation of this book.
     * @return a string representation of the book
     */
    @Override
    public String toString(){
        return ("BookId: " + id + ", Title: " + title + ", Year: " + year
                +", available Copies: " + availableCopies);
    }

}

