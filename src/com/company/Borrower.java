package com.company;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Borrower extends Person implements Serializable {

    ArrayList<Book> loans = new ArrayList<>();

    public Borrower(String name, String idNumber, String userName, String password) {
        super(name, idNumber, userName, password);

        /*if (Files.exists(Paths.get("loans.ser"))) {
            loans = (ArrayList<Book>) FileUtility.loadObject("loans.ser");
        } else {
            FileUtility.saveObject("loans.ser", loans);
        }*/

    }

    public void loanBookFromLibrary(Book book) {
        loans.add(book);
    }

    public void returnBookToLibrary(String title) {
        int indexBookRemove = getIndexOfBook(title);
        loans.remove(indexBookRemove);
        System.out.println("Book returned.");
    }

    private int getIndexOfBook(String title) {
        for (Book book : loans) {
            if (book.getTitle().equals(title)) {
                book.setAvailable(true);
                return loans.indexOf(book);
            }
        }
        return 0;
    }

    public void getBorrowedBooks() {
        if (loans.size() > 0) {
            for (Book loan : loans) {
                System.out.println("Book title: " + loan.getTitle());
            }
        } else {
            System.out.println("You currently do not have any loaned books.");
        }
        System.out.println(" ");
    }


    public void showBorrowedBooks(String name) {
        System.out.println("Loaned books by " + name + ": ");
        getBorrowedBooks();

    }

    @Override
    public void getInfo() {
        System.out.printf("\nName: %s\nId number: %s\nUsername: %s\n", getName(), getIdNumber(), getUserName());
    }

}
