import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a member of the library
 */
public class Member implements Comparable<Member> {
    private int id;
    private String name;
    private String email;


    /**
     * Creates a new member and populates its fields
     *
     * @param id the id of the member
     * @param name the name of the member
     * @param email the email of the member
     * @throws IllegalArgumentException if the id is not positive
     * @throws IllegalArgumentException if the name is blank or null
     * @throws IllegalArgumentException if the email is blank, null or not containing @
     */
    @JsonCreator
    public Member(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("email") String email) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id should be positive: ");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Email cannot be null or blank or not containing @");
        }

        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Returns the member id
     *
     * @return the member id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the member
     * @return the member name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the member email
     * @return the email of the member
     */
    public String getEmail() {
        return email;
    }

    /**
     * Compares this member with another member alphabetically by name.
     *
     * @param other the member to compare with
     * @return a negative integer, zero, or a positive integer if this member's
     *      name is less than, equal to, or greater than the other member's name
     */
    @Override
    public int compareTo(Member other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    /**
     * Compares this member with another object for equality.
     * Two members are considered equal if they have the same id.
     *
     * @param other the  object with which to compare.
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (!(other instanceof Member)) {
            return false;
        }

        Member member = (Member) other;

        return (this.id == member.id);
    }

    /**
     * Returns the hash code for this member
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Returns the string representation of this member
     * @return a string representation of this member
     */
    @Override
    public String toString() {
        return ("MemberId: " + id + ", Name: " + name + ", Email: " + email);
    }
}
