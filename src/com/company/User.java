package com.company;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class User implements Serializable {

    Scanner input = new Scanner(System.in);
    private ArrayList<Person> users = new ArrayList<>();

    public User() {

        if (Files.exists(Paths.get("users.ser"))) {
            users = (ArrayList<Person>) FileUtility.loadObject("users.ser");
        } else {
            FileUtility.saveObject("users.ser", users);
        }

    }

    //******************************
    //Create user account and login
    //******************************

    //Step 1
    void createLibraryAccount() {
        System.out.println("1. Borrower");
        System.out.println("2. Librarian");
        String userType = input.nextLine();
        chooseUserType(userType);
    }

    //Step 2
    private void chooseUserType(String userType) {
        switch (userType) {
            case "1":
                registerUser("1");
                break;
            case "2":
                registerUser("2");
                break;
            default:
                System.out.println("Input 1 or 2.");
                break;
        }
    }

    //Step 3
    private void registerUser(String userType) {
        System.out.println("Name: ");
        String name = input.nextLine();

        boolean validate = true;

        while (validate) {
            System.out.println("Id number: YYMMDDXXXX");
            String idNumber = input.nextLine();
            if (validateIdNumber(idNumber)) {
                System.out.println("Choose a username: "); //If user name exist, extra function.
                String userName = input.nextLine();
                System.out.println("Choose a password");
                String password = input.nextLine();
                if (userType.equals("1")) {
                    users.add(new Borrower(name, idNumber, userName, password));
                    System.out.println("Your Borrower account is registered.");
                    validate = false;
                } else if (userType.equals("2")) {
                    users.add(new Librarian(name, idNumber, userName, password));
                    System.out.println("Your Librarian account is registered.");
                    validate = false;
                }
            } else {
                System.out.println("Wrong format, try again.");
            }
        }
    }

    //Step 4
    public Person logInUser() {
        System.out.println("Enter username: ");
        String userNameLogin = input.nextLine();
        Person user = getUserName(userNameLogin);
        if (user != null) {
            while (true) {
                System.out.println("Enter password: ");
                String password = input.nextLine();
                if (password != null) {
                    if (user.getPassword().equals(password)) {
                        System.out.println("Login successful.");
                        return user;
                    }
                }
                System.out.println("Wrong password, try again.");
            }
        }
        return null;

    }

    private boolean validateIdNumber(String idNumber) {
        String regex = "^(19|20)?[0-9]{6}[- ]?[0-9]{4}$";
        return Pattern.matches(regex, idNumber);
    }


    //******************************
    //Get and show user
    //******************************

    Person checkIfBorrowerIsRegistered() {
        System.out.println("Name: ");
        String nameOfBorrower = input.nextLine();
        return getBorrower(nameOfBorrower);
    }


    public Person getBorrower(String name) {
        for (Person borrower : users) {
            if (name.equals(borrower.getName())) {
                return borrower;
            } else {
                System.out.println("No user with that name, try again.");
                return null;
            }
        }
        return null;
    }


    public Person getUserName(String userName) {
        for (Person user : users) {
            if (userName.equals(user.getUserName())) {
                return user;
            }
        }
        return null;
    }


    void showBorrowerLoans() {
        Borrower borrower = (Borrower) checkIfBorrowerIsRegistered();
        if (borrower != null) {
            borrower.showBorrowedBooks(borrower.getName());
        } else {
            System.out.println("No user with that name.");
        }
    }

    void showAllBorrowers() {
        for (Person borrower : users) {
            if (borrower != null) {
                borrower.getInfo();
            } else {
                System.out.println("No borrowers registered.");
            }
        }

    }

    public ArrayList<Person> getUsers() {
        return users;
    }

}
