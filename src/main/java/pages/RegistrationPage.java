package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    WebDriver driver;
    WebDriverWait wait;
    private final By registrationForm = By.xpath("//a[contains(text(),'إنشاء حساب جديد')]") ;
    private final By nameField = By.id("name");
    private final By emailField = By.id("email");
    private final By countryField = By.xpath("(//button[starts-with(@aria-controls, 'radix-')])[1]");
    private final By cityField = By.xpath("(//button[starts-with(@aria-controls, 'radix-')])[2]");
    private final By genderField = By.xpath("(//button[starts-with(@aria-controls, 'radix-')])[3]");
    private final By numberField = By.id("phone");
    private final By passwordField = By.id("password");
    private final By confirmationPasswordField = By.id("confirm_password");
    private final By agreeButton = By.id("terms");
    private final By createAccountButton = By.cssSelector("button[type='submit']");
    private final By nameRequiredMessage = By.xpath("//p[contains(text(),'الاسم مطلوب')]");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public boolean isFormDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(registrationForm));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    public void clickOnRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(registrationForm)).click();
    }

    public void setUsername(String userName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        element.clear();
        element.sendKeys(userName);
    }

    public void setEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        element.clear();
        element.sendKeys(email);
    }

    public void selectCountry() {
        wait.until(ExpectedConditions.elementToBeClickable(countryField)).click();

        By countryOption = By.xpath("//*[contains(@role, 'option')]//span[contains(text(), 'مصر')]");

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(countryOption));
        jsClick(option);
    }

    public void selectCity() {
        wait.until(ExpectedConditions.elementToBeClickable(cityField)).click();

        By cityOption = By.xpath("//*[contains(@role, 'option')]//span[contains(text(), 'أسيوط')]");

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(cityOption));
        jsClick(option);
    }

    public void selectGender() {
        wait.until(ExpectedConditions.elementToBeClickable(genderField)).click();

        By genderOption = By.xpath("//*[contains(@role, 'option')]//span[contains(text(), 'انثى')]");

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(genderOption));
        jsClick(option);
    }

    public void setPhoneNumber() {
        WebElement phoneInput = wait.until(ExpectedConditions.elementToBeClickable(numberField));
        phoneInput.clear();
        phoneInput.sendKeys("01205043898");
    }

    public void setPassword() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.sendKeys("Route@1234");
    }

    public void setConfirmationPassword() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationPasswordField));
        element.sendKeys("Route@1234");
    }

    public void clickAgree() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(agreeButton));

        scrollToElement(checkbox);
        jsClick(checkbox);
    }

    public void clickCreateAccount() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));

        scrollToElement(button);

        try {
            button.click();
        } catch (ElementClickInterceptedException e) {
            jsClick(button);
        }
    }

    public String validateMessageName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameRequiredMessage)).getText();
    }

    public String getUrl() {
        wait.until(ExpectedConditions.urlContains("register"));
        return driver.getCurrentUrl();
    }


    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element);
    }

}