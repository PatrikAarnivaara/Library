package com.company;

import java.util.ArrayList;

public class Borrower extends Person {

    ArrayList<Book> loans = new ArrayList<>();

    public Borrower(String name, String idNumber) {
        super(name, idNumber);
    }
}
