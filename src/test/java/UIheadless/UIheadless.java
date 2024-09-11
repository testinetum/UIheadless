package UIheadless;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.After;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class UIheadless {

    private static final Logger logger = LogManager.getLogger(UIheadless.class);

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void setup() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");  // Set the browser to headless mode
        options.addArguments("--disable-gpu");  // Disable GPU hardware acceleration
        options.addArguments("--no-sandbox");  // Disable sandboxing

        driver = new EdgeDriver(options);
        driver.get("https://the-internet.herokuapp.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.info("Setup completed and navigated to the home page.");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }

    @Given("I am on the home page")
    public void i_am_on_the_home_page() {
        driver.get("https://the-internet.herokuapp.com/");
        logger.info("Navigated to the home page.");
    }

    @Then("the page title should be {string}")
    public void the_page_title_should_be(String title) {
        wait.until(ExpectedConditions.titleContains(title));
        String pageTitle = driver.getTitle();
        logger.info("Page title is: " + pageTitle);
        Assert.assertTrue(pageTitle.contains(title), "Page title does not match.");
        logger.info("Page title matched expected value.");
    }

    @Given("I navigate to the login page")
    public void i_navigate_to_the_login_page() {
        WebElement linkelement = driver.findElement(By.linkText("Form Authentication"));
        linkelement.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        logger.info("Navigated to the login page.");
    }

    @When("I enter {string} and {string} and click login")
    public void i_enter_and_and_click_login(String usernameInput, String passwordInput) {
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys(usernameInput);
        WebElement password = driver.findElement(By.cssSelector("#password"));
        password.sendKeys(passwordInput);
        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login']/button"));
        loginButton.click();
        logger.info("Entered username and password and clicked login.");
    }

    @Then("I should see the flash message")
    public void i_should_see_the_flash_message() {
        WebElement flashMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        String flashText = flashMessage.getText();
        logger.info("Flash message text: " + flashText);
    }

    @Then("I should see the logout link")
    public void i_should_see_the_logout_link() {
        WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));
        Assert.assertTrue(logoutLink.isDisplayed(), "Login was not successful.");
        logger.info("Logout link is visible, login was successful.");
    }

    @Given("I am on the dropdown page")
    public void i_am_on_the_dropdown_page() {
        WebElement linkelementDrop = driver.findElement(By.cssSelector("#content > ul > li:nth-child(11) > a"));
        linkelementDrop.click();
        logger.info("Navigated to the dropdown page.");
    }

    @When("I select the option with value {string}")
    public void i_select_the_option_with_value(String value) {
        WebElement dropdown = driver.findElement(By.xpath("//*[@id='dropdown']"));
        Select select = new Select(dropdown);
        select.selectByValue(value);
        logger.info("Selected option with value: " + value);
    }

    @Then("the selected option should be {string}")
    public void the_selected_option_should_be(String value) {
        WebElement dropdown = driver.findElement(By.xpath("//*[@id='dropdown']"));
        Select select = new Select(dropdown);
        WebElement selectedOption = select.getFirstSelectedOption();
        String selectedValue = selectedOption.getAttribute("value");
        Assert.assertEquals(selectedValue, value, "The selected option is not correct.");
        logger.info("Selected option matches expected value: " + value);
    }
}
