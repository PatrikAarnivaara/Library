package com.company;

import java.io.Serializable;
import java.util.Scanner;

public class Library implements Serializable {

    transient Scanner input = new Scanner(System.in);
    Shelf shelf = new Shelf();
    User user = new User();

    public void start() {
        mainMenu();
    }


    //**********************************
    //Main, borrower and librarian menus
    //**********************************

    private void mainMenu() {
        while (true) {

            System.out.println("--- Main menu ---");
            System.out.println("1. Login");
            System.out.println("2. Create account");
            System.out.println("3. Show books");
            System.out.println("4. Exit");
            System.out.println("-----------------");
            String option = input.nextLine();

            switch (option) {
                case "1":
                    logIn(user.logInUser());
                    break;
                case "2":
                    user.createLibraryAccount();
                    break;
                case "3":
                    shelf.showAllBooks();
                    break;
                case "4":
                    FileUtility.saveObject("users.ser", user.getUsers());
                    FileUtility.saveObject("books.ser", shelf.books);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Enter a number between 1-4");
                    break;
            }
        }
    }

    private void borrowerMenu(Borrower userName) {

        userName.showLateBooks();

        boolean borrowing = true;

        while (borrowing) {

            System.out.println("--- Borrower menu ---");
            System.out.println("1. Borrow book");
            System.out.println("2. Return book");
            System.out.println("3. Show my loans");
            System.out.println("4. Show days left of loan");
            System.out.println("5. Show all books");
            System.out.println("6. Show available books");
            System.out.println("7. Search on writer or title");
            System.out.println("8. Sort list on writer or title");
            System.out.println("9. Logout");
            System.out.println("----------------------");

            String option = input.nextLine();

            switch (option) {
                case "1":
                    borrowBook(userName);
                    break;
                case "2":
                    returnBook(userName);
                    break;
                case "3":
                    user.showBorrowerLoans(userName);
                    break;
                case "4":
                    user.getNumberOfDaysLeftOnLoan(userName);
                    break;
                case "5":
                    shelf.showAllBooks();
                    break;
                case "6":
                    shelf.showAvailableBooks();
                    break;
                case "7":
                    searchOnWriterOrTitle();
                    break;
                case "8":
                    showSortedListOfWritersOrTitles();
                    break;
                case "9":
                    borrowing = false;
                    break;
                default:
                    System.out.println("Enter a number between 1-9");
                    break;
            }
        }
    }


    private void librarianMenu() {

        boolean administrating = true;

        while (administrating) {

            System.out.println("--- Librarian menu ---");
            System.out.println("1. Add book");
            System.out.println("2. Remove book");
            System.out.println("3. Show borrowed books");
            System.out.println("4. Show all borrowers");
            System.out.println("5. Search borrower name");
            System.out.println("6. Show borrower's loans");
            System.out.println("7. Logout");
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
                    user.showAllBorrowers();
                    break;
                case "5":
                    user.searchBorrowerWithName();
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

    private void logIn(Person userName) {

        if (userName instanceof Borrower) {
            borrowerMenu((Borrower) userName);
        } else if (userName instanceof Librarian) {
            librarianMenu();
        }

    }

    //******************************
    //Borrow and return book
    //******************************

    private void borrowBook(Borrower username) {
        System.out.println("Enter name of book to loan:");
        String titleOfBook = input.nextLine();
        Book book = shelf.isBookAvailable(titleOfBook);
        if (book != null) {
            username.loanBookFromLibrary(book);
            System.out.printf("Book: %s loaned.\n", book.getTitle());
        }

    }

    private void returnBook(Borrower username) {
        if (username != null) {
            System.out.println("Name of book to return:");
            String itemToReturn = input.nextLine();
            username.returnBookToLibrary(itemToReturn);
        } else {
            System.out.println("No borrower with that name.");
        }
    }

    //******************************
    //Search user, writer and title
    //******************************

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

    private void showBorrowerLoans() {
        System.out.println("Enter name of borrower");
        String name = input.nextLine();
        Borrower borrower = (Borrower) user.getBorrower(name);
        user.showBorrowerLoans(borrower);
    }


    //******************************
    //Librarian add and remove book
    //******************************

    private void addNewBookToLibrary() {

        boolean adding = true;

        System.out.println("Title: ");
        String title = input.nextLine();
        System.out.println("Writer: ");
        String writer = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();
        while (adding) {
            try {
                System.out.println("Category: ");
                String categoryInput = input.nextLine();
                Category category = Category.valueOf(categoryInput.toUpperCase());
                shelf.addNewBookToShelf(title, writer, description, category);
                adding = false;
            } catch (Exception e) {
                System.out.println("No such category in library system, try again.");
            }
        }
    }

    private void removeBookFromLibrary() {
        System.out.println("Title of book to remove: ");
        String titleOfBookToRemove = input.nextLine();
        shelf.removeBookFromShelf(titleOfBookToRemove);
    }

}
