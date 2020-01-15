package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Library {

    Scanner input = new Scanner(System.in);
    private ArrayList<Person> borrowers = new ArrayList<>();
    Shelf shelf = new Shelf();
    User user = new User();

    public Library() {
        shelf.createBooksForLibrary();
    }

    public void start() {
        mainMenu();
    }


    //Menus
    private void mainMenu() {
        while (true) {

            System.out.println("--- Main menu ---");
            System.out.println("1. Borrower");
            System.out.println("2. Administrator");
            System.out.println("3. Create account");
            System.out.println("4. Show books");
            System.out.println("5. Exit");
            System.out.println("-----------------");

            String option = input.nextLine();

            switch (option) {
                case "1":
                    //Verify user here
                    borrowerMenu();
                    break;
                case "2":
                    //Verify admin here
                    adminMenu();
                    break;
                case "3":
                    addBorrower();
                    //Create account
                    break;
                case "4":
                    shelf.showAllBooks();
                    break;
                case "5":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter a number between 1-5");
                    break;
            }
        }
    }

    private void borrowerMenu() {

        boolean borrowing = true;

        while (borrowing) {

            System.out.println("--- Borrower menu ---");
            System.out.println("1. Borrow book");
            System.out.println("2. Return book");
            System.out.println("3. Show my loans");
            System.out.println("4. Show all books");
            System.out.println("5. Show available books");
            System.out.println("6. Find book by writer");
            System.out.println("7. Find book by title");
            System.out.println("8. Return to main menu");
            System.out.println("----------------------");

            String option = input.nextLine();

            switch (option) {
                case "1":
                    borrowBook();
                    break;
                case "2":
                    System.out.println("Which book to return: ");
                    //findLoanedBookBorrower, search list and add to Library list.
                    break;
                case "3":
                    showBorrowerLoans();
                    break;
                case "4":
                    System.out.println("Show all books in library");
                    break;
                case "5":
                    System.out.println("Available books");
                    //showAvailableBooks();
                    break;
                case "6":
                    findWriter();
                    break;
                case "7":
                    findTitle();
                    break;
                case "8":
                    borrowing = false;
                    break;
                default:
                    System.out.println("Enter a number between 1-6");
                    break;
            }
        }
    }


    private void adminMenu() {

        boolean administrating = true;

        while (administrating) {

            System.out.println("--- Administrator menu ---");
            System.out.println("1. Add book");
            System.out.println("2. Remove book");
            System.out.println("3. Show borrowed books");
            System.out.println("4. Show all borrowers");
            System.out.println("5. Search borrowers");
            System.out.println("6. Show borrower's books");
            System.out.println("7. Return to main menu");
            System.out.println("--------------------------");

            String option = input.nextLine();

            switch (option) {
                case "1":
                    System.out.println("Input info for new book:");
                    break;
                case "2":
                    System.out.println("Remove book, input title.");
                    break;
                case "3":
                    System.out.println("Borrowed books:");
                    break;
                case "4":
                    System.out.println("Borrowers:");
                    break;
                case "5":
                    System.out.println("Input id number:");
                    break;
                case "6":
                    System.out.println("Books loaned by name: ");
                    break;
                case "7":
                    administrating = false;
                    break;
                default:
                    System.out.println("Enter a number between 1-7");
            }
        }
    }

    //Security
    private void verifyUser() {
        System.out.println("Enter name: ");
        //Search for user
        System.out.println("Enter password: ");
        //Check that password is correct?
        System.out.println("Wrong password, try again.");
    }

    //Borrower
    private void addBorrower() {
        System.out.println("Name: ");
        String name = input.nextLine();
        //Check id-number
        System.out.println("Id number: ");
        String idNumber = input.nextLine();
        System.out.println("Your account is registered.");
        borrowers.add(new Borrower(name, idNumber));
    }


    private Person getBorrower(String name) {
        for (Person borrower : borrowers) {
            if (name.equals(borrower.getName())) {
                return borrower;
            }
        }
        return null;
    }

    //Borrow books
    private void borrowBook() {
        System.out.println("Enter name of book to loan");
        String nameOfBook = input.nextLine();
        Book book = shelf.borrowBook(nameOfBook);
        if (book != null) {
            addBookToBorrower(book);
        } else {
            System.out.println("Try another title.");
        }

    }

    private void addBookToBorrower(Book book) {
        System.out.println("Enter your name: ");
        String nameOfBorrower = input.nextLine();
        Borrower borrower = (Borrower) getBorrower(nameOfBorrower);
        if (borrower != null) {
            borrower.addLoan(book);
        } else {
            System.out.println("No user with that name.");
        }

    }

    private void showBorrowerLoans() {
        System.out.println("Enter your name: ");
        String nameOfBorrower = input.nextLine();
        Borrower borrower = (Borrower) getBorrower(nameOfBorrower);
        if (borrower != null) {
            borrower.showBorrowedBooks(nameOfBorrower);
        }
        else {
            System.out.println("No user with that name.");
        }
    }

    private void findWriter() {
        System.out.println("Name of writer: ");
        try {
            String writer = input.nextLine();
            Book book = shelf.findWriterByName(writer);
            if (book != null) {
                book.getInfo();
            }
        } catch (Exception e) {
            System.out.println("Try again, only characters.");
        }

    }

    private void findTitle() {
        System.out.println("Title of book: ");
        try {
            String title = input.nextLine();
            Book book = shelf.findTitleByName(title);
            if (book != null) {
                book.getInfo();
            }
        } catch (Exception e) {
            System.out.println("Try again, only characters.");
        }

    }


}
