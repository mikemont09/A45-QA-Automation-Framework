package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    //locators
    private By submitButtonLocator = By.cssSelector("button[type='submit']");
    private By emailField = By.cssSelector("input[type='email']");
    private By passwordField = By.cssSelector("input[type='password']");

    //constructor
    public LoginPage( WebDriver givenDriver) {
        super(givenDriver);
    }
    //page methods
    public void clickSubmitBtn() {
        driver.findElement(submitButtonLocator).click();
    }
    public void provideEmail(String email) {
        WebElement emailElement = driver.findElement(emailField);
        emailElement.click();
        emailElement.sendKeys(email);
    }
    public void providePassword(String password) {
        WebElement passwordElement = driver.findElement(passwordField);
        passwordElement.click();
        passwordElement.sendKeys(password);
    }
    public void login(){
        provideEmail("monty133@gmail.com");
        providePassword("te$t$tudent");
        clickSubmitBtn();
    }

}
