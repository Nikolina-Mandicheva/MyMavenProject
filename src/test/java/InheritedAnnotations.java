import org.testng.annotations.*;

    public class InheritedAnnotations{

        @BeforeMethod(alwaysRun = true,groups= {"exercises"})
        public void beforeMethod() {
            System.out.println("BaseClass's Before Test method");
        }
        @AfterMethod(alwaysRun = true,groups= {"exercises"})
        public void afterMethod() {
            System.out.println("BaseClass's After Test method");
        }
        @BeforeClass(alwaysRun = true,groups= {"exercises"})
        public void beforeClass() {
            System.out.println("BaseClass's Before Class method");
        }
        @AfterClass(alwaysRun = true,groups= {"exercises"})
        public void afterClass() {
            System.out.println("BaseClass's After Class method");
        }
    }


