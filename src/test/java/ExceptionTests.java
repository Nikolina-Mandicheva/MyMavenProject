import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;


public class ExceptionTests {
//    @Test(groups = "example4", expectedExceptions = RuntimeException.class)
//    public void throwExceptionExample() {
//        System.out.println("Runtime Exception is triggered");
//        throw new RuntimeException("Runtime Exception");
//    }
//
//    @Test(expectedExceptions = { IOException.class }, expectedExceptionsMessageRegExp = "Pass Message test IOException")
//    public void exceptionTestOne() throws Exception {
//        throw new IOException("Pass Message test IOException");
//    }
//
//    @Test(expectedExceptions = { IOException.class }, expectedExceptionsMessageRegExp = "Pass Message test")
//    public void exceptionTestTwo() throws Exception {
//        throw new IOException("Fail Message test");                                 //Fails
//    }
//    @Test(expectedExceptions = { IOException.class })
//    public void exceptionTestThree() throws Exception {
//        throw new FileNotFoundException("Pass Message test FileNotFound as subclass of IOException");
//    }

    @Test(expectedExceptions = {IllegalArgumentException.class }, groups= {"exercises"})
    public void exceptionTestFour() throws Exception {
        String myString= new String("123");
        Integer.parseInt(myString);
        System.out.println(myString);
    }

}
