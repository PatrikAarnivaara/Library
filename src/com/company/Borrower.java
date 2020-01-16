package com.company;

import java.util.ArrayList;

public class Borrower extends Person {

    ArrayList<Book> loans = (ArrayList<Book>) FileUtility.loadObject("loans.ser");

    public Borrower(String name, String idNumber) {
        super(name, idNumber);
    }

    public void addLoan(Book book) {
        loans.add(book);
        FileUtility.saveObject("loans.ser", loans);
    }

    private int getIndexOfBook(String title) {
        for (Book book : loans) {
            if (book.getTitle().equals(title)) {
                book.setAvailable(true);
                FileUtility.saveObject("loans.ser", loans);
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

    public void returnBook(String name) {
        try {
            int indexBookRemove = getIndexOfBook(name);
            loans.remove(indexBookRemove);
            FileUtility.saveObject("loans.ser", loans);
            System.out.println("Book returned.");
        } catch (Exception e) {
            System.out.println("Try again, no book with that title in your account.");
        }


    }

}
