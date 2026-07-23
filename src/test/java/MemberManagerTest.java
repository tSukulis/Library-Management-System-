import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemberManagerTest {
    BookManager bm;
    MemberManager mm;
    LoanManager lm;

    @BeforeEach
    void setUp(){
        bm = new BookManager();
        mm = new MemberManager();
        lm = new LoanManager();
    }

    @Test
    void addMember_nullArgument_throwsIllegalArgumentException(){
        Member member = null;
        assertThrows(IllegalArgumentException.class, () -> mm.addMember(member));
    }

    @Test
    void addMember_duplicatedId_throwsIllegalArgumentException(){
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(100, "Tom", "@mail");

        mm.addMember(firstMember);
        assertThrows(IllegalArgumentException.class, () -> mm.addMember(secondMember));
    }

    @Test
    void addMember_validMember_addsMemberToList(){
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");

        mm.addMember(firstMember);
        mm.addMember(secondMember);

        assertTrue(mm.getMembers().contains(firstMember));
        assertTrue(mm.getMembers().contains(secondMember));
        assertEquals(2, mm.getMembers().size());
    }

    @Test
    void deleteMemberById_invalidId_throwsIllegalArgumentException(){
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");

        mm.addMember(firstMember);
        mm.addMember(secondMember);

        assertThrows(IllegalArgumentException.class, () -> mm.deleteMemberById(110, lm));
        assertEquals(2, mm.getMembers().size());
    }

    @Test
    void deleteMemberById_loanNotReturned_throwsIllegalStateException(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "different book ", 2018, 11, 10);
        bm.addBook(firstBook);
        bm.addBook(secondBook);

        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");

        mm.addMember(firstMember);
        mm.addMember(secondMember);

        Loan loan = new Loan(firstBook, firstMember, LocalDate.now(), LocalDate.now().plusDays(14), false);
        lm.addLoan(loan);

        assertThrows(IllegalStateException.class, () -> mm.deleteMemberById(100, lm));
        assertEquals(2, mm.getMembers().size());
        assertEquals(1, lm.getLoans().size());
        assertTrue(lm.getLoans().contains(loan));
    }

    @Test
    void deleteMemberById_returnedLoans_removesMembersAndRelatedLoans(){
        Book firstBook = new Book(1, "Clean code", 2015, 10, 8);
        Book secondBook = new Book(2, "different book ", 2018, 11, 10);
        bm.addBook(firstBook);
        bm.addBook(secondBook);



        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        Member thirdMember = new Member(102, "David", "@mail");
        Member fourthMember = new Member(103, "Pablo", "@mail");
        mm.addMember(firstMember);
        mm.addMember(secondMember);
        mm.addMember(thirdMember);
        mm.addMember(fourthMember);

        Loan firstLoan = new Loan(firstBook, firstMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        Loan secondLoan = new Loan(secondBook, firstMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        Loan thirdLoan = new Loan(firstBook,thirdMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        Loan fourthLoan = new Loan(secondBook, fourthMember, LocalDate.now(), LocalDate.now().plusDays(14), true);
        lm.addLoan(firstLoan);
        lm.addLoan(secondLoan);
        lm.addLoan(thirdLoan);
        lm.addLoan(fourthLoan);

        mm.deleteMemberById(100, lm);

        assertFalse(mm.getMembers().contains(firstMember));
        assertTrue(mm.getMembers().contains(secondMember));
        assertTrue(mm.getMembers().contains(thirdMember));
        assertTrue(mm.getMembers().contains(fourthMember));
        assertFalse(lm.getLoans().contains(firstLoan));
        assertFalse(lm.getLoans().contains(secondLoan));
        assertTrue(lm.getLoans().contains(thirdLoan));
        assertTrue(lm.getLoans().contains(fourthLoan));
        assertEquals(3, mm.getMembers().size());
        assertEquals(2, lm.getLoans().size());
    }

    @Test
    void lookUpMembersByName_invalidName_ReturnsEmptyList(){
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        mm.addMember(firstMember);
        mm.addMember(secondMember);
        List<Member> matches = mm.lookUpMembersByName("Pablo");
        assertTrue(matches.isEmpty());
    }

    @Test
    void lookUpMembersByName_validName_returnsMatch(){
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        mm.addMember(firstMember);
        mm.addMember(secondMember);
        List<Member> matches = mm.lookUpMembersByName("Tom");

        assertEquals(1, matches.size());
        assertTrue(matches.contains(secondMember));
    }

    @Test
    void lookUpMembersByName_validName_returnsMultipleMatches(){
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        Member thirdMember = new Member(102, "Tom him", "@mail");
        mm.addMember(firstMember);
        mm.addMember(secondMember);
        mm.addMember(thirdMember);

        List<Member> matches = mm.lookUpMembersByName("Tom");
        assertEquals(2, matches.size());
        assertTrue(matches.contains(secondMember));
        assertTrue(matches.contains(thirdMember));
        assertFalse(matches.contains(firstMember));
    }

    @Test
    void findMemberById_matchesId_returnsMatchedMember(){
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        mm.addMember(firstMember);
        mm.addMember(secondMember);

        assertEquals(firstMember, mm.findMemberById(100));
    }

    @Test
    void findMemberById_noMatch_returnsNull(){
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        mm.addMember(firstMember);
        mm.addMember(secondMember);

        assertNull(mm.findMemberById(102));
    }

    @Test
    void sortMembersByName_sortsMembersAlphabetically(){
        Member firstMember = new Member(100, "Nick", "@mail");
        Member secondMember = new Member(101, "Tom", "@mail");
        Member thirdMember = new Member(102, "Aaron", "@mail");
        mm.addMember(firstMember);
        mm.addMember(secondMember);
        mm.addMember(thirdMember);

        mm.sortMembersByName();

        assertEquals(thirdMember, mm.getMembers().get(0));
        assertEquals(firstMember, mm.getMembers().get(1));
        assertEquals(secondMember, mm.getMembers().get(2));
    }

}
