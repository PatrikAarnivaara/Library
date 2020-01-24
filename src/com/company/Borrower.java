package com.company;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Borrower extends Person implements Serializable {

    ArrayList<Book> loans = new ArrayList<>();

    public Borrower(String name, String idNumber, String userName, String password) {
        super(name, idNumber, userName, password);
    }

    //****************************************
    //Borrower loan, return and status of book
    //****************************************

    public void loanBookFromLibrary(Book book) {
        loans.add(book);
        book.setAvailable(false);

        // ***************************************************************************
        // Hårdkodade datum är endast för att visa att metoden som påminner
        // låntagare om försenade böcker fungerar.
        // API metoderna hämtar dagens datum och datumet om 14 dagar från lånedatumet.
        // ***************************************************************************
        //book.setLoanDate("2020-01-02");
        //book.setDueDate("2020-01-22");

        book.setLoanDate(LocalDate.now().toString());
        book.setDueDate(LocalDate.now().plusDays(14).toString());
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
        } else {
            System.out.println("Incorrect title, try again.");
        }
    }

    private Book getBorrowedBook(String title) {
        for (Book book : loans) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
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

    public void getDaysLeftOfLoanPeriod() {
        System.out.println("Loans: ");
        for (Book book : loans) {
            showLeftOfLoanPeriod(book);
        }
        System.out.println(" ");

    }

    private void showLeftOfLoanPeriod(Book book) {
        int numberOfDaysLeft = countDays(book);
        if (numberOfDaysLeft == 0) {
            System.out.printf("%s is due today. \n", book.getTitle());
        } else if (numberOfDaysLeft >= 0) {
            System.out.printf("%s is due in %d days. \n", book.getTitle(), numberOfDaysLeft);
        } else {
            System.out.printf("%s is %d days late. \n", book.getTitle(), Math.abs(numberOfDaysLeft));
        }
    }

    public void getLateBooks() {
        for (Book book : loans) {
            alertWhenBookIsLate(book);
        }
        System.out.println(" ");
    }

    private void alertWhenBookIsLate(Book book) {
        int numberOfDaysLeft = countDays(book);
        if (numberOfDaysLeft < 0) {
            System.out.printf("* Book: %s is %d days late * \n", book.getTitle(), Math.abs(numberOfDaysLeft));
        }
    }

    private int countDays(Book book) {
        LocalDate todayDate = LocalDate.now();
        LocalDate dueDate = LocalDate.parse(book.getDueDate());
        Period period = Period.between(todayDate, dueDate);
        return period.getDays();
    }

    @Override
    public void getInfo() {
        System.out.printf("\nName: %s\nId number: %s\nUsername: %s\n\n", getName(), getIdNumber(), getUserName());
    }

}
