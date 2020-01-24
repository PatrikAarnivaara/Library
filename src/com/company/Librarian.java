package com.company;

import java.io.Serializable;
import java.util.Scanner;

public class Librarian extends Person implements Serializable {


    public Librarian(String name, String idNumber, String userName, String password) {
        super(name, idNumber, userName, password);
    }

    //******************************
    //Librarian add and remove book
    //******************************

    Book addNewBookToLibrary() {
        Scanner input = new Scanner(System.in);
        System.out.println("Title: ");
        String title = input.nextLine();
        System.out.println("Writer: ");
        String writer = input.nextLine();
        System.out.println("Description: ");
        String description = input.nextLine();
        while (true) {
            try {
                System.out.println("Category: ");
                String categoryInput = input.nextLine();
                Category category = Category.valueOf(categoryInput.toUpperCase());
                return new Book(title, writer, description, category, true, "", "");
            } catch (Exception e) {
                System.out.println("No such category in library system, try again.\n");
            }
        }
    }

    public String removeBookFromLibrary() {
        Scanner input = new Scanner(System.in);
        System.out.println("Title of book to remove: ");
        return input.nextLine();
    }

    @Override
    public void getInfo() {
        System.out.printf("\nName: %s\nId number: %s \nUsername: %s \n", getName(), getIdNumber(), getUserName());
    }


}
