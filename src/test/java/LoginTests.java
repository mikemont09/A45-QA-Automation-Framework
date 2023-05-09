import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends BaseTest {

//    @Test (dataProvider = "IncorrectLoginData", dataProviderClass = BaseTest.class, enabled = true, priority = 0, description = "Login with invalid email and valid password")
    public void loginInvalidEmailValidPasswordTest(String username, String password) throws InterruptedException {

        provideEmail("demo@class.com");
        providePassword("te$t$tudent");
        Thread.sleep(2000);
        clickSubmit();

        Assert.assertEquals(getThreadLocal().getCurrentUrl(), url); // https://bbb.testpro.io/
    }

    @Test (enabled = false, priority = 1, description = "Login with valid email and valid password")
    public void loginValidEmailPasswordTest() throws InterruptedException {
        provideEmail("demo@class.com");
        providePassword("te$t$tudent");
        Thread.sleep(2000);
        clickSubmit();
        Thread.sleep(2000);
        isAvatarDisplayed();
    }

    @Test (enabled = false, priority = 3, description = "Login with valid email and empty password")
    public void loginValidEmailEmptyPasswordTest() throws InterruptedException {
        provideEmail("demo@class.com");
        providePassword("");
        Thread.sleep(2000);
        clickSubmit();

        Assert.assertEquals(getThreadLocal().getCurrentUrl(), url);
    }
    public void isAvatarDisplayed() {
        WebElement avatarIcon = getThreadLocal().findElement(By.cssSelector("img[class='avatar']"));
        Assert.assertTrue(avatarIcon.isDisplayed());
//        Assert.assertEquals(avatarIcon.isDisplayed(), true);
    }

    //Page Object Model example
    @Test
    public void LoginValidEmailPasswordTest () {

        LoginPage loginPage = new LoginPage(getThreadLocal());
        HomePage homePage = new HomePage(getThreadLocal());

        loginPage.provideEmail("demo@class.com");
        loginPage.providePassword("te$t$tudent");
        loginPage.clickSubmit();

        Assert.assertTrue(homePage.getUserAvatar().isDisplayed());
    }
    @Test
    public void LoginInvalidEmailPasswordTest () {

        LoginPage loginPage = new LoginPage(getThreadLocal());

        loginPage.provideEmail("demo@class.coms");
        loginPage.providePassword("te$t$tudents");
        loginPage.clickSubmit();

        Assert.assertEquals(getThreadLocal().getCurrentUrl(), url);
    }
}
