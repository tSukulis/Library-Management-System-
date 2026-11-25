import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Displays the application menu and handles user interactions.
 */
public class Menu {

    /**
     * Displays the menu to the user
     */
    public static void displayMenu() {
        System.out.println();
        System.out.println("########## Library Menu ##########");
        System.out.println("1. Display books");
        System.out.println("2. Display members");
        System.out.println("3. Display loans");
        System.out.println("4. Borrow book");
        System.out.println("5. Return book");
        System.out.println("6. Active Loans");
        System.out.println("7. Overdue loans");
        System.out.println("8. Add Book");
        System.out.println("9. Add Member");
        System.out.println("10. Delete Book");
        System.out.println("11. Delete Member");
        System.out.println("12. Search books by title");
        System.out.println("13. Search members by name");
        System.out.println("14. Get Active Loans By Member Id");
        System.out.println("15. Get Returned Loans By Member Id");
        System.out.println("16. Get loans by member id");
        System.out.println("17. Sort books by title");
        System.out.println("18. Sort members by name");
        System.out.println("19. Display Statistics");
        System.out.println("20. Exit");
    }

    /**
     * Runs the main application loop by displaying the menu,
     * accepting user input, and executing the selected action.
     *
     * @param scanner the scanner used to read user input
     * @param bm the book manager
     * @param mm the member manager
     * @param lm the loan manager
     */
    public static void run(Scanner scanner, BookManager bm, MemberManager mm, LoanManager lm) {
        while (true) { try {
                Menu.displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {
                    DataDisplay.displayBooks(bm.getBooks(), "Books");
                }
                else if (choice == 2) {
                    DataDisplay.displayMembers(mm.getMembers());
                }
                else if (choice == 3) {
                    DataDisplay.displayLoans(lm.getLoans(), "Loans");
                }
                else if (choice == 4) {
                    System.out.println("Enter book Id: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter member id: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        lm.borrowBook(bookId, memberId, bm, mm);
                        System.out.println("Loan was successful.\nBook with id " + bookId
                                + " was loaned to member with id " + memberId);
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (choice == 5) {
                    System.out.println("Enter book Id: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter member id: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        lm.returnBook(bookId, memberId);
                        System.out.println("Book with id: " + bookId + " returned by member with id " + memberId);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (choice == 6) {
                    DataDisplay.displayLoans(lm.getActiveLoans(), "ActiveLoans");

                } else if (choice == 7) {
                    DataDisplay.displayLoans(lm.getOverdueLoans(), "OverdueLoans");
                } else if (choice == 8) {
                    Book book = createBook(scanner);
                    if (book == null) {
                        System.out.println("Could not create book");
                        continue;
                    }
                    try {
                        bm.addBook(book);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if (choice == 9) {
                    Member member = createMember(scanner);
                    if (member == null) {
                        System.out.println("Could not create new Member.");
                        continue;
                    }
                    try {
                        mm.addMember(member);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else if (choice == 10) {
                    System.out.println("Enter book Id for deletion");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        bm.deleteBookById(bookId, lm);
                    }
                    catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }


                } else if (choice == 11) {
                    System.out.println("Enter member Id for deletion");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        mm.deleteMemberById(memberId, lm);
                    }
                    catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }

                }
                else if (choice == 12) {
                    System.out.println("Enter keyword to search");
                    String keyword = scanner.nextLine();
                    List<Book> results = bm.lookUpBooksByTitle(keyword);
                    DataDisplay.displayBooks(results, "Results");
                }
                else if (choice == 13) {
                    System.out.println("Enter keyword to search");
                    String keyword = scanner.nextLine();
                    List<Member> results = mm.lookUpMembersByName(keyword);
                    DataDisplay.displayMembers(results);
                }
                else if (choice == 14) {
                    System.out.println("Enter member id.");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    DataDisplay.displayLoans(lm.getActiveLoansByMemberId(id), "Active Loans of member with id: " + id);
                }
                else if (choice == 15) {
                    System.out.println("Enter member id.");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    DataDisplay.displayLoans(lm.getReturnedLoansByMemberId(id),
                            "Returned Loans of member with id: " + id);
                }
                else if (choice == 16) {
                    System.out.println("Enter member id.");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    DataDisplay.displayLoans(lm.getLoansByMemberId(id), "Loans of member with id: " + id);

                }
                else if (choice == 17) {
                    bm.sortBooksByTitle();
                    DataDisplay.displayBooks(bm.getBooks(), "Books");
                }
                else if (choice == 18) {
                    mm.sortMembersByName();
                    DataDisplay.displayMembers(mm.getMembers());
                }
                else if (choice == 19) {
                   DataDisplay.statistics(bm, mm, lm);
                }
                else if (choice == 20) {
                    System.out.println("Program will exit now.");
                    return;
                }
                else {
                    System.out.println("Wrong choice, try again");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong choice, try again.");
                scanner.nextLine();
            }

        }
    }

    /**
     * Creates a book and returns it.
     *
     * @param scanner  the scanner used to read user input.
     * @return the new book
     */
    public static Book createBook(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter bookId: ");
                int id = scanner.nextInt();

                scanner.nextLine();
                System.out.println("Enter book title: ");
                String title = scanner.nextLine().trim();

                System.out.println("Enter release year: ");
                int year = scanner.nextInt();

                scanner.nextLine();
                System.out.println("Enter total copies: ");
                int totalCopies = scanner.nextInt();

                scanner.nextLine();
                int availableCopies = totalCopies;
                return new Book(id, title, year, totalCopies, availableCopies);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Creates a new member and returns it.
     *
     * @param scanner  the scanner used to read user input.
     * @return the new member
     */
    public static Member createMember(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter member id: ");
                int id = scanner.nextInt();

                scanner.nextLine();
                System.out.println("Enter member name");
                String name = scanner.nextLine().trim();

                System.out.println("Enter member email");
                String email = scanner.nextLine().trim();
                return new Member(id, name, email);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
}
