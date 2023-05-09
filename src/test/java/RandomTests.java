import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class RandomTests extends BaseTest {

    @Test
    public void actionMethods() throws InterruptedException {
        provideEmail("demo@class.com");
        providePassword("te$t$tudent");
        clickSubmit();
        Thread.sleep(2000);
        doubleClickChoosePlaylist();

//        hoverPlay();
//        contextClickFirstSong();
    }

    @Test
    public void listOfSongsWebElements() {
        provideEmail("demo@class.com");
        providePassword("te$t$tudent");
        clickSubmit();
        displayAllSongs();
    }
}
