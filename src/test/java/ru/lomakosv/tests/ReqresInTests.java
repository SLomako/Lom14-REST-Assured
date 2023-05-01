package ru.lomakosv.tests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.lomakosv.api.model.*;

import java.time.Clock;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.lomakosv.tests.Specification.requestSpec;
import static ru.lomakosv.tests.Specification.responseSpec;

@Tag("api_test")
@DisplayName("api тесты на сайт Reqres.in")
public class ReqresInTests {

    @DisplayName("email у users/2")
    @Owner("SLomako")
    @Test
    void checkSingleUserDataEmail() {
        String expendDataEmail = "janet.weaver@reqres.in";


        String actualDataEmail = step("Запрос", () ->
        given(requestSpec).
        when()
                .get("/users/2").
        then()
                .spec(responseSpec)
                .statusCode(200).extract().path("data.email"));
        step("Проверка", () -> {
        Assertions.assertEquals(expendDataEmail, actualDataEmail);
        });
    }

    @DisplayName("соотвествие возвращаемых name и job отправленным")
    @Owner("SLomako")
    @Test
    void successfulCreateUser() {
        String name = "morpheus";
        String job = "leader";
        Users users = new Users("morpheus", "leader");
        CreateUser createUser = step("Запрос", () ->
        given(requestSpec)
                .body(users).
        when()
                .post("/users").
        then()
                .spec(responseSpec)
                .statusCode(201)
                .extract()
                .as(CreateUser.class));

        assertNotNull(createUser.getId());
        assertNotNull(createUser.getCreatedAt());
        step("Проверка", () -> {
        Assertions.assertEquals(name, createUser.getName());});
            step("Проверка", () -> {
        Assertions.assertEquals(job, createUser.getJob());});
    }

    @DisplayName("сообщения и статус-кода 400 при ошибки регистрации")
    @Owner("SLomako")
    @Test
    void usSuccessfulCreateUser() {
        String error = "Missing password";
        Register register = new Register("sydney@fife");
        UnSuccessfulRegister unSuccessfulRegister = step("Запрос", () ->
        given(requestSpec)
                .body(register).
        when()
                .post("/register").
        then()
                .spec(responseSpec)
                .statusCode(400)
                .extract()
                .as(UnSuccessfulRegister.class));
        step("Проверка", () -> {
        Assertions.assertEquals(error, unSuccessfulRegister.getError());});
    }

    @DisplayName("статус кода 204 при запросе на удаление пользователя")
    @Owner("SLomako")
    @Test
    void deleteUserTest() {
        step("Запрос");
        given(requestSpec).
        when()
                .delete("/users/2").
        then()
                .spec(responseSpec)
                .statusCode(204);
    }

    @DisplayName("одинаковость(до минут) системного времени у ново созданного пользователя и системы")
    @Owner("SLomako")
    @Test
    void timeTest() {
        Users users = new Users("morpheus", "zion resident");
        CreateUserTime createUserTime = step("Запрос", () ->
        given(requestSpec).
                body(users).
        when()
                .put("/users/2").
        then()
                .spec(responseSpec)
                .statusCode(200)
                .extract()
                .as(CreateUserTime.class));

        String regex = "(.{3}\\..*)$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");
        step("Проверка", () -> {
        Assertions.assertEquals(currentTime, createUserTime.getUpdatedAt().replaceAll(regex, ""));});
    }

    @DisplayName("имя после сортировки ответа по году")
    @Owner("SLomako")
    @Test
    void checkListResource() {
        step("Запрос");
        given(requestSpec).
        when()
                .get("/unknown").
        then()
                .log().all()
                .statusCode(200)
                .body("data.findAll {it.year > 2003}.name", hasItems("tigerlily"));

    }
}


