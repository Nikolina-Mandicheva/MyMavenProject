import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.*;

public class AnnotationTests {
    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        System.out.println("This is before test update!");
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        System.out.println("This is after test update!");
    }
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        System.out.println("This is before method update!");
    }


    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        System.out.println("This is after method update!");
    }


    @Test(alwaysRun=true)
    public void sampleTestOne(){
        System.out.println("The test is first");
    }

    @Test(alwaysRun=true)
    public void sampleTestTwo(){
        System.out.println("The test is second");
    }


}
