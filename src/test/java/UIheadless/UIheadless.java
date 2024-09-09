package UIheadless;

	import io.cucumber.java.en.Given;
	import io.cucumber.java.en.When;
	import io.cucumber.java.en.Then;
	import io.cucumber.java.Before;
	import io.cucumber.java.After;

	import java.time.Duration;

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

	    public WebDriver driver;
	    public WebDriverWait wait;

	    @Before
	    public void setup() {
	        EdgeOptions options = new EdgeOptions();
	        options.addArguments("--headless");  // Set the browser to headless mode
	        options.addArguments("--disable-gpu");  // Disable GPU hardware acceleration
	        //options.addArguments("--no-sandbox");  // Disable sandboxing

	        driver = new EdgeDriver(options);
	        driver.get("https://the-internet.herokuapp.com/");
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    }

	    @After
	    public void tearDown() {
	        driver.quit();
	    }

	    @Given("I am on the home page")
	    public void i_am_on_the_home_page() {
	        driver.get("https://the-internet.herokuapp.com/");
	    }

	    @Then("the page title should be {string}")
	    public void the_page_title_should_be(String title) {
	        wait.until(ExpectedConditions.titleContains(title));
	        Assert.assertTrue(driver.getTitle().contains(title));
	    }

	    @Given("I navigate to the login page")
	    public void i_navigate_to_the_login_page() {
	        WebElement linkelement = driver.findElement(By.linkText("Form Authentication"));
	        linkelement.click();
	        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
	    }

	    @When("I enter {string} and {string} and click login")
	    public void i_enter_and_and_click_login(String usernameInput, String passwordInput) {
	        WebElement username = driver.findElement(By.id("username"));
	        username.sendKeys(usernameInput);
	        WebElement password = driver.findElement(By.cssSelector("#password"));
	        password.sendKeys(passwordInput);
	        WebElement loginButton = driver.findElement(By.xpath("//*[@id='login']/button"));
	        loginButton.click();
	    }

	    @Then("I should see the flash message")
	    public void i_should_see_the_flash_message() {
	        WebElement flashMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
	        System.out.println("Flash message text: " + flashMessage.getText());
	    }

	    @Then("I should see the logout link")
	    public void i_should_see_the_logout_link() {
	        WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));
	        Assert.assertTrue(logoutLink.isDisplayed(), "Login was not successful.");
	    }

	    @Given("I am on the dropdown page")
	    public void i_am_on_the_dropdown_page() {
	        WebElement linkelementDrop = driver.findElement(By.cssSelector("#content > ul > li:nth-child(11) > a"));
	        linkelementDrop.click();
	    }

	    @When("I select the option with value {string}")
	    public void i_select_the_option_with_value(String value) {
	        WebElement dropdown = driver.findElement(By.xpath("//*[@id='dropdown']"));
	        Select select = new Select(dropdown);
	        select.selectByValue(value);
	    }

	    @Then("the selected option should be {string}")
	    public void the_selected_option_should_be(String value) {
	        WebElement dropdown = driver.findElement(By.xpath("//*[@id='dropdown']"));
	        Select select = new Select(dropdown);
	        WebElement selectedOption = select.getFirstSelectedOption();
	        Assert.assertEquals(selectedOption.getAttribute("value"), value, "The selected option is not correct.");
	    }
	}

