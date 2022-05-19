package RestAPITesting;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GetUsersPost {

    @Test
    public void getPosts(){
        baseURI="http://training.skillo-bg.com:3100";

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

        for (int element:allPostIDs){
            System.out.println("post id is: " +element);
        }
        Collections.sort(allPostIDs);//Sorting the list in order to get later the latest element from it
        //System.out.println("sorted" + allPostIDs);
        Integer lastPotsID=allPostIDs.get((allPostIDs.size())-1); //why it gets actually the first item in the list?
         System.out.println("user LAST post id is " + lastPotsID);

        for (int item:allUserIds){
            System.out.println("user's id is: "+ item);
        }
       //Collections.sort(allUserIds); NO
       //System.out.println("Sorted: "+allUserIds);
        Integer lastUserID=allUserIds.get(0);
        System.out.println("last post id " + lastUserID);




    }
}
