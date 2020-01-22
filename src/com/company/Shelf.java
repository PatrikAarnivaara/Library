package com.company;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Shelf implements Serializable {

    Comparator<Book> sortWriters = (book, bookTwo) -> (int) book.getWriter().compareTo(bookTwo.getWriter());
    Comparator<Book> sortBookTitles = (book, bookTwo) -> (int) book.getTitle().compareTo(bookTwo.getTitle());

    public ArrayList<Book> books = new ArrayList<>();

    public Shelf() {

        if (Files.exists(Paths.get("books.ser"))) {
            books = (ArrayList<Book>) FileUtility.loadObject("books.ser");
        } else {
            addBooksToShelf();
        }
    }

    //Create
    void addBooksToShelf() {
        books.add(new Book("The Defenders", "Philip K Dick", "The Defenders is a 1953 science fiction novelette.", Category.FICTION, true, "", ""));
        books.add(new Book("Frankenstein", "Mary Shelley", "When Victor Frankenstein, a brilliant scientist, tries to create life in his laboratory.", Category.FICTION, true,"", ""));
        books.add(new Book("Dracula", "Bram Stoker", "Count Dracula, the legendary vampire who is Lord of the Undead.", Category.FICTION, true,"", ""));
        books.add(new Book("The Invisible Man", "H. G. Wells", "When a brilliant scientist discovers an invisibility formula.", Category.FICTION, true,"", ""));
        books.add(new Book("Knife", "Jo Nesbo", "When Harry wakes up the morning after a blackout, " + "\n" + " drunken night with blood that's clearly not his own on his hands.", Category.CRIME, true,"", ""));
        books.add(new Book("Noir: A Novel", "Christopher Moore", "It’s not every afternoon that an enigmatic, comely blonde named Stilton.", Category.CRIME, true,"", ""));
        books.add(new Book("Gutshot Straight: A Novel", "Lou Berney", "Berney is “all in”—sure to win a fervent following with the story of “Shake” Bouchon.", Category.CRIME, true,"", ""));
        books.add(new Book("The Road to Gandolfo: A Novel", "Robert Ludlum", "Under house arrest in Peking. ", Category.CRIME, true,"", ""));
        books.add(new Book("Fahrenheit 451", "Ray Bradbury", "Guy Montag is a fireman. In his world, where television rules.", Category.FICTION, true,"", ""));
        books.add(new Book("Rule of Capture", "Christopher Brown", "Defeated in a devastating war with China. ", Category.HISTORY, true,"", ""));
        books.add(new Book("Witch Hunt", "Gregg Jarret", "How did a small group of powerful intelligence officials convince.", Category.HISTORY, true,"", ""));
        books.add(new Book("The Book", "Stephane Mallarme", "This legendary, unfinished project is now translated into English for the first time.", Category.LITERATURE, true,"", ""));
        books.add(new Book("Family Record", "Patrick Modiano", "An enthralling reflection on the ways that family history influences identity.", Category.LITERATURE, true,"", ""));
        books.add(new Book("Beloved", "Toni Morrison", "Staring unflinchingly into the abyss of slavery.", Category.LITERATURE, true,"", ""));
        books.add(new Book("A Brief History of Time", "Stephen Hawkins", "Published more than two decades ago to great critical acclaim and commercial success.", Category.SCIENCE, true,"", ""));
        books.add(new Book("A Short History of Nearly Everything", "Bill Bryson", "One of the world’s most beloved and bestselling writers takes his ultimate journey.", Category.SCIENCE, true,"", ""));
        books.add(new Book("The Age of AI", "Jason Thacker", "Alexa, how is AI changing our world? We interact with artificial intelligence.", Category.SCIENCE, true,"", ""));
        FileUtility.saveObject("books.ser", books);
    }

    //Add
    public void addNewBookToShelf(String title, String writer, String description, Category category) {
        books.add(new Book(title, writer, description, category, true, "", ""));
        System.out.printf("Book %s has been added to library.\n", title);
    }

    //Remove
    public void removeBookFromShelf(String title) {
        int indexOfBookToRemove = getIndexOfBook(title);
        if (indexOfBookToRemove != 0) {
            books.remove(indexOfBookToRemove);
            System.out.printf("Book %s has been removed from library.\n", title);
        } else {
            System.out.println("No book with that title in library.\n");
        }

    }

    //Show
    void showAllBooks() {
        for (Book book : books) {
            book.getInfo();
        }
    }

    void showAvailableBooks() {
        for (Book book : books) {
            if (book.isAvailable())
                book.getInfo();
        }
    }

    void showBorrowedBooks() {
        for (Book book : books) {
            if (!book.isAvailable())
                book.getInfo();
        }
    }


    private int getIndexOfBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return books.indexOf(book);
            }
        }
        return 0;
    }

    public Book isBookAvailable(String title) {
        Book book = getBook(title);
        if (book != null) {
            if (book.isAvailable()) {
                return book;
            }
        }
        return null;
    }

    public Book getBook(String title) {
        for (Book book : books) {
            if (title.equals(book.getTitle()))
                return book;
        }
        System.out.println("No book with that name in library");
        return null;
    }


    //Sort
    public ArrayList<Book> sortWriters(ArrayList<Book> list) {
        list.sort(sortWriters);
        return list;
    }

    public ArrayList<Book> sortBookTitles(ArrayList<Book> list) {
        list.sort(sortBookTitles);
        return list;
    }

    //Find
    public Book findWriterByName(String name) {
        ArrayList<Book> writers = sortWriters(books);
        for (Book writer : writers) {
            if (writer.getWriter().toLowerCase().contains(name.toLowerCase())) {
                return writer;
            }
        }
        System.out.println("No writer with that name in library catalogue.");
        return null;

    }

    public Book findTitleByName(String name) {
        ArrayList<Book> titles = sortBookTitles(books);
        for (Book title : titles) {
            if (title.getTitle().toLowerCase().contains(name.toLowerCase())) {
                return title;
            }
        }
        System.out.println("No book with that title in library catalogue.");
        return null;

    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
