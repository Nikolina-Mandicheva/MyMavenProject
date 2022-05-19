import org.testng.annotations.Test;

public class MySimpleTest {

    @Test(alwaysRun = true,groups= {"exercises"})
    public void simpleTest(){
        System.out.println("My first test is executed");
    }

    @Test(alwaysRun = true,groups= {"exercises"})
    public void simpleTest2(){
        System.out.println("My second test is executed");
    }
}
