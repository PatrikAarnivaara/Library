package com.company;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class User implements Serializable {

    transient Scanner input = new Scanner(System.in);
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
        verifyUser(name, userType);
    }

    //Step 4
    private void verifyUser(String name, String userType) {

        boolean validatePassword = true;
        boolean validateIdNumber = true;

        while (validateIdNumber) {
            System.out.println("Id number: YYMMDDXXXX");
            String idNumber = input.nextLine();
            if (validateIdNumber(idNumber)) {
                System.out.println("Choose a username: ");
                String userName = input.nextLine();
                while (validatePassword) {
                    System.out.println("Password: 4-8 characters. Letters and digits only. (aBc1)");
                    String password = input.nextLine();
                    if (validatePassword(password)) {
                        if (userType.equals("1")) {
                            users.add(new Borrower(name, idNumber, userName, password));
                            System.out.println("Your Borrower account is registered.");
                            validatePassword = false;
                            validateIdNumber = false;
                        } else if (userType.equals("2")) {
                            users.add(new Librarian(name, idNumber, userName, password));
                            System.out.println("Your Librarian account is registered.");
                            validatePassword = false;
                            validateIdNumber = false;
                        }
                    } else {
                        System.out.println("Password must be between 4 and 8 digits long and include at least one numeric digit.");
                    }
                }
            } else {
                System.out.println("Wrong Id number format, try again.");
            }
        }

    }


    //Step 5
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
                        System.out.println("Login successful.\n");
                        return user;
                    }
                }
                System.out.println("Wrong password, try again.");
            }
        }
        System.out.println("Wrong username");
        return null;

    }

    private boolean validateIdNumber(String idNumber) {
        String regex = "^(19|20)?[0-9]{6}[- ]?[0-9]{4}$";
        return Pattern.matches(regex, idNumber);
    }

    private boolean validatePassword(String password) {
        String regex = "^(?=.*\\d).{4,8}$"; //Weak password
        //String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"; // Strong password
        return Pattern.matches(regex, password);
    }


    //******************************
    //Get, show and search user
    //******************************

    public void searchBorrowerWithName() {
        System.out.println("Name of borrower: ");
        String name = input.nextLine();
        Person person = getBorrower(name);
        if (person != null) {
            person.getInfo();
        } else {
            System.out.println("No borrower with that name.");
        }
    }

    public Person getBorrower(String name) {
        for (Person borrower : users) {
            if (name.equalsIgnoreCase(borrower.getName())) {
                return borrower;
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

    void getNumberOfDaysLeftOnLoan(Borrower userName) {
        userName.getDaysLeftOfLoanPeriod();

    }

    void showBorrowerLoans(Borrower userName) {
        if (userName != null) {
            userName.showBorrowedBooks(userName.getName());
        } else {
            System.out.println("No user with that name.");
        }
    }

    void showAllBorrowers() {
        for (Person borrower : users) {
            if (borrower instanceof Borrower) {
                borrower.getInfo();
            }
        }

    }

    public ArrayList<Person> getUsers() {
        return users;
    }

}
