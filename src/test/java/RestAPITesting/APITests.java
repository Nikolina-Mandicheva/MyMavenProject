package RestAPITesting;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Array;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class APITests {
    String loginToken;
    Integer userID;
    Integer userPostId;
    Integer commentID;

    @BeforeTest
    public void loginUser() throws JsonProcessingException {
        //create new login POJO Class object named login
        LoginPOJO login=new LoginPOJO();

        //set the login credentials to our login object. usually do not pass password in a code ;)
        login.setUsernameOrEmail("nikidm-testing-user");
        login.setPassword("nikidm-testing-user");

       //converting our POJO object to Json
        ObjectMapper objectMapper=new ObjectMapper();

        // The converted json will be saved in a String value
        String convertedJson = objectMapper.writeValueAsString(login);
        System.out.println("Converted Json is: " + convertedJson);

        baseURI="http://training.skillo-bg.com:3100";

        Response response=given()
                .header("Content-Type", "application/json")
                //.header("Authorization", "Bearer + ")
                .body(convertedJson)
                .when()
                .post("/users/login");

        response
                .then()
                .statusCode(201);

        // Convert the response body into a String
        String loginResponseBody=response.getBody().asString();

       loginToken = JsonPath.parse(loginResponseBody).read("$.token");
        System.out.println("Extracted token is: " + loginToken);

        userID=JsonPath.parse(loginResponseBody).read("$.user.id");
        System.out.println("User id is: " +userID);

    }

    @Test
    public void getUsersPosts(){
     Response response=   given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .param("skip", 0)
                .param("take", 15)
                .when()
                .get("/users/" +userID+"/posts");
     response
                .then()
            //.body("user.username", equalTo("nikidm-testing-user"))
             // .body("id", equalTo(4622))
                .log()
                .all();

//        userPostId=JsonPath.parse(response).read("$.id");
//        System.out.println("user-post-id is " + userPostId);
    }

    @Test
    public void likePost(){
        //Created an object of class Action POJO which is needed for making a Like. The object is set to the expected value
        ActionPOJO likePost=new ActionPOJO();
        likePost.setAction("likePost");

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .body(likePost)
                .when()
                .patch("/posts/4622")
                .then()
                .body("post.id", equalTo(4622))
                .log()
                .all();

    }

    @Test
    public void commentPost(){
        //Created an object of class Action POJO which is needed for making a Like. The object is set to the expected value
        ActionPOJO commentPost=new ActionPOJO();
        commentPost.setContent("My new comment from Maven 2");

       Response response= given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .body(commentPost)
                .when()
                .post("/posts/4622/comment");

          response
                .then()
                .statusCode(201)
                .body("content", equalTo("My new comment from Maven 2"))
                .log()
                .all();

//        // Convert the response body into a String
        String commentResponseBody=response.getBody().asString();

        commentID=JsonPath.parse(commentResponseBody).read("$.id");
        System.out.println("comment id is " + commentID);

    }

    @Test
    public void getUsersComments(){

     //  int commentID;

       Response response= given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .when()
                .get("/posts/4622/comments");

       response
               .then()
               .statusCode(200)
               .log()
               .all();
       // commentID=JsonPath.parse(response).read("$.id");


    }

    @Test
    public void deleteComment(){

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .when()
                .delete("/posts/4622/comments/"+commentID)
                .then()
                .statusCode(200)
                .body("user.id", equalTo(2399))
                .body("id",equalTo(commentID))
                .log()
                .all();

        //add assertion that the deleted id is the given in the request

    }


}

