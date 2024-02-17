package Reqres;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.StringAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GetListResourceTest {
    @Test
    public void getListResource() {

        Response response = RestAssured
                .get("https://reqres.in/api/unknown")
                .andReturn();
        response.prettyPrint();
    }

    @Test
    public void getListResourceParametrs() {
        ValidatableResponse response = RestAssured
                .given()
                .queryParam("page", "1")
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200);

        System.out.println("Connection: \n" + response.extract().header("Connection"));
        System.out.println("Responce: \n" + response.extract().asPrettyString());
    }

    @Test
    public void getListResourceId() {
        ValidatableResponse response = RestAssured
                .given()
                .queryParam("id", "8")
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200);

        System.out.println("Responce: \n" + response.extract().asPrettyString());
    }

    @Test
    public void getListResourceStatusCodeNegativ() {
        Response response = RestAssured
                .given()
                .queryParam("id", "13")
                .when()
                .get("https://reqres.in/api/unknown");


        int statusCode = response.getStatusCode();
        System.out.println("StatusCode: " + statusCode);

        assertEquals("200", statusCode, "Unexpected status code");
    }

    @Test
    public void getListResourceTotal() {
        JsonPath response = RestAssured
                .given()
                .get("https://reqres.in/api/unknown")
                .jsonPath();
        response.prettyPrint();

        assertEquals("12", response.get("total").toString());
        assertEquals("6", response.get("per_page").toString());
    }

    @Test
    public void getListResourceAssert() {
        JsonPath response = RestAssured
                .given()
                .get("https://reqres.in/api/unknown/?id=6")
                .jsonPath();
        response.prettyPrint();

        SoftAssertions softAssert = new SoftAssertions();

       softAssert.assertThat("6").isEqualTo("6");
       softAssert.assertThat("blue turquoise").isEqualTo("blue turquoise");
       softAssert.assertThat("2005").isEqualTo("2005");
       softAssert.assertThat("#53B0AE").isEqualTo("#53B0AE");
       softAssert.assertThat("15-5217").isEqualTo("15-5217");


        softAssert.assertAll();
    }
}
