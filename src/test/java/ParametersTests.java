import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParametersTests {
    @Test(alwaysRun = true,groups= {"exercises"})
    @Parameters({ "parameter-name"})
    public void singleParamTest(String parameterName){
        System.out.println("Test singleParamTest suite param is: " + parameterName);
    }

    @Test(alwaysRun = true,groups= {"exercises"})
    @Parameters({ "parameter-name1" , "parameter-name2"})
    public void multiParamTest(String suiteParam, String testParam) {
        System.out.println("Test multiParamTest suite param is: " + suiteParam);
        System.out.println("Test multiParamTest param is: " + testParam);
    }

}
