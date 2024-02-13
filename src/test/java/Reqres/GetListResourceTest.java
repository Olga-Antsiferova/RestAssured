package Reqres;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GetListResourceTest {
    @Test
    public void GetListResource() {

        Response response = RestAssured
                .get("https://reqres.in/api/unknown")
                .andReturn();
        response.prettyPrint();
    }

    @Test
    public void GetListResourceParametrs() {
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
    public void GetListResourceId() {
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
    public void GetListResourceStatusCode() {
        Response response = RestAssured
                .given()
                .queryParam("id", "13")
                .when()
                .get("https://reqres.in/api/unknown");


        int statusCode = response.getStatusCode();
        System.out.println("StatusCode: " + statusCode);

        assertEquals(200, statusCode, "Unexpected status code");
    }

    @Test
    public void GetListResourceTotal() {
        JsonPath response = RestAssured
                .given()
                .get("https://reqres.in/api/unknown")
                .jsonPath();
        response.prettyPrint();

        assertEquals("12", response.get("total").toString());
        assertEquals("6", response.get("per_page").toString());
    }

    @Test
    public void GetListResourceAssert() {
        JsonPath response = RestAssured
                .given()
                .get("https://reqres.in/api/unknown/?id=6")
                .jsonPath();
        response.prettyPrint();

        assertEquals("6", response.get("id").toString());
        assertEquals("blue turquoise", response.get("name").toString());
        assertEquals("2005", response.get("year").toString());
        assertEquals("#53B0AE", response.get("color").toString());
        assertEquals("15-5217", response.get("pantone_value").toString());
    }
}
