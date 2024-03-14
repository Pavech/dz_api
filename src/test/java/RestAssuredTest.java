import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RestAssuredTest {

    private static String token;

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268";

        token = RestAssured.given().contentType(ContentType.JSON).body("{\n" +
                "\"username\": \"test11\",\n" +
                "\"password\": \"testpass\"\n" +
                "}").post("/login").then().assertThat().statusCode(200).and().extract().jsonPath().getString("access_token");

    }

    @Test
    public void registerUserTest() {
        Response response = RestAssured.given().contentType(ContentType.JSON).body("{\n" +
                "\"username\": \"teffff\",\n" +
                "\"password\": \"tessaasass\"\n" +
                "}").post("/register");

        response.then().log().all().assertThat().statusCode(201);
    }


    @Test
    public void loginUserTest() {
        Response response = RestAssured.given().contentType(ContentType.JSON).body("{\n" +
                "\"username\": \"teffff\",\n" +
                "\"password\": \"tessaasass\"\n" +
                "}").post("/login");

        response.then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void checkProductsTest() {
        Response response = RestAssured.get("/products");
        response.then().log().all().assertThat().statusCode(200);
    }

    //    Тест AddNewProductsTest возвращает 405
    @Test
    public void AddNewProductsTest() {
        Response response = RestAssured.given().contentType(ContentType.JSON).body("{\n" +
                "  \"name\": \"New test Product\",\n" +
                "  \"category\": \"Electronics\",\n" +
                "  \"price\": 12.99,\n" +
                "  \"discount\": 5\n" +
                "}").post("/products");
        response.then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void getInformationProductTest() {
        Response response = RestAssured.get("/products/1");
        response.then().log().all().assertThat().statusCode(200);
    }

    //    Тест updateInformationProductTest возвращает 405
    @Test
    public void updateInformationProductTest() {
        Response response = RestAssured.given().contentType(ContentType.JSON).body("{\n" +
                "  \"name\": \"Updated Product Name\",\n" +
                "  \"category\": \"Electronics\",\n" +
                "  \"price\": 15.99,\n" +
                "  \"discount\": 8\n" +
                "}").put("/products/1");
        response.then().log().all().assertThat().statusCode(200);
    }

    //    Тест deleteProductTest возвращает 405
    @Test
    public void deleteProductTest() {
        Response response = RestAssured.delete("/products/1");
        response.then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void addProductUserCartTest() {
        Response response = RestAssured.given().header(new Header("Authorization", "Bearer " + token))
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"product_id\": 1,\n" +
                        "  \"quantity\": 2\n" +
                        "}")
                .post("/cart");
        response.then().log().all().assertThat().statusCode(201);
    }

    @Test
    public void getUserCartTest() {
        Response response = RestAssured.given().header(new Header("Authorization", "Bearer " + token)).get("/cart");
        response.then().log().all().assertThat().statusCode(200);
    }


    @Test
    public void removeUserCartTest() {
        Response response = RestAssured.given().header(new Header("Authorization", "Bearer " + token))
                .delete("/cart/1");
        response.then().log().all().assertThat().statusCode(200);
    }
}
