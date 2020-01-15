package com.company;

import java.util.ArrayList;

public class Borrower extends Person {

    ArrayList<Book> loans = new ArrayList<>(); //(ArrayList<Book>) FileUtility.loadObject("loans.ser");

    public Borrower(String name, String idNumber) {
        super(name, idNumber);
    }

    public void addLoan(Book book) {
        loans.add(book);
    }

    public void removeLoan(Book book) {
        loans.remove(book);
        //FileUtility.saveObject("loans.ser", loans);
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

    public Book returnBook(String name) {

        for (Book book : loans) {
            if (name.equals(book.getTitle())) {
                book.setAvailable(true);
                return book;
            }
        }
        return null;
    }

}
