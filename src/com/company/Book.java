package com.company;

import java.io.Serializable;

public class Book implements Serializable {

    private String title;
    private String writer;
    private String description;
    private Category category;
    private boolean available;
    private String loanDate;
    private String dueDate;


    public Book(String title, String writer, String description, Category category,
                boolean available, String loanDate, String dueDate) {
        this.title = title;
        this.writer = writer;
        this.description = description;
        this.category = category;
        this.available = available;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void getInfo() {
        System.out.printf(" Title: %s \n Writer: %s \n Description: %s \n Category: %s \n Available: %b \n Loan Date: %s \n Due Date: %s \n\n",
                title, writer, description, category, available, loanDate, dueDate);
    }

}
