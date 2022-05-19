import org.testng.annotations.*;

public class ChildClass extends InheritedAnnotations{
    @BeforeMethod (groups= {"exercises"})
    public void beforeChildMethod() {
        System.out.println("ChildClass's Before Test method");
    }

    @AfterMethod(groups= {"exercises"})
    public void afterChildMethod() {
        System.out.println("ChildClass's After Test method");
    }

    @BeforeClass(groups= {"exercises"})
    public void beforeChildClass() {
        System.out.println("ChildClass's Before Class method");
    }

    @AfterClass (groups= {"exercises"})
    public void afterChildClass() {
        System.out.println("ChildClass's After Class method");
    }

    @Test (groups= {"exercises"})
    public void testCase() {
        System.out.println("===== Executing actual test ======");
    }
}


