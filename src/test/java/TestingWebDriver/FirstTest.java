package TestingWebDriver;

import io.restassured.response.ValidatableResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class FirstTest {
    ChromeDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp(){

        // Headless implementation
        // ChromeOptions options=new ChromeOptions();
        //options.addArguments("--headless", "--disable-gpu","--ignore-certificate-errors");
        //driver=new Chromedriver(options);

        WebDriverManager.chromedriver().setup();

         driver = new ChromeDriver();
        driver.manage().window().maximize();

       // wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        //Implicit wait - waiting up to 20s before return no found element
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        //Explicit wait - waiting for a designated action to happen before to throw an error "element not found"
        wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        //page load time
       // driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
    }

    @Test
    public void loginTest() {

        // First approach - setting web driver manually via .exe file
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        // Second approach - Use web browser manager library (import+below line)
        driver.get("http://training.skillo-bg.com/posts/all");
        WebElement loginButton = driver.findElement(By.id("nav-link-login"));
        loginButton.click();

        // WebElement userNameField=driver.findElement(By.xpath("//input[@id='defaultLoginFormUsername']"));
        WebElement userNameField = driver.findElement(By.id("defaultLoginFormUsername"));
        userNameField.sendKeys("nikidm-testing-user");
        WebElement passwordField = driver.findElement(By.id("defaultLoginFormPassword"));
        passwordField.sendKeys("nikidm-testing-user");
        WebElement signInButton = driver.findElement(By.xpath("//*[@id='sign-in-button']"));
        signInButton.click();

        WebElement newPostButton = driver.findElement(By.xpath("//a[@id='nav-link-new-post']"));
        WebElement logoutButton = driver.findElement(By.xpath("//i[contains(@class,'sign-out')]"));
        //newPostButton.getText();
        Assert.assertEquals(newPostButton.getText(), "New post");
        Assert.assertTrue(newPostButton.isDisplayed());
        Assert.assertTrue(logoutButton.isDisplayed());
        //Thread.sleep(5000);
        //logoutButton.click(); ?
        driver.close();
        //driver.quit();
    }

    @Test
    public void dropDownList(){
        driver.get("https://www.mobile.bg/pcgi/mobile.cgi");
        WebElement consentButton= driver.findElement(By.xpath("//div[@class='fc-footer-buttons-container']//button[contains(@class,fc-primary-button)]/p[@class='fc-button-label'][1]"));
        WebElement cookieConsentButton= driver.findElement(By.xpath("//div[@class='fc-footer-buttons-container']//div[@class='fc-footer-buttons']/button[1]/p[1]"));
        cookieConsentButton.click();
        Select dropDownMarka=new Select(driver.findElement(By.xpath("//select[@name='marka']")));
        dropDownMarka.selectByVisibleText("Mitsubishi");
        Select dropDownModel=new Select(driver.findElement(By.xpath("//select[@name='model']")));
        dropDownModel.selectByVisibleText("Lancer");
        WebElement searchButton= driver.findElement(By.xpath("//input[@id='button2']"));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        //searchButton.click();

        WebElement paginationList=driver.findElement(By.xpath("//table[@class='tablereset']/tbody//span[@class='pageNumbersInfo']"));
        Assert.assertTrue(paginationList.isDisplayed());

        WebElement resultItem= driver.findElement(By.id("//table[@class='tablereset'][2]"));
        Assert.assertTrue(resultItem.isDisplayed());

    }

    @Test
    public void getPosts() {
        baseURI = "http://training.skillo-bg.com:3100";

        ValidatableResponse validatableResponse = given()
                .header("Content-Type", "application/json")
                .queryParam("skip", 0)
                .queryParam("take", 10)
                //.basePath("public")
//                .log()
//                .all()
                .when()
                .get("/posts")
                .then()
                // .body("user.id", equalTo(2467))
                .log()
                .all();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}


