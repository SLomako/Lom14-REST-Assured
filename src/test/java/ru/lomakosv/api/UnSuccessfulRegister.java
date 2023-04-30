package ru.lomakosv.api;

public class UnSuccessfulRegister {
    private String error;

    public UnSuccessfulRegister(String error) {
        this.error = error;
    }

     public UnSuccessfulRegister() {
         super();
     }

    public String getError() {
        return error;
    }
}

