package com.company;

import java.io.Serializable;

public abstract class Person implements Serializable {

    private String name;
    private String idNumber;
    private String userName;
    private String password;

    public Person(String name, String idNumber, String userName, String password) {
        this.name = name;
        this.idNumber = idNumber;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public abstract void getInfo();
}
