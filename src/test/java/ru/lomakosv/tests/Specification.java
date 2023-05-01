package ru.lomakosv.tests;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class Specification {

    public static RequestSpecification request =
            with()
                    .baseUri("https://reqres.in")
                    .basePath("/api")
                    .log()
                    .all()
                    .contentType(JSON);

}
