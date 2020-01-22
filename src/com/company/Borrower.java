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

    public void loanBookFromLibrary(Book book) {
        loans.add(book);
        book.setAvailable(false);
        //book.setLoanDate(LocalDate.now().toString());
        //book.setDueDate(LocalDate.now().plusDays(12).toString());

        book.setLoanDate("2020-01-02");//To test method alerting when book is overdue
        book.setDueDate("2020-01-20");
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

    public void daysLeftOfLoanPeriod() {
        System.out.println("Loans: ");
        for (Book book : loans) {
            showDays(book);
        }
        System.out.println(" ");

    }

    private void showDays(Book book) {
        LocalDate loanDay = LocalDate.parse(book.getLoanDate());
        LocalDate dueDay = LocalDate.parse(book.getDueDate());
        Period period = Period.between(loanDay, dueDay);
        int numberOfDaysLeft = period.getDays();
        System.out.printf("%s is due in %d days. \n", book.getTitle(), numberOfDaysLeft);
    }

    public void alertWhenBookIsOverDue() {

        /*Customer customer = findCustomerByName(name);
        if (customer!=null){
            System.out.println("Accounts for "+name+ ":\n-----------");
            customer.showAccounts();
        }*/

        for (Book book : loans) {
            showLateBook(book);
        }
        System.out.println(" ");
    }

    private void showLateBook(Book book) {
        LocalDate loanDay = LocalDate.parse(book.getLoanDate());
        LocalDate dueDay = LocalDate.parse(book.getDueDate());
        int compareValue = loanDay.compareTo(dueDay);
        if (compareValue < 0) {
            System.out.println(" ");
            System.out.printf("* PLEASE RETURN LATE BOOKS * \n");
            System.out.printf("Book %s is %d days late. \n", book.getTitle(), Math.abs(compareValue));
        }

    }

    @Override
    public void getInfo() {
        System.out.printf("\nName: %s\nId number: %s\nUsername: %s\n\n", getName(), getIdNumber(), getUserName());
    }

}
