package implementation;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Implementation {
    public static AppiumDriver driver;
    public static TouchAction action;
    public static List<WebElement> notes;
    public static int sizeOfListBefore;
    public static int sizeOfListAfter;
    public static String beforeText;
    public static String afterText;


    /**
     * Opens the ColorNote+Notepad application.
     *
     * @throws MalformedURLException if there is an issue with the URL.
     */


    public static void launchApplication() throws MalformedURLException {
        // Set up desired capabilities for the Android driver
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, ConfigReader.getProperty("appium.platformName"));
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigReader.getProperty("appium.deviceName"));
        capabilities.setCapability(MobileCapabilityType.APP, ConfigReader.getProperty("appium.appPath"));
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);

        try {
            // Initialize Appium driver and open the application
            URL url = new URL(ConfigReader.getProperty("appium.appUrl"));
            driver = new AndroidDriver(url, capabilities);
            driver.findElement(Locators.skip).click();

        } catch (MalformedURLException e) {
            // Handle the exception if there's an issue with the URL
            e.printStackTrace();
        }
    }


    /**
     * Opens ColorNote Notepad, navigating to the add text screen.
     * And verify if the AddText Button is present or not
     */

    public static void openAddTextScreen() {
        // Set implicit wait and perform necessary actions to open the text input screen
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement add = driver.findElement(Locators.addText);
            Assert.assertTrue(add.isDisplayed());
            add.click();
            driver.findElement(Locators.text).click();
        } catch (Exception e) {
            // Handle the exception if any element interaction fails
            e.printStackTrace();
        }
    }

    /**
     * Creates and saves multiple text notes.
     *
     * @throws InterruptedException if the thread is interrupted.
     */


    public static void createMultipleTextNotes() {
        try {
            for (int i = 1; i <= 3; i++) {
                driver.findElement(Locators.title).sendKeys("Title" + i);
                driver.findElement(Locators.notes).sendKeys("Text" + i);
                driver.findElement(Locators.save).click();
                driver.findElement(Locators.back).click();
                Thread.sleep(2000);
                if (i != 3) {
                    driver.findElement(Locators.addText).click();
                    driver.findElement(Locators.text).click();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    /**
     * Verify if the notes are created or not
     * Check the size of the list if it is not zero
     * Then assert passes else gives the error
     */

    public static void verifyNotesCreated(){
        notes = driver.findElements(Locators.listOfNotes);
        Assert.assertTrue(notes.size()!=0);
    }
    /**
     * Edit the existing note
     * tap on the note and change the title of that note
     * first clear the existing text and then write the new text
     */

    public static void editTheNote() {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            notes = driver.findElements(Locators.listOfNotes);
            sizeOfListBefore=notes.size();
            action = new TouchAction(driver);
            beforeText=driver.findElement(Locators.verifyTitle).getText();
            action.tap(ElementOption.element(notes.get(0))).perform();
            driver.findElement(Locators.edit).click();
            WebElement editTitle = driver.findElement(Locators.titleSection);
            editTitle.click();
            Thread.sleep(1000);
            editTitle.clear();
            editTitle.sendKeys("Title4");
            driver.findElement(Locators.save).click();
            driver.findElement(Locators.back).click();
            afterText=driver.findElement(Locators.verifyTitle).getText();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * verify the editNote
     * If the title gets changed then asser will pass
     */

    public static void verifyEdit(){
        Assert.assertTrue(!(beforeText.equals(afterText)));
    }
    /**
     * Delete the notes.
     * Long press a note and then select the delete option and click on it
     */

    public static void deleteNotes() {
        try {
            Thread.sleep(2000);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            LongPressOptions longPressOptions = new LongPressOptions();
            WebElement deleteThisNote = driver.findElement(Locators.deleteNote);
            longPressOptions.withElement(ElementOption.element(deleteThisNote)).withDuration(Duration.ofSeconds(2));
            action.longPress(longPressOptions).release().perform();
            driver.findElement(Locators.delete).click();
            driver.findElement(Locators.finalDelete).click();
            notes = driver.findElements(Locators.listOfNotes);
            sizeOfListAfter=notes.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * verify if the note is deleted of not
     * if true then the size of the list must be less than before
     * I have created three notes and after deletion the size must reduce to 2
     */

    public static void verifyDeletedNote() {
        Assert.assertTrue(sizeOfListBefore!=sizeOfListAfter);
    }


}
