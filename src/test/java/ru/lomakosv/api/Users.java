package ru.lomakosv.api;

public class Users {

    private String name;
    private String job;

    public Users() {
        super();
    }

    public Users(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}

