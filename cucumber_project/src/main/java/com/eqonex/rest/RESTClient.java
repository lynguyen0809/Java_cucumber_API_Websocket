package com.eqonex.rest;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RESTClient {

    public Response GET(String url){
        return given().when().get(url);
    }

    public Response GET(String url, String param, Integer value){
        return given().queryParam(param,value).when().get(url);
    }

    public Response POST(String url, String payload){
        return given().accept(ContentType.JSON).contentType("application/json").
                and().body(payload).
                when().post(url);
    }

    public Response POST(String url, Headers headers, String payload){
        return given().accept(ContentType.JSON).contentType("application/json").
                headers(headers).
                and().body(payload).
                when().post(url);
    }

}
