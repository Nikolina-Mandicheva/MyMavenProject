import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.*;

public class ABExercise {

    @DataProvider(name = "ABData")
    public Object[][] createData1() {
        return new Object[][]{
                {10, 15},
                /*{20,15},*/
        };
    }

    @Test(dataProvider = "ABData", groups= "sum")
    public void sumAB(int a, int b){
        System.out.println("The sum of the both values is: " + (a+b));
    assertTrue((a+b)>=0, "Failed as sum is negative");
   assertEquals((a+b), 25);
    }

    @Test(dataProvider = "ABData", groups= "subtraction")
    public void subtractAB(int a, int b){
        SoftAssert softAssert=new SoftAssert();
        System.out.println("The subtraction of the both values is: " + (a-b));

        softAssert.assertTrue(a<b, "a is smaller than b");
        softAssert.assertTrue(a>b, "a is bigger than b");
        softAssert.assertEquals((a-b),0, "(a-b) is Not 0");
        softAssert.assertFalse(a<0, "value a is smaller than 0");
        softAssert.assertFalse(b<0, " value b is smaller than 0");
        softAssert.assertAll();
//        assertTrue(a>b, "Ok, a is > b");
//     //   fail(b>a, "Failed as b is bigger than a"); It wants me to make a method Fail?
//        assertFalse(b>a,"Failed as b is bigger than a");
//        assertEquals((a-b),0);
    }

    @Test(dataProvider = "ABData", groups= "multi")
    public void multiplicationAB(int a, int b){
        System.out.println("The multiplication of the both values is: " + (a*b));
        assertTrue(a>=0, "Passed - a>0");
        assertFalse(b==0, "Failed b=0");
        assertEquals((a*b), 150);
    }

    @Test(dataProvider = "ABData", groups= "division")
    public void wholeDivisionAB(int a, int b){
        System.out.println("The whole division of the both values is: " + (a/b));
        assertFalse(a==0, "Failed - a=0, it will be always 0");
        assertFalse(b==0, "Failed b=0");
        assertTrue(a/b>=0, "Passed ");

    }

    @Test(dataProvider = "ABData", groups= "division")
    public void fractionAB(int a, int b){
        System.out.println("The fractional division of the both values is: " + (a%b));
        assertTrue(a>=0, "Passed - a>0");
        assertFalse(b==0, "Failed b=0");
        assertTrue(a%b>=0, "Passed ");

    }


}
