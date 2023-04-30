package ru.lomakosv.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.lomakosv.api.*;

import java.time.Clock;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

public class ReqresInTests {

    private final static String URL = "https://reqres.in/";

    @Test
    void checkSingleUserDataEmail() {
        String expendDataEmail = "janet.weaver@reqres.in";

        String actualDataEmail = given().
        when()
                .get(URL + "api/users/2").
        then()
                .log().all()
                .statusCode(200).extract().path("data.email");

        Assertions.assertEquals(expendDataEmail, actualDataEmail);
    }

    @Test
    void successfulCreateUser() {
        String name = "morpheus";
        String job = "leader";
        Users users = new Users("morpheus", "leader");
        CreateUser createUser = given()
                .contentType(JSON)
                .body(users).
        when()
                .post(URL + "api/users").
        then().
                log().all().
                statusCode(201).
                extract().
                as(CreateUser.class);

        Assertions.assertNotNull(createUser.getId());
        Assertions.assertNotNull(createUser.getCreatedAt());
        Assertions.assertEquals(name, createUser.getName());
        Assertions.assertEquals(job, createUser.getJob());
    }

    @Test
    void usSuccessfulCreateUser() {
        String error = "Missing password";
        Register register = new Register("sydney@fife");
        UnSuccessfulRegister unSuccessfulRegister = given()
                .contentType(JSON)
                .body(register).
        when()
                .post(URL + "api/register").
        then()
                .statusCode(400)
                .extract()
                .as(UnSuccessfulRegister.class);

        Assertions.assertEquals(error, unSuccessfulRegister.getError());
    }

    @Test
    void deleteUserTest() {
        given().
        when().
                delete(URL + "api/users/2").
        then().
                statusCode(204);
    }

    @Test
    void timeTest() {
        Users users = new Users("morpheus", "zion resident");
        CreateUserTime createUserTime = given().
                contentType(JSON).
                body(users).
        when()
                .put(URL + "api/users/2").
        then()
                .statusCode(200)
                .extract()
                .as(CreateUserTime.class);

        String regexUserTime = "(.{8})$";
        String regexSystemTime = "(.{14})$";
        
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regexSystemTime, "");
        Assertions.assertEquals(currentTime, createUserTime.getUpdatedAt().replaceAll(regexUserTime, ""));

        System.out.println(currentTime);
        System.out.println(createUserTime.getUpdatedAt().replaceAll(regexUserTime, ""));

    }
}


