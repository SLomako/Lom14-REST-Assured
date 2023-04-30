package ru.lomakosv.api;

public class CreateUserTime extends Users {
    String updatedAt;

    public CreateUserTime(String name, String job, String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CreateUserTime() {
        super();
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
