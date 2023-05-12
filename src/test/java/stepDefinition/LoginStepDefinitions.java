package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;

import java.time.Duration;

public class LoginStepDefinitions {
    WebDriver driver;
    WebDriverWait wait;
    @Before
    public void openBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @After
    public void iCloseTheBrowser(){
        driver.quit();
    }
    @Given("I open browser")
    public void launchBrowser(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Given("I open Login Page")
    public void openLoginPage(){
        driver.get("https://bbb.testpro.io");
    }
    @Then("I enter email {string}")
    public void enterEmail(String email) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.provideEmail(email);
    }
    @And("I enter password {string}")
    public void enterPassword(String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.providePassword(password);
    }
    @And("I submit")
    public void submit() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickSubmit();
    }
    @Then("I am logged in")
    public void userIsLoggedIn() {
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.avatar"))).isDisplayed());
    }
}
