import org.testng.annotations.Test;

public class PriorityTests {
    @Test( priority = 0,alwaysRun = true,groups= {"exercises"})
    public void priorityFirstExample() {
        System.out.println("This is a priority First test");
    }

    @Test( priority = -1,groups= {"exercises"})
    public void priorityLastExample() {
        System.out.println("This is a priority Second test");
    }

}
