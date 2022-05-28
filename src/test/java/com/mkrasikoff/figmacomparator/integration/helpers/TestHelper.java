package com.mkrasikoff.figmacomparator.integration.helpers;

import com.mkrasikoff.figmacomparator.api.FigmaAPI;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestHelper {

   public static RequestSpecification givenUrl() {
      return given()
              .baseUri(FigmaAPI.BASE_URL_API)
              .config(RestAssuredConfig.config()
                      .httpClient(HttpClientConfig.httpClientConfig()
                              .setParam("http.connection.timeout",1000)));
   }

   public static Headers getBasicHeaderStore(String token) {
      List<Header> headersList = new ArrayList<>();
      Header header = new Header("X-Figma-Token", token);
      headersList.add(header);

      return new Headers(headersList);
   }
}
