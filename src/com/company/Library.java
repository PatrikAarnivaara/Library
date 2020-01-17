package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Library implements Serializable {

    Scanner input = new Scanner(System.in);
    private ArrayList<Person> borrowers = (ArrayList<Person>) FileUtility.loadObject("borrowers.ser");
    private ArrayList<Person> librarians = (ArrayList<Person>) FileUtility.loadObject("librarians.ser");
    Shelf shelf = new Shelf();
    User user = new User();

    public void start() {
        mainMenu();
    }


    //Menus
    private void mainMenu() {
        while (true) {

            System.out.println("--- Main menu ---");
            System.out.println("1. Borrower");
            System.out.println("2. Librarian");
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
                    librarianMenu();
                    break;
                case "3":
                    createLibraryAccount();
                    break;
                case "4":
                    shelf.showAllBooks();
                    break;
                case "5":
                    FileUtility.saveObject("borrowers.ser", borrowers);
                    FileUtility.saveObject("librarians.ser", librarians);
                    FileUtility.saveObject("books.ser", shelf.books);
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
            System.out.println("6. Search on writer or title");
            System.out.println("7. Sort list on writer or title");
            System.out.println("8. Return to main menu");
            System.out.println("----------------------");

            String option = input.nextLine();

            switch (option) {
                case "1":
                    checkIfBookIsAvailable();
                    break;
                case "2":
                    returnBook();
                    break;
                case "3":
                    showBorrowerLoans();
                    break;
                case "4":
                    shelf.showAllBooks();
                    break;
                case "5":
                    shelf.showAvailableBooks();
                    break;
                case "6":
                    searchOnWriterOrTitle();
                    break;
                case "7":
                    showSortedListOfWritersOrTitles();
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


    private void librarianMenu() {

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
                    addNewBookToLibrary();
                    break;
                case "2":
                    removeBookFromLibrary();
                    break;
                case "3":
                    shelf.showBorrowedBooks();
                    break;
                case "4":
                    showAllBorrowers();
                    break;
                case "5":
                    System.out.println("Input id number:");
                    break;
                case "6":
                    showBorrowerLoans();
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
        //Instance of librarian can change meny
        System.out.println("Enter password: ");
        //Check that password is correct?
        System.out.println("Wrong password, try again.");
    }

    private boolean validateIdNumber(String idNumber) {
        String regex = "^(19|20)?[0-9]{6}[- ]?[0-9]{4}$";
        return Pattern.matches(regex, idNumber);
    }

    //User
    private void createLibraryAccount() {
        System.out.println("1. Borrower");
        System.out.println("2. Librarian");
        String userType = input.nextLine();
        registerUser(userType);
    }

    private void registerUser(String userType) {
        if (userType.equals("1")) {
            registerBorrower();
        } else if (userType.equals("2")) {
            registerLibrarian();
        } else {
            System.out.println("Input 1 or 2.");
        }
    }

    //Borrower
    private void registerBorrower() {
        System.out.println("Name: ");
        String name = input.nextLine();

        boolean validate = true;

        while (validate) {
            System.out.println("Id number: YYMMDDXXXX");
            String idNumber = input.nextLine();
            if (validateIdNumber(idNumber)) {
                borrowers.add(new Borrower(name, idNumber));
                validate = false;
                System.out.println("Your borrower account is registered.");
            }
        }
    }

    private Person checkIfBorrowerIsRegistered() {
        System.out.println("Name: ");
        String nameOfBorrower = input.nextLine();
        return getBorrower(nameOfBorrower);
    }

    private Person getBorrower(String name) {
        for (Person borrower : borrowers) {
            if (name.equals(borrower.getName())) {
                return borrower;
            }
        }
        System.out.println("No borrowers registered.");
        return null;
    }

    private void checkIfBookIsAvailable() {
        System.out.println("Enter name of book to loan");
        String nameOfBook = input.nextLine();
        Book book = shelf.isBookAvailable(nameOfBook);
        if (book != null) {
            borrowBook(book);
            FileUtility.saveObject("books.ser", shelf.books);
            System.out.printf("Book: %s loaned.\n", book.getTitle());
        } else {
            System.out.println("Try another title.");
        }

    }

    private void borrowBook(Book book) {
        Borrower borrower = (Borrower) checkIfBorrowerIsRegistered();
        if (borrower != null) {
            FileUtility.saveObject("books.ser", shelf.books);
            borrower.addLoan(book);
        } else {
            System.out.println("No user with that name.");
        }

    }

    private void showBorrowerLoans() {
        Borrower borrower = (Borrower) checkIfBorrowerIsRegistered();
        if (borrower != null) {
            borrower.showBorrowedBooks(borrower.getName());
        } else {
            System.out.println("No user with that name.");
        }
    }


    private void returnBook() {
        Borrower borrower = (Borrower) checkIfBorrowerIsRegistered();
        if (borrower != null) {
            System.out.println("Return book");
            String itemToReturn = input.nextLine();
            borrower.returnBook(itemToReturn);
        } else {
            System.out.println("No borrower with that name.");
        }
    }

    //Combine to switch with two options
    private void searchOnWriterOrTitle() {
        System.out.println("Search for writer or title");
        System.out.println("1. Writer");
        System.out.println("2. Title");
        String option = input.nextLine();

        switch (option) {
            case "1":
                findWriter();
                break;
            case "2":
                findTitle();
                break;
            default:
                System.out.println("Enter 1 or 2");
                break;
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

    private void showSortedListOfWritersOrTitles() {
        System.out.println("Show list of writer or title");
        System.out.println("1. Writer");
        System.out.println("2. Title");
        String option = input.nextLine();

        switch (option) {
            case "1":
                shelf.sortWriters(shelf.getBooks());
                shelf.showAllBooks();
                break;
            case "2":
                shelf.sortBookTitles(shelf.getBooks());
                shelf.showAllBooks();
                break;
            default:
                System.out.println("Enter 1 or 2");
                break;
        }
    }

    //Librarian
    private void registerLibrarian() {
        System.out.println("Name: ");
        String name = input.nextLine();

        boolean validate = true;

        while (validate) {
            System.out.println("Id number: YYMMDDXXXX");
            String idNumber = input.nextLine();
            if (validateIdNumber(idNumber)) {
                librarians.add(new Librarian(name, idNumber));
                validate = false;
                System.out.println("Your Librarian account is registered.");
            }
        }
    }

    private void showAllBorrowers() {
        for (Person borrower : borrowers) {
            if (borrower != null) {
                borrower.getInfo();
            } else {
                System.out.println("No borrowers registered.");
            }
        }

    }

    private void addNewBookToLibrary() {
        System.out.println("Title: ");
        String title = input.nextLine();
        System.out.println("Writer: ");
        String writer = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();
        try {
            System.out.println("Category: ");
            String categoryInput = input.nextLine();
            Category category = Category.valueOf(categoryInput.toUpperCase());
            shelf.addNewBookToShelf(title, writer, description, category);
        } catch (Exception e) {
            System.out.println("No such category in library system, try again.");
        }
    }

    private void removeBookFromLibrary() {
        System.out.println("Title of book to remove: ");
        String titleOfBookToRemove = input.nextLine();
        shelf.removeBookFromShelf(titleOfBookToRemove);
    }

}
