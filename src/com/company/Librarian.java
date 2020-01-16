package com.company;

import java.io.Serializable;

public class Librarian extends Person implements Serializable {

    public Librarian(String name, String idNumber) {
        super(name, idNumber);
    }

    @Override
    public void getInfo() {
        System.out.printf("Name: %s\n Id number: %s \n", getName(), getIdNumber());
    }


}
