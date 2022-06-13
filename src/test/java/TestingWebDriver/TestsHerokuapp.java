package TestingWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class TestsHerokuapp {
    ChromeDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();

       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        //Explicit wait - waiting for a designated action to happen before to throw an error "element not found"
        //wait=new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void ABTesting(){
        WebElement text= driver.findElement(By.xpath("//div/p"));
        Assert.assertTrue(text.isDisplayed());
    }

    @Test
    public void AddRemoveElements() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
        WebElement AddElementButton= driver.findElement(By.xpath("//button[@onclick]"));
       List<WebElement> elementsContainerChildren=driver.findElements(By.xpath("//div[@id='elements']/descendant::*"));
       //descendants=children which is  and each element of the descendants
        Assert.assertTrue(elementsContainerChildren.isEmpty());

        for (int i = 0; i <2 ; i++) {
            AddElementButton.click();
            elementsContainerChildren.add(driver.findElement(By.xpath("//div[@id='elements']/descendant::*")));

        }

        Assert.assertEquals(elementsContainerChildren.size(),2);


//How To make List elements to click? so to remove them and then to validate that list is empty again?
        for (int i = 2; i <0 ; i--) {
            elementsContainerChildren.get(i).click();
        }
        Thread.sleep(5000);
        Assert.assertTrue(elementsContainerChildren.isEmpty());


    }

    @Test
    public void basicAuth(){
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        WebElement text=driver.findElement(By.xpath("//div[@class='example']/p[text()]"));
        Assert.assertEquals(text.getText(),"Congratulations! You must have the proper credentials.");
    }

    @Test //think that does not work as expected
    public void dragAndDrop() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        WebElement elementA=driver.findElement(By.id("column-a"));
        WebElement elementB=driver.findElement(By.id("column-b"));

        Actions action=new Actions(driver);

       action.moveToElement(elementA).clickAndHold(elementA).moveToElement(elementB).release(elementB).build().perform();
       // action.dragAndDrop(elementA,elementB).perform();
        Thread.sleep(2000);

        WebElement switchedAtoB= driver.findElement(By.xpath("//div[@id='columns']/div[2]/header[text()='A']"));

       //Assert.assertEquals();

    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

}
