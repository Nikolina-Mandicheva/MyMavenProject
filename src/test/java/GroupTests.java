import org.testng.annotations.Test;

public class GroupTests {
    @Test(groups = "example2", description = "This is my first test case with testng description"
            , dependsOnGroups = "example4")
    public void dependsOnGroupExample() {
        System.out.println("This is a depends on test example  groups = \"example2\", dependsOnGroups = example4 ");
    }

    @Test(groups = "example4", dependsOnMethods = "myFirstTestNGTestCase")
    public void dependsOnMethodExample() {
        System.out.println("This is a depends on test example, from group=example4");
    }


    @Test(alwaysRun=true, dependsOnGroups = "mygroup")
    public void myFirstTestNGTestCase() {
        System.out.println("This is my first testNG test case, depends on mygroup");
    }

    @Test(groups = { "mygroup", "example2"})
    public void groupExample() {
        System.out.println("This is a test with two groups ");
    }


}
