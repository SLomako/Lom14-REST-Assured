package ru.lomakosv.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.lomakosv.api.model.*;

import java.time.Clock;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.lomakosv.tests.Specification.request;

public class ReqresInTests {

    @Test
    void checkSingleUserDataEmail() {
        String expendDataEmail = "janet.weaver@reqres.in";

        String actualDataEmail = given().spec(request).
        when()
                .get("/users/2").
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
        CreateUser createUser = given().spec(request)
                .contentType(JSON)
                .body(users).
        when()
                .post("/users").
        then().
                log().all().
                statusCode(201).
                extract().
                as(CreateUser.class);

        assertNotNull(createUser.getId());
        assertNotNull(createUser.getCreatedAt());
        Assertions.assertEquals(name, createUser.getName());
        Assertions.assertEquals(job, createUser.getJob());
    }

    @Test
    void usSuccessfulCreateUser() {
        String error = "Missing password";
        Register register = new Register("sydney@fife");
        UnSuccessfulRegister unSuccessfulRegister = given().spec(request)
                .contentType(JSON)
                .body(register).
        when()
                .post("/register").
        then()
                .statusCode(400)
                .extract()
                .as(UnSuccessfulRegister.class);

        Assertions.assertEquals(error, unSuccessfulRegister.getError());
    }

    @Test
    void deleteUserTest() {
        given().spec(request).
        when().
                delete("/users/2").
        then().
                statusCode(204);
    }

    @Test
    void timeTest() {
        Users users = new Users("morpheus", "zion resident");
        CreateUserTime createUserTime = given().spec(request).
                contentType(JSON).
                body(users).
        when()
                .put("/users/2").
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

    @Test
    void checkListResource() {
        given().
                spec(request).
        when()
                .get("/unknown").
        then()
                .log().all()
                .statusCode(200)
                .body("data.findAll {it.year > 2003}.name", hasItems("tigerlily"));

    }
}


