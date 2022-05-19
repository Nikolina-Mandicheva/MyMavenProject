import org.testng.annotations.*;

public class ParalelMethodTests {
    @BeforeMethod (alwaysRun = true,groups= {"exercises"})
    public void beforeMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("Before test-method. Thread id is: " + id);
    }

    @Test(alwaysRun = true,groups= {"exercises"})
    public void testMethodsOne() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method One. Thread id is: " + id);
    }

    @Test(alwaysRun = true,groups= {"exercises"})
    public void testMethodsTwo() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method Two. Thread id is: " + id);
    }
    @Test(alwaysRun = true,groups= {"exercises"})
    public void testMethodsThree() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method Three. Thread id is: " + id);
    }
    @Test(alwaysRun = true,groups= {"exercises"})
    public void testMethodsFour() {
        long id = Thread.currentThread().getId();
        System.out.println("Simple test-method Four. Thread id is: " + id);
    }

    @AfterMethod(alwaysRun = true,groups= {"exercises"})
    public void afterMethod() {
        long id = Thread.currentThread().getId();
        System.out.println("After test-method. Thread id is: " + id);
    }
}

