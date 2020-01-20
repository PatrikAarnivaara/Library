package com.company;

import java.io.Serializable;
import java.util.Scanner;

public class Library implements Serializable {

    Scanner input = new Scanner(System.in);
    Shelf shelf = new Shelf();
    User user = new User();

    public void start() {
        mainMenu();
    }


    //Menus
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
                    System.out.println("Enter a number between 1-5");
                    break;
            }
        }
    }

    private void borrowerMenu(Borrower userName) {

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
                    checkIfBookIsAvailable(userName);
                    break;
                case "2":
                    returnBook();
                    break;
                case "3":
                    user.showBorrowerLoans();
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
                    userName = null;
                    borrowing = false;
                    break;
                default:
                    System.out.println("Enter a number between 1-6");
                    break;
            }
        }
    }


    private void librarianMenu(Librarian userName) {

        boolean administrating = true;

        while (administrating) {

            System.out.println("--- Librarian menu ---");
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
                    user.showAllBorrowers();
                    break;
                case "5":
                    searchBorrowerWithName();//Null pointer exception
                    break;
                case "6":
                    user.showBorrowerLoans();
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
            librarianMenu((Librarian) userName);
        }

    }

    private void searchBorrowerWithName() {
        System.out.println("Name of borrower: ");
        String name = input.nextLine();
        Person person = user.getBorrower(name);
        person.getInfo();
    }


    private void checkIfBookIsAvailable(Borrower username) {
        System.out.println("Enter name of book to loan:");
        String titleOfBook = input.nextLine();
        Book book = shelf.isBookAvailable(titleOfBook);
        if (book != null) {
            username.addLoan(book);
            //FileUtility.saveObject("books.ser", shelf.books);
            System.out.printf("Book: %s loaned.\n", book.getTitle());
        } else {
            System.out.println("Try another title.");
        }

    }

    //Borrower - Return
    private void returnBook() {
        Borrower borrower = (Borrower) user.checkIfBorrowerIsRegistered();
        if (borrower != null) {
            System.out.println("Name of book to return:");
            String itemToReturn = input.nextLine();
            borrower.returnBook(itemToReturn);
        } else {
            System.out.println("No borrower with that name.");
        }
    }

    //Borrower - Search
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


    //Borrower - Show
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

    //Librarian - Add
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

    //Librarian - Remove
    private void removeBookFromLibrary() {
        System.out.println("Title of book to remove: ");
        String titleOfBookToRemove = input.nextLine();
        shelf.removeBookFromShelf(titleOfBookToRemove);
    }

}
