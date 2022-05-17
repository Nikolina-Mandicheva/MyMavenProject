package RestAPITesting;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GetUsersPost {

    @Test
    public void getPosts(){
        baseURI="http://training.skillo-bg.com:3100";

        given()
                .when()
                .get("/posts/public?take=2&skip=0")
        .then()
                .body("user.id", equalTo(2467))
                .log()
                .all();



    }
}
