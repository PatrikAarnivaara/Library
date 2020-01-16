package com.company;

import java.io.Serializable;

public abstract class Person implements Serializable {

    private String name;
    private String idNumber;

    public Person(String name, String idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public abstract void getInfo();
}
