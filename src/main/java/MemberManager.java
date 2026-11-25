import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Manages all members in the library
 * Provides methods for adding, removing, searching, and sorting members
 */
public class MemberManager {
    private List<Member> members;

    /**
     * Creates an empty member manager
     */
    public MemberManager() {
        members = new ArrayList<>();
    }

    /**
     * Returns all members
     *
     * @return a list containing all members
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * Adds a member to the library
     *
     * @param member the member to add
     * @throws IllegalArgumentException if the member is null or if a member with the same id already exists
     */
    public void addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        if (findMemberById(member.getId()) != null) {
            throw new IllegalArgumentException("Member with id " + member.getId() + " already exists");
        }

        members.add(member);
    }


    /**
     * Deletes the member with the given id
     *
     * @param memberId the member id
     * @param lm the loan manager containing the member's loans
     *
     * @throws IllegalArgumentException if the member with memberId does not exist
     * @throws IllegalStateException if the member has active loans
     */
    public void deleteMemberById(int memberId, LoanManager lm) {
        Member member = findMemberById(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member with id: " + memberId + " could not be found");
        }


        for (Loan loan: lm.getLoans()) {
            if (loan.getMember().equals(member)) {
                if (!loan.isReturned()) {
                    throw new IllegalStateException("Member cannot be deleted, has active loans");
                }
            }
        }


        Iterator<Member> memberIterator = members.iterator();
        while (memberIterator.hasNext()) {
            if (memberIterator.next().equals(member)) {
                memberIterator.remove();
                break;
            }
        }

        Iterator<Loan> loanIterator = lm.getLoans().iterator();
        while (loanIterator.hasNext()) {
            if (loanIterator.next().getMember().getId() == memberId) {
                loanIterator.remove();
            }
        }
    }

    /**
     * Searches for members whose names contain the given name
     *
     * @param name the search keyword
     * @return a list of matching members
     */
    public List<Member> lookUpMembersByName(String name) {
        List<Member> result = new ArrayList<>();
        for (Member member: members) {
            if (member.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(member);
            }
        }
        return result;
    }

    /**
     * Finds a member by its id
     *
     * @param id the id of the member
     * @return the matching member or null if no member is found
     */
    public Member findMemberById(int id) {
        for (Member member: members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    /**
     * Sorts all members alphabetically by name
     */
    public void sortMembersByName() {
        Collections.sort(members);
    }

}
