package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CourseDetailsPage {
    WebDriver driver;
    WebDriverWait wait;

    public CourseDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));}
    private final By aboutCourseSection = By.xpath("//*[contains(text(),'عن') or contains(text(),'About')]");
    private final By courseDetailsSection = By.xpath("//div[contains(@class,'course') or contains(@class,'container')]");
    public boolean isAboutCourseDisplayed() {

        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000)");
            WebElement about = wait.until(ExpectedConditions.visibilityOfElementLocated(aboutCourseSection));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", about);
            return about.isDisplayed();}
        catch (TimeoutException e) {
            try {
                WebElement section = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(courseDetailsSection));
                return section.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
}