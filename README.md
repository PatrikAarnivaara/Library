# Library
> Library application for loaning and returning books.

## General info
School project to create a library app and learn how to save and load from file with IO Utility.

## Technologies
* Java

## Setup
Download and import to optional IDE. 

## Code Examples
Show examples of usage:
`private void showLeftOfLoanPeriod(Book book) {
        int numberOfDaysLeft = countDays(book);
        if (numberOfDaysLeft == 0) {
            System.out.printf("%s is due today. \n", book.getTitle());
        } else if (numberOfDaysLeft >= 0) {
            System.out.printf("%s is due in %d days. \n", book.getTitle(), numberOfDaysLeft);
        } else {
            System.out.printf("%s is *%d* days late. \n", book.getTitle(), Math.abs(numberOfDaysLeft));
        }
    }`

## Features
* Create user or admin account
* Login
* Borrow and return books
* Admin can add and remove books
* User is alerted if book is late or soon to be due

## Status
Project is: _finished_

## Contact
Created by Patrik Aarnivaara - feel free to contact me!
