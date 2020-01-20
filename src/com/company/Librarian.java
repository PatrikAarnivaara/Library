package com.company;

import java.io.Serializable;

public class Librarian extends Person implements Serializable {

    public Librarian(String name, String idNumber, String userName, String password) {
        super(name, idNumber, userName, password);
    }

    @Override
    public void getInfo() {
        System.out.printf("Name: %s\n Id number: %s \n", getName(), getIdNumber());
    }


}
