package com.company;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
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
        book.setAvailable(false);
        book.setLoanDate(LocalDate.now().toString());
        book.setDueDate(LocalDate.now().plusDays(12).toString());
    }

    private Book getBorrowedBook(String title) {
        for (Book book : loans) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    public void returnBookToLibrary(String title) {
        Book bookReturned = getBorrowedBook(title);
        int indexBookRemove = getIndexOfBook(title);
        if (bookReturned != null) {
            loans.remove(indexBookRemove);
            bookReturned.setAvailable(true);
            bookReturned.setLoanDate("");
            bookReturned.setDueDate("");
            System.out.println("Book returned.");
        }
        else{
            System.out.println("Incorrect title, try again.");
        }
    }

    private int getIndexOfBook(String title) {
        if (title != null)
            for (Book book : loans) {
                if (book.getTitle().equals(title)) {
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
        }
        System.out.println(" ");
    }


    public void showBorrowedBooks(String name) {
        System.out.println("Loaned books by " + name + ": ");
        getBorrowedBooks();

    }

    @Override
    public void getInfo() {
        System.out.printf("\nName: %s\nId number: %s\nUsername: %s\n\n", getName(), getIdNumber(), getUserName());
    }

}
