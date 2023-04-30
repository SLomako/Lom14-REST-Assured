package ru.lomakosv.api;

public class Register {

    private String email;

    public Register(String email) {
        this.email = email;
    }

    public Register() {
        super();
    }

    public String getEmail() {
        return email;
    }
}
