package ru.lomakosv.tests;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static ru.lomakosv.helpers.CustomApiListener.withCustomTemplates;

public class Specification {

    public static RequestSpecification requestSpec =
            with()
                    .baseUri("https://reqres.in")
                    .basePath("/api")
                    .log().all()
                    .filter(withCustomTemplates())
                    .contentType(JSON);

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder().
            log(STATUS).
            log(BODY).
            build();

}
