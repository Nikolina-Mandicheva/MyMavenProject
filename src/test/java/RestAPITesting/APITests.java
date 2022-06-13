package RestAPITesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class APITests {
    String loginToken;
    Integer userID;
    ArrayList<Integer> userPostIds = new ArrayList<>();
    static Integer commentID1;


    // @Test TODO REGISTER AND CHAIN TO LOGIN CREDENTIALS;MAKE IT ONCE BEFORE THE CLASS

    @BeforeClass
    public void register(){
       //TODO REGISTER AND CHAIN TO LOGIN CREDENTIALS;MAKE IT ONCE BEFORE THE CLASS
     
            RegisterPOJO register=new RegisterPOJO();

            //logic for unique username and pass
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat();
            dateFormat.getDateFormatSymbols();
            // String a=date.toString();
            System.out.println(dateFormat);
            System.out.println(date.getTime());

            register.setUsername("Nikolina"+date.getTime());
            register.setPassword("nikidm-testing-user");
            register.setBirthDate("12.12.1985");
            register.setEmail("n"+date.getTime()+"@a.b");
            register.setPublicInfo("hi");

            ValidatableResponse validatableResponse= given()
                    .header("Content-Type", "application/json")
                    .body(register)
                    .when()
                    .post("/users")
                    .then()
                    .log()
                    .all()
                    .statusCode(201);


        }



    @BeforeTest
    public void loginUser() throws JsonProcessingException {
        //create new login POJO Class object named login
        LoginPOJO login = new LoginPOJO();

        //set the login credentials to our login object. usually do not pass password in a code ;)
        login.setUsernameOrEmail("nikidm-testing-user");
        login.setPassword("nikidm-testing-user");

        //converting our POJO object to Json
        ObjectMapper objectMapper = new ObjectMapper();

        // The converted json will be saved in a String value
        String convertedJson = objectMapper.writeValueAsString(login);
        System.out.println("Converted Json is: " + convertedJson);

        baseURI = "http://training.skillo-bg.com:3100";

        Response response = given()
                .header("Content-Type", "application/json")
                //.header("Authorization", "Bearer + ")
                .body(convertedJson)
                .when()
                .post("/users/login");

        response
                .then()
                .statusCode(201);

        // Convert the response body into a String
        String loginResponseBody = response.getBody().asString();

        loginToken = JsonPath.parse(loginResponseBody).read("$.token");
        System.out.println("Extracted token is: " + loginToken);

        userID = JsonPath.parse(loginResponseBody).read("$.user.id");
        System.out.println("User id is: " + userID);

    }

    @Test
    public void getUsersPosts() {
        ValidatableResponse validatableResponse = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                //.basePath("public")
                .queryParam("skip", 0)
                .queryParam("take", 10)
                //                .param("skip", 0)
//                .param("take", 15)
                .when()
                .get("/users/" + userID + "/posts")
                .then()
                //.body("user.username", equalTo("nikidm-testing-user"))
                //.assertThat().body("id", equalTo(4622))
                .log()
                .all();

        userPostIds = validatableResponse.extract().path("id");
        //System.out.println("user-post-id is " + userPostIds);
        Assert.assertNotEquals(userPostIds, null);
        for (int element : userPostIds) {
            System.out.println(element);

        }
    }

    @Test
    public void likePost() {
        //Created an object of class Action POJO which is needed for making a Like. The object is set to the expected value
        ActionPOJO likePost = new ActionPOJO();
        likePost.setAction("likePost");

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .body(likePost)
                .when()
                .patch("/posts/4673")
                .then()
                .body("post.id", equalTo(4673))
                .log()
                .all();

    }

    @Test
    public void commentPost() {
        //Created an object of class Action POJO which is needed for making a Like. The object is set to the expected value
        ActionPOJO commentPost = new ActionPOJO();
        commentPost.setContent("My new comment from Maven 2");

        Response response = given()
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
        String commentResponseBody = response.getBody().asString();

        Integer commentID = JsonPath.parse(commentResponseBody).read("$.id");
        System.out.println("comment id is " + commentID);
        commentID1 = commentID;
        System.out.println("Class var " + commentID1);

    }

    @Test
    public void getUsersComments() {

        //  int commentID;

        ValidatableResponse validatableResponse = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .when()
                .get("/posts/4622/comments")
                .then()
                .statusCode(200)
                //.assertThat().body("user.id", equalTo(userID))
                .log()
                .all();
        // commentID=JsonPath.parse(response).read("$.id");

        ArrayList<Integer> userCommentsIds = new ArrayList<>();
        userCommentsIds = validatableResponse.extract().path("id");
        Assert.assertNotEquals(userCommentsIds, null);
        for (int element : userCommentsIds) {
            System.out.println("Comment posts ids are: " + element);
        }


        ArrayList<Integer> userIds = new ArrayList<>();
        userIds = validatableResponse.extract().path("user.id");
        Assert.assertEquals(userIds.get(0), userID);
        for (int element : userIds) {
            System.out.println("user id is: " + element);
        }


    }

    @Test
    public void deleteComment() {

        // End to End - first create a comment to post, then delete it

        commentPost();

        System.out.println("inherited " + commentID1);

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .log()
                .all()
                .when()
                .delete("/posts/4622/comments/" + commentID1)
                .then()
                .statusCode(200)
                .body("user.id", equalTo(2399))
                .assertThat().body("id", equalTo(commentID1))
                .log()
                .all();

        //add assertion that the deleted id is the given in the request

    }

    @Test
    public void followUser() {
        ActionPOJO followUser=new ActionPOJO();
        followUser.setAction("followUser");

 //HOW TO CALL A METHOD FROM ANOTHER CLASS, BUT TO TAKE ONLY ONE VALUE OF IT?
        //CAN WE DO IT WITH INHERITING AND SMTH FROM POLYMORPHISM

//        GetUsersPost testID=new GetUsersPost();
//        testID.getPosts();

        ValidatableResponse validatableResponse= given()
                .header("Content-Type", "application/json")
                .queryParam("skip", 0)
                .queryParam("take", 10)
                //.basePath("public")
//                .log()
//                .all()
                .when()
                .get("/posts")
                .then();
        // .body("user.id", equalTo(2467))
//                .log()
//                .all();

        ArrayList<Integer> allPostIDs, allUserIds=new ArrayList<>();
        allPostIDs=validatableResponse.extract().path("id");
        allUserIds=validatableResponse.extract().path("user.id");

        for (int item:allUserIds){
            System.out.println("user's id is: "+item);
        }


        Integer lastUserID=allUserIds.get(0);
        System.out.println("last user id " + lastUserID);

       ValidatableResponse validateResponseFollowers= given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .body(followUser)
                .log()
                .all()
                .when()
                .patch("/users/"+ lastUserID)
                .then()
                .log()
                .all()
                //.assertThat().body("user.followers.id", equalTo(2399))
               .assertThat().body("user.id",equalTo(lastUserID))
               .assertThat().body("user.followers[0].username", equalTo("nikidm-testing-user"));
//        Integer usernameValidation= validateResponseFollowers.extract().path("user.followers.[0].username");
//        Assert.assertEquals(usernameValidation,"nikidm-testing-user");


        unFollowUser();
       confirmUnfollowUser();


    }

    @Test
    public void unFollowUser() {
        ActionPOJO unFollowUser=new ActionPOJO();
        unFollowUser.setAction("unfollowUser");

        ValidatableResponse validatableResponse= given()
                .header("Content-Type", "application/json")
                .queryParam("skip", 0)
                .queryParam("take", 10)
                               .when()
                .get("/posts")
                .then();

        ArrayList<Integer>  allUserIds=new ArrayList<>();

        allUserIds=validatableResponse.extract().path("user.id");

        for (int item:allUserIds){
            System.out.println("user's id is: "+item);
        }

        Integer lastUserID=allUserIds.get(0);

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .body(unFollowUser)
                .log()
                .all()
                .when()
                .patch("/users/"+ lastUserID)
                .then()
                .statusCode(200)
                .assertThat().body("user.id",equalTo(lastUserID));

    }

    @Test
    public void confirmUnfollowUser() {
        ActionPOJO unFollowUser=new ActionPOJO();
        unFollowUser.setAction("unfollowUser");

        ValidatableResponse validatableResponse= given()
                .header("Content-Type", "application/json")
                .queryParam("skip", 0)
                .queryParam("take", 10)
                .when()
                .get("/posts")
                .then();

        ArrayList<Integer>  allUserIds=new ArrayList<>();

        allUserIds=validatableResponse.extract().path("user.id");

        for (int item:allUserIds){
            System.out.println("user's id is: "+item);
        }

        Integer lastUserID=allUserIds.get(0);

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .body(unFollowUser)
                .log()
                .all()
                .when()
                .patch("/users/"+ lastUserID)
                .then()
                .statusCode(400);

    }

}

