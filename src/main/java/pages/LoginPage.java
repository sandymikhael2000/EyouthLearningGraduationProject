package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private final By loginLink = By.xpath("//a[contains(text(),'تسجيل الدخول')]");
    private final By emailField = By.xpath("//input[@id='email'] | //input[@type='email']");
    private final By passwordField = By.xpath("//input[@id='password']");
    private final By loginButton = By.xpath("//button[contains(text(),'تسجيل الدخول')]");
    private final By errorMessage = By.xpath("(//p[@class='text-center ms-1 mt-1.5 w-full text-sm text-[red]'])[1]");
    private final By usernameRequired = By.xpath("//*[contains(text(),'البريد الإلكتروني مطلوب')]");
    private final By passwordRequired = By.xpath("//*[contains(text(),'كلمة المرور مطلوبة')]");

    public void openLoginPage() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        loginBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
    }

    public void enterUsername(String username) {
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        email.clear();
        email.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        pass.clear();
        pass.sendKeys(password);
    }

    public void clickLogin() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        btn.click();
        try {
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.urlToBe("https://eyouthlearning.com/ar")
            ));
        } catch (TimeoutException ignored) {
        }

        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public String getErrorMessage() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(errorMessage)
            ).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public String getUsernameRequiredMessage() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(usernameRequired)
            ).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public String getPasswordRequiredMessage() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(passwordRequired)
            ).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }
}