package utilities;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WindowManager {
    private WebDriver driver;
    private WebDriver.Navigation navigate;
    WebDriverWait wait;
    public WindowManager(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        navigate= driver.navigate();
    }


    public void goBack()
    {

        navigate.back();

    }
}
