import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework18 extends BaseTest {
    @Test
    public void playASongTest() throws InterruptedException {
        login("monty133@gmail.com", "te$t$tudent");
        playSong();
        Assert.assertTrue(isSongPlaying());
        driver.quit();
    }
}
