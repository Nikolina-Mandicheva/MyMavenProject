package TestingWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

public class TestsHerokuapp {
    ChromeDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    Actions actions;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
//Implicit - waits designated time before throwing an error
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        //Explicit wait - waiting for a designated action to happen before to throw an error "element not found"
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        actions = new Actions(driver);

        js = (JavascriptExecutor) driver;
    }

    @Test
    public void ABTesting() {
        WebElement text = driver.findElement(By.xpath("//div/p"));
        Assert.assertTrue(text.isDisplayed());
    }

    @Test
    public void AddRemoveElements() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
        WebElement AddElementButton = driver.findElement(By.xpath("//button[@onclick]"));
        List<WebElement> elementsContainerChildren = driver.findElements(By.xpath("//div[@id='elements']/descendant::*"));
        //descendants=children which is  and each element of the descendants
        Assert.assertTrue(elementsContainerChildren.isEmpty());

        for (int i = 0; i < 2; i++) {
            AddElementButton.click();

        }
        elementsContainerChildren = driver.findElements(By.xpath("//div[@id='elements']/button[@onclick='deleteElement()']"));

        Assert.assertEquals(elementsContainerChildren.size(), 2);


//How To make List elements to click? so to remove them and then to validate that list is empty again? Below:
        List<WebElement> deleteButtonsList = driver.findElements(By.xpath("//div[@id='elements']/button[@onclick='deleteElement()']"));
        for (WebElement element : deleteButtonsList
        ) {
            element.click();

        }

        deleteButtonsList = driver.findElements(By.xpath("//div[@id='elements']/button[@onclick='deleteElement()']"));
        Assert.assertEquals(deleteButtonsList.size(), 0);


    }

    @Test
    public void basicAuth() {
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        WebElement text = driver.findElement(By.xpath("//div[@class='example']/p[text()]"));
        Assert.assertEquals(text.getText(), "Congratulations! You must have the proper credentials.");
    }

    @Test //think that does not work as expected
    public void dragAndDrop() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        WebElement elementA = driver.findElement(By.id("column-a"));
        WebElement elementB = driver.findElement(By.id("column-b"));

        Actions actions = new Actions(driver);

        actions.moveToElement(elementA).clickAndHold(elementA).moveToElement(elementB).release(elementB).build().perform();
        // action.dragAndDrop(elementA,elementB).perform();


        WebElement switchedAtoB = driver.findElement(By.xpath("//div[@id='columns']/div[2]/header[text()='A']"));

        //Assert.assertEquals();

    }

    @Test
      //canvas - image recognition - Sikuly library
    public void challengingDOM() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/challenging_dom");
        //Verify there is table with columns
        List<WebElement> tableColumnFields = driver.findElements(By.xpath("//div[@class='large-10 columns']/table//tr/th"));
        Assert.assertNotNull(tableColumnFields.size());
        Assert.assertFalse(tableColumnFields.isEmpty());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='large-10 columns']/canvas")).isDisplayed());

        //Verify there is at least one table row
        List<WebElement> tableRows = driver.findElements(By.xpath("//div[@class='large-10 columns']//tbody/tr"));
        int i = tableRows.size();
        Assert.assertTrue(tableRows.get(0).isDisplayed());
        Assert.assertTrue(tableRows.get(i - 1).isDisplayed());

        WebElement rowEditButton = driver.findElement(By.xpath("//div[@class='large-10 columns']//tbody/tr/td/a[text()='edit']"));
        WebElement rowDeleteButton = driver.findElement(By.xpath("//div[@class='large-10 columns']//tbody/tr/td/a[text()='delete']"));
        Assert.assertTrue(rowEditButton.isDisplayed());
        Assert.assertTrue(rowDeleteButton.isDisplayed());

        // How to verify that on click on button, the Answer is changed? JS?
        WebElement buttonBlue = driver.findElement(By.xpath("//div[@class='large-2 columns']/a[@class='button']"));
        WebElement buttonRed = driver.findElement(By.xpath("//div[@class='large-2 columns']/a[@class='button alert']"));
        WebElement buttonGreen = driver.findElement(By.xpath("//div[@class='large-2 columns']/a[@class='button success']"));


    }

    @Test
    public void checkBoxes() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        // The case generally works along with the Assertions, but in debug even the box is clicked get its state as false?

        WebElement checkbox1 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[2]"));

        boolean checkboxState1 = checkbox1.isSelected();
        boolean checkboxState2 = checkbox2.isSelected();


        checkbox1.click();
        boolean checkboxStateAfterClick1 = checkbox1.isSelected();
        Assert.assertNotEquals(checkboxState1, checkboxStateAfterClick1);

        checkbox2.click();
        boolean checkboxStateAfterClick2 = checkbox1.isSelected();
        Assert.assertNotEquals(checkboxState2, checkboxStateAfterClick2);



    }

    @Test
    public void contextMenu()  {
        driver.get("https://the-internet.herokuapp.com/context_menu");


        WebElement contextBox = driver.findElement(By.id("hot-spot"));
        actions.contextClick(contextBox).perform();
        Alert alert = driver.switchTo().alert();


        String alertText = alert.getText();
        Assert.assertEquals(alertText, "You selected a context menu");
        alert.dismiss();

    }

    @Test
    //Handle the case when the page does not reload
    public void disappearingElements() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/disappearing_elements");

        List<WebElement> buttonList=driver.findElements(By.xpath("//ul/li"));
        int size= buttonList.size();
        System.out.println("Buttons are: "+ size);

        driver.navigate().refresh();

       buttonList=driver.findElements(By.xpath("//ul/li"));
        int size1= buttonList.size();
        System.out.println("New buttons number is: " +size1);

        try {
            if (size1 > size) {

                Assert.assertTrue(driver.findElement(By.xpath("//ul/li/a[text()='Gallery']")).isDisplayed());
            } else {
                Assert.assertNotEquals((buttonList.get(size1 - 1).getText()), "Gallery");
            }
        }
        catch (Exception e ){

            System.out.println("the reloaded page did not change the button appearance");
        }


    }

    @Test

    public void dropdown()  {
        driver.get("https://the-internet.herokuapp.com/dropdown");

        WebElement dropdown=driver.findElement(By.id("dropdown"));
        Select select = new Select(dropdown);
        WebElement defaultDropdownOption=select.getFirstSelectedOption();
        String defaultDropdownOptionText=select.getFirstSelectedOption().getText();
        Assert.assertTrue(defaultDropdownOption.isDisplayed());
        Assert.assertEquals(defaultDropdownOptionText, "Please select an option");


        select.selectByIndex(1);
                Assert.assertFalse(defaultDropdownOption.isSelected());
        String option1DropdownSelection=select.getFirstSelectedOption().getText();
        Assert.assertEquals(option1DropdownSelection, "Option 1");

        select.selectByValue("2");
        String option2DropdownSelection=select.getFirstSelectedOption().getText();
        Assert.assertEquals(option2DropdownSelection, "Option 2");





//        Without using Select
//        ropdown.click();
//
//        WebElement dropdownList= driver.findElement(By.xpath("//select[@id='dropdown']/option[position()>1]"));
//        Assert.assertTrue(dropdownList.isDisplayed());
//
//        dropdownList.click();
//
//        WebElement dropdownOptionSelected= driver.findElement(By.xpath("//select[@id='dropdown']/option[@selected='selected']"));
//        Assert.assertTrue(dropdownOptionSelected.isSelected());
//        Assert.assertTrue(dropdownOptionSelected.isDisplayed());

           }

    @Test // USE Click here! and get the text which to compare after reload

    public void dynamicContent() throws InterruptedException {
        //After click here is selected/clicked once, I cannot click it again?

        driver.get("https://the-internet.herokuapp.com/dynamic_content");
        WebElement clickHereLink= driver.findElement(By.xpath("//a[@href='/dynamic_content?with_content=static']"));
        clickHereLink.click();
        Thread.sleep(5000);

        WebElement firstRowImage=driver.findElement(By.xpath("//div[@class='large-2 columns']/img[contains(@src,'Avatar-3')]"));
        WebElement firstRowText=driver.findElement(By.xpath("//div[contains(text(),' Accusantium')]"));

        WebElement secondRowImage=driver.findElement(By.xpath("//div[@class='large-2 columns']/img[contains(@src,'Avatar-6')]"));
        WebElement secondRowText=driver.findElement(By.xpath("//div[contains(text(),' Omnis')]"));

        WebElement thirdRowImage=driver.findElement(By.cssSelector("#content > div > div > div > div:nth-of-type(3) > div:nth-of-type(1)>img"));
        WebElement thirdRowText=driver.findElement(By.cssSelector("#content > div > div > div > div:nth-of-type(3) > div:nth-of-type(2)"));

        SoftAssert softAssertSuite=new SoftAssert();
        softAssertSuite.assertTrue(firstRowImage.isDisplayed());
        softAssertSuite.assertTrue(firstRowText.isDisplayed());
        softAssertSuite.assertTrue(secondRowImage.isDisplayed());
        softAssertSuite.assertTrue(secondRowText.isDisplayed());
        softAssertSuite.assertTrue(thirdRowImage.isDisplayed());
        softAssertSuite.assertTrue(thirdRowText.isDisplayed());
        softAssertSuite.assertAll();

        String firstElementContent=firstRowText.getText();
        String secondElementContent=secondRowText.getText();
        String thirdElementContent=thirdRowText.getText();

//        wait.until(ExpectedConditions.visibilityOf(thirdRowText));
//        Assert.assertTrue(clickHereLink.isDisplayed());
//        clickHereLink.click();

        driver.navigate().refresh();
        WebElement firstRowImageReloaded=driver.findElement(By.xpath("//div[@class='large-2 columns']/img[contains(@src,'Avatar-3')]"));
        WebElement firstRowTextReloaded=driver.findElement(By.xpath("//div[contains(text(),' Accusantium')]"));

        WebElement secondRowImageReloaded=driver.findElement(By.xpath("//div[@class='large-2 columns']/img[contains(@src,'Avatar-6')]"));
        WebElement secondRowTextReloaded=driver.findElement(By.xpath("//div[contains(text(),' Omnis')]"));

        WebElement thirdRowImageReloaded=driver.findElement(By.cssSelector("#content > div > div > div > div:nth-of-type(3) > div:nth-of-type(1)>img"));
        WebElement thirdRowTextReloaded=driver.findElement(By.cssSelector("#content > div > div > div > div:nth-of-type(3) > div:nth-of-type(2)"));

        String firstElementContentReloaded=firstRowTextReloaded.getText();
        String secondElementContentReloaded=secondRowTextReloaded.getText();
        String thirdElementContentReloaded=thirdRowTextReloaded.getText();

        Assert.assertEquals(firstElementContent,firstElementContentReloaded);
        Assert.assertEquals(secondElementContent,secondElementContentReloaded);
        Assert.assertNotEquals(thirdElementContent,thirdElementContentReloaded);



    }

    @Test
    public void dynamicControls() {
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

// assert that the dynamic checkbox is present after loading the page
        WebElement checkbox = driver.findElement(By.xpath("//div[@id='checkbox']"));
        Assert.assertTrue(checkbox.isDisplayed());

        //click the remove button and wait until the loading animation is gone
        WebElement removeButton = driver.findElement(By.xpath("//form[@id='checkbox-example']/button[text()='Remove']"));
        removeButton.click();

        WebElement loadingAnimation = driver.findElement(By.xpath("//div[@id='loading']"));
        wait.until(ExpectedConditions.invisibilityOf(loadingAnimation)); //чака анимацията да изчезне

        //Example - fluent wait example
        Wait<WebDriver> waitFluent= new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        WebElement loadingBar=wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(By.xpath("//div[@id='loading']"));
            }
        });

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='checkbox']")));
        //Assert.assertFalse(checkbox.isDisplayed()); //verifying that the checkbox is not displayed
        Assert.assertEquals(driver.findElement(By.id("message")).getText(), "It's gone!");



    }

    @Test
    public void dynamicLoading() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        By startButton = By.xpath("//div[@id='start']/button");
        By helloWorldText = By.xpath("//div[@id='finish']");

        WebElement startButtonWebElement = driver.findElement(startButton);
        startButtonWebElement.click();
        WebElement loadingAnimation= driver.findElement(By.xpath("//div[@id='loading']"));
        wait.until(ExpectedConditions.invisibilityOf(loadingAnimation));
        WebElement helloWorldTextWebElement = driver.findElement(helloWorldText);
        Assert.assertTrue(helloWorldTextWebElement.isDisplayed());

    }

    @Test
    public void floatingMenu() {
        driver.get("https://the-internet.herokuapp.com/floating_menu");
        //Create JS Executor - we will need it for scrolling
        //Assert the buttons are there
        WebElement homeButton = driver.findElement(By.xpath("//div[@id='menu']//a[text()='Home']"));
        Assert.assertTrue(homeButton.isDisplayed());
        WebElement newsButton= driver.findElement(By.xpath("//div[@id='menu']//a[text()='News']"));
        Assert.assertTrue(newsButton.isDisplayed());

        // SCROLL THE PAGE - scroll down
        js.executeScript("window.scrollBy(0,2000)");

        //Assert the buttons are Still there  after the scroll
        Assert.assertTrue(homeButton.isDisplayed());
        Assert.assertTrue(newsButton.isDisplayed());

        // SCROLL THE PAGE - scroll up
        js.executeScript("window.scrollBy(0,-1000)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='menu']//a[text()='Home']")));
        //Assert the buttons are Still there  after the scroll Up
        Assert.assertTrue(homeButton.isDisplayed());
        Assert.assertTrue(newsButton.isDisplayed());

        //click home button as using js
        js.executeScript("arguments[0].click()", homeButton);
    }


    @Test
    public void hovers() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/hovers");
        WebElement defaultAvatar1= driver.findElement(By.xpath("//div[@class='figure']/img"));
        Assert.assertTrue(defaultAvatar1.isDisplayed());

        WebElement avatar1Details= driver.findElement(By.xpath("//div[@class='figure']//div/h5"));
        Assert.assertFalse(avatar1Details.isDisplayed());


        actions.moveToElement(defaultAvatar1).perform();
        Assert.assertTrue(defaultAvatar1.isDisplayed());
        Assert.assertFalse(avatar1Details.isDisplayed());
        Assert.assertEquals(avatar1Details.getText(), "name: user1");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='figure']//a[text()='View profile']")).isDisplayed());

        WebElement defaultAvatar2= driver.findElement(By.xpath("//div[@class='figure'][2]/img"));
        Assert.assertTrue(defaultAvatar2.isDisplayed());

        WebElement avatar2Details= driver.findElement(By.xpath("//div[@class='figure'][2]//div/h5"));
        Assert.assertFalse(avatar2Details.isDisplayed());


        actions.moveToElement(defaultAvatar2).perform();
        Assert.assertEquals(avatar2Details.getText(), "name: user2");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='figure'][2]//a[text()='View profile']")).isDisplayed());

        WebElement defaultAvatar3= driver.findElement(By.xpath("//div[@class='figure'][3]/img"));
        Assert.assertTrue(defaultAvatar3.isDisplayed());

        WebElement avatar3Details= driver.findElement(By.xpath("//div[@class='figure'][3]//div/h5"));
        Assert.assertFalse(avatar3Details.isDisplayed());


        actions.moveToElement(defaultAvatar3).perform();
        Assert.assertEquals(avatar3Details.getText(), "name: user3");
        Thread.sleep(5000);
    }

    @Test
    public void multipleWindows(){
        driver.get("https://the-internet.herokuapp.com/windows");
        //use getWindowHandle in for-each so it will switch between the windows
        WebElement clickHereButton= driver.findElement(By.xpath("//a[@href='/windows/new']"));
        Assert.assertTrue(clickHereButton.isDisplayed());
        clickHereButton.click();

        for (String winHandle: driver.getWindowHandles()
             ) {
            driver.switchTo().window(winHandle);
            driver.getTitle();
            if(driver.getTitle()=="New Window"){
                   Assert.assertTrue(driver.findElement(By.xpath("//h3[text()='New Window']")).isDisplayed());
                break;
            }


        }

   }


    @Test
    //TO DO
    public void switchWindows() {

    }

    @Test
    public void redirectLink(){
        driver.get("https://the-internet.herokuapp.com/redirector");
        WebElement clickHereButton= driver.findElement(By.id("redirect"));
        clickHereButton.click();
        Assert.assertEquals(driver.getCurrentUrl(),"https://the-internet.herokuapp.com/status_codes");

    }


    @Test
    public void iFrames() {
        driver.get("https://the-internet.herokuapp.com/iframe");
        //step into the frame in which the web element is located
        //TO DO
        driver.switchTo().frame("mce_0_ifr");
        //now we are in the i-frame where can correctly take the needed web element
        WebElement textelement = driver.findElement(By.xpath("//body[@id='tinymce']//p"));
        textelement.clear();
        textelement.sendKeys("TEST");

        //get back to the main doc, getting out of the i-frame
        driver.switchTo().defaultContent();
        //now as in main doc we can validate web-elements outside of the frame
        WebElement headerText = driver.findElement(By.xpath("//div[@class='example]/h3'"));
    }

    @Test
    public void nested_iFrames() {
        driver.get("https://the-internet.herokuapp.com/nested_frames");
        //step into the frame in which the web element is located
        //TO DO
        driver.switchTo().frame("frame-top").switchTo().frame("frame-left");
        //now we are in the i-frame where can correctly take the needed web element
        WebElement leftFrameBodyText = driver.findElement(By.xpath("//body"));
        Assert.assertEquals(leftFrameBodyText.getText(), "LEFT");
//switching to parent frame in order to get all children accessible
        //driver.switchTo().parentFrame();

        //get back to the main frame which in nested frame is the top frame, in our case should go back to frame-top
        driver.switchTo().defaultContent();
        //as getting back to default content should acces to the sub-frame by flow from top one
        driver.switchTo().frame("frame-top").switchTo().frame("frame-middle");
        WebElement middleFrameBodyText = driver.findElement(By.xpath("//body"));
        Assert.assertEquals(middleFrameBodyText.getText(), "MIDDLE");
    }


    @AfterMethod
    public void tearDown() {

        driver.quit();
    }

}
