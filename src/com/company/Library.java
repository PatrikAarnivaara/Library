package com.company;

import java.util.ArrayList;

public class Library {

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Person> borrowers = new ArrayList<>();

    public Library() {
        books.add(new Book("The Defenders", "Philip K Dick", "The Defenders is a 1953 science fiction novelette by American author Philip K. Dick.", Category.FICTION, true));

    }
}
