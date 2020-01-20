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

    private boolean validateIdNumber(String idNumber) {
        String regex = "^(19|20)?[0-9]{6}[- ]?[0-9]{4}$";
        return Pattern.matches(regex, idNumber);
    }

    //User - Create user account - 1
    void createLibraryAccount() {
        System.out.println("1. Borrower");
        System.out.println("2. Librarian");
        String userType = input.nextLine();
        chooseUserType(userType);
    }

    //User - What type of user: Borrower or Librarian - 2
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

    //User - Register user - 3
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

    //User - Login user - 4 (move to Library class?)
    public Person logInUser() {

        System.out.println("Enter User Name: ");
        String userName = input.nextLine();
        Person user = getUserName(userName);
        if (user != null) {
            while (true) {
                System.out.println("Enter password: ");
                String password = input.nextLine();
                if (password != null) {
                    if (user.getPassword().equals(password)) {
                        System.out.println("Login successful.");
                        return user;
                    }
                } else {
                    System.out.println("Wrong password, try again.");
                }
            }
        }
        System.out.println("Wrong username, try again.");
        return null;

    }

    //User - Check
    Person checkIfBorrowerIsRegistered() {
        System.out.println("Name: ");
        String nameOfBorrower = input.nextLine();
        return getBorrower(nameOfBorrower);
    }

    //Get borrower
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

    //Get username
    public Person getUserName(String userName) {
        for (Person user : users) {
            if (userName.equals(user.getUserName())) {
                return user;
            } else {
                System.out.println("Username unknown, try again.");
                return null;
            }
        }
        return null;
    }

    //User - Show
    void showBorrowerLoans() {
        Borrower borrower = (Borrower) checkIfBorrowerIsRegistered();
        if (borrower != null) {
            borrower.showBorrowedBooks(borrower.getName());
        } else {
            System.out.println("No user with that name.");
        }
    }


    //Librarian - Show
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
