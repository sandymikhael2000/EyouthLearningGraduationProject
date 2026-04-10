package base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.*;
import utilities.WindowManager;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected HomePage homePage;
    protected SearchPage searchPage;
    protected CoursesPage coursesPage;
    protected CourseDetailsPage courseDetailsPage;
    protected RegistrationPage registrationPage;
    protected pages.LoginPage loginPage;

    ;

    protected String baseUrl = "https://eyouthlearning.com/ar";
    @BeforeMethod
    public void setup() {

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");

        driver = new EdgeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(baseUrl);

        homePage = new HomePage(driver);
        searchPage=new SearchPage(driver);
        coursesPage=new CoursesPage(driver);
        courseDetailsPage =new CourseDetailsPage(driver);
        registrationPage=new RegistrationPage(driver);
        loginPage =new pages.LoginPage(driver);


    }
    public WindowManager getWindowManger()
    {
        return new WindowManager(driver);
    }

    public void clickIconAndReturnHome(By icon) {
        String mainWindow = driver.getWindowHandle();
        driver.findElement(icon).click();
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        driver.close();
        driver.switchTo().window(mainWindow);
    }
    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}