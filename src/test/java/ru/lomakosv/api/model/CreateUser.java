package ru.lomakosv.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUser {
    private String name;
    private String job;
    private String id;
    private Date createdAt;
}