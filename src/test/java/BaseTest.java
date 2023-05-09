import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class BaseTest {
//    private static final ThreadLocal<WebDriver> THREAD_LOCAL = new ThreadLocal<>();

    public  ThreadLocal<WebDriver> THREAD_LOCAL = null;
    public  String url = "https://bbb.testpro.io/";
    private static String email;
    private WebDriver driver = null;
    public Actions actions;
    static WebDriverWait wait;



    @BeforeSuite
    static void setupClass() {

//        WebDriverManager.chromedriver().setup();
//        WebDriverManager.firefoxdriver().setup();

    }

    @DataProvider(name="IncorrectLoginData")
    public static Object[][] getDataFromDataProviders() {

        return new Object[][] {
                {"invalid@mail.com", "invalidPass"},
                {"demo@class.com", ""},
                {"", ""}
        };
    }

    public WebDriver getThreadLocal() {
        return THREAD_LOCAL.get();
    }
    @BeforeMethod
    public void setUpBrowser(@Optional String baseURL) throws MalformedURLException{
        THREAD_LOCAL = new ThreadLocal<>();
        driver = pickBrowser(System.getProperty("browser"));
        THREAD_LOCAL.set(driver);
//        THREAD_LOCAL.set(pickBrowser("browser"));
        wait = new WebDriverWait(getThreadLocal(),Duration.ofSeconds(10));
        getThreadLocal().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        getThreadLocal().manage().window().maximize();
        getThreadLocal().manage().deleteAllCookies();

        getThreadLocal().get(url);
        System.out.println("Browser setup by Thread " + Thread.currentThread().getId() + " and Driver reference is : " + getThreadLocal());
    }
    public void launchBrowser(String BaseURL) throws MalformedURLException {
        //      Added ChromeOptions argument below to fix websocket error
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
//        driver = new ChromeDriver(options);

//        driver = new FirefoxDriver();

        driver = pickBrowser(System.getProperty("browser"));
        //threadDriver = new ThreadLocal<>();
        //threadDriver.set(driver);

        getThreadLocal().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        //actions = new Actions(getDriver());
        url = BaseURL;
        getThreadLocal().get(BaseURL);
//        navigateToPage();
    }
    public WebDriver lambdaTest() throws MalformedURLException {
        String username = "";
        String authkey = "";
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "113.0");
        caps.setCapability("resolution", "1920x1080");
        caps.setCapability("build", "TestNG With Java");
        caps.setCapability("name", this.getClass().getName());
        caps.setCapability("plugin", "git-testing");
        return new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }
    @AfterMethod
    public void closeBrowser() {
//        THREAD_LOCAL.get().close();
        getThreadLocal().quit();
        THREAD_LOCAL.remove();
    }

    public WebDriver pickBrowser(String browser) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String gridURL = "http://192.168.1.160:4444";

        switch (browser){
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                optionsFirefox.addArguments("-private");
                return driver = new FirefoxDriver(optionsFirefox);
            case "MicrosoftEdge":
                WebDriverManager.edgedriver().setup();
                return driver = new EdgeDriver();
            case "grid-firefox":
                capabilities.setCapability("browserName", "firefox");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), capabilities);
            case "grid-chrome":
                capabilities.setCapability("browserName", "chrome");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), capabilities);
            case "grid-edge":
                capabilities.setCapability("browserName", "MicrosoftEdge");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), capabilities);
            case "cloud":
                return lambdaTest();
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                return driver = new ChromeDriver(options);
        }
    }

//    public void navigateToPage() {
//        getThreadLocal().get(url);
//    }

    public void provideEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void providePassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickSubmit() {
        WebElement submit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        submit.click();
    }

    public static void clickSaveButton() {
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-submit")));
        saveButton.click();
    }

    public static void provideProfileName(String randomName) {
        WebElement profileName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name='name']")));
        profileName.clear();
        profileName.sendKeys(randomName);
    }

    public static void provideCurrentPassword(String password) {
        WebElement currentPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name='current_password']")));
        currentPassword.clear();
        currentPassword.sendKeys(password);
    }

    public static String generateRandomName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void clickAvatarIcon() {
        WebElement avatarIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("img.avatar")));
        avatarIcon.click();
    }

    // hover
    public void hoverPlay() {
        WebElement play = getThreadLocal().findElement(By.cssSelector("[data-testid='play-btn']"));
        // move to element
        actions.moveToElement(play).perform();
    }

    // context click
    public void chooseAllSongsList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li a.songs")));
        getThreadLocal().findElement(By.cssSelector("li a.songs")).click();
    }

    public void contextClickFirstSong(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".all-songs tr.song-item:nth-child(1)")));
        WebElement firstSong = getThreadLocal().findElement(By.cssSelector(".all-songs tr.song-item:nth-child(1)"));
        // context click
        actions.contextClick(firstSong).perform();
    }

    public void displayAllSongs() {
        chooseAllSongsList();
    //add assertion
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".all-songs tr.song-item")));
        List<WebElement> songsList = getThreadLocal().findElements(By.cssSelector(".all-songs tr.song-item"));
        Assert.assertEquals(songsList.size(), 63);
    }

    // double click
    public void doubleClickChoosePlaylist() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".playlist:nth-child(3)")));
        // double click
        WebElement playlist = getThreadLocal().findElement(By.cssSelector(".playlist:nth-child(3)"));
        actions.doubleClick(playlist).perform();
    }
}