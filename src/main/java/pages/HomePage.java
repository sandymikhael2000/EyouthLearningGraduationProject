package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    private final By searchBar = By.cssSelector("input[type='search']");
    private final By searchOutput = By.xpath("//div[@class='divide-border-card w-full divide-y p-2'][1]");
    private final By courseButton = By.xpath("//a[contains(.,'الدورات التدريبية')]");
    private final By registerButton = By.xpath("//a[contains(text(),'أنضم لنا الان')]");
    private final By facebookIcon = By.xpath("//*[name()='path' and contains(@d,'M26.0063 1')]");
    private final By linkedinIcon= By.xpath("//*[name()='path' and contains(@d,'M23.2914 3')]");
    private final By instagramIcon= By.xpath("//a[@href='https://www.instagram.com/eyouthlearning/']//*[name()='svg']");
    private final By loginLink=By.xpath("//a[contains(text(),'تسجيل الدخول')]");

    public void openSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBar)).click();}

    public void enterKeyword(String keyword) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
        input.sendKeys(keyword);}

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchOutput)).click();}

    public void clickOnCourses() {
        try {

            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(courseButton));
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

            try {
                element.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", element);}

        } catch (StaleElementReferenceException e) {

            WebElement refreshed = wait.until(
                    ExpectedConditions.refreshed(
                            ExpectedConditions.elementToBeClickable(courseButton)
                    )
            );
            refreshed.click();

        } catch (TimeoutException e) {
            System.out.println("Button 'الدورات التدريبية' not found or not clickable");
        }
    }

    public void clickRegister() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            try {
                element.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }
        } catch (TimeoutException e) {
            System.out.println("Button 'أبدأ الان' not found ");
        }
    }

    public void clickFacebookIcon() {
        WebElement fb = wait.until(ExpectedConditions.elementToBeClickable(facebookIcon));
        new Actions(driver).moveToElement(fb).click().perform();
    }

    public void clickLinkedinIcon() {
        WebElement lnk = wait.until(ExpectedConditions.elementToBeClickable(linkedinIcon));
        new Actions(driver).moveToElement(lnk).click().perform();
    }

    public void clickInstagramIcon() {
        WebElement insta = wait.until(ExpectedConditions.elementToBeClickable(instagramIcon));
        new Actions(driver).moveToElement(insta).click().perform();
    }

    public LoginPage openLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return new LoginPage(driver);
    }
}