package com.company;

public class Book {

    private String title;
    private String writer;
    private String description;
    private Category category;
    private boolean available;


    public Book(String title, String writer, String description, Category category, boolean available) {
        this.title = title;
        this.writer = writer;
        this.description = description;
        this.category = category;
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void getInfo() {
        System.out.printf(" Title: %s \n Writer: %s \n Description: %s \n Category: %s \n Available: %b \n\n", title, writer, description, category, available);
    }

}
