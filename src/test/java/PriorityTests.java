import org.testng.annotations.Test;

public class PriorityTests {
    @Test( priority = 0)
    public void priorityFirstExample() {
        System.out.println("This is a priority First test");
    }

    @Test( priority = -1)
    public void priorityLastExample() {
        System.out.println("This is a priority Second test");
    }

}
