package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CoursesPage {

    WebDriver driver;
    WebDriverWait wait;

    public CoursesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private final By courseCards    = By.xpath("//a[contains(@href,'course')]");
    private final By courseImage    = By.xpath(".//img");
    private final By courseTitle    = By.xpath(".//h3 | .//h2 | .//h4 | .//p[contains(@class,'title')]");
    private final By instructorName = By.xpath("//div//div//div[1]//h6[1]");
    private final By subscribeButton = By.xpath(".//button[contains(text(),'اشترك الآن')]");

    public List<WebElement> getCourseCards() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
        for (int i = 0; i < 3; i++) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000)");
        }
        List<WebElement> courses = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(courseCards));
        System.out.println("Courses found: " + courses.size());
        return courses;
    }
    public WebElement getCourseTitle(WebElement card) {
        try {
            return card.findElement(courseTitle);
        } catch (Exception e) {
            return null;
        }
    }
    public String getCourseTitleText(WebElement card) {
        try {
            WebElement el = getCourseTitle(card);
            if (el == null) return "";
            return el.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
    public WebElement getInstructorName(WebElement card) {
        try {
            return card.findElement(instructorName);
        } catch (Exception e) {
            return null;
        }
    }
    public String getInstructorNameText(WebElement card) {
        try {
            WebElement el = getInstructorName(card);
            return el != null ? el.getText().trim() : "";
        } catch (Exception e) {
            return "";
        }
    }
    public WebElement getCourseImage(WebElement card) {
        try {
            return card.findElement(courseImage);
        } catch (Exception e) {
            return null;
        }
    }
    public WebElement getSubscribeButton(WebElement card) {
        try {
            WebElement parent = (WebElement) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].parentElement;", card);

            List<WebElement> buttons = parent.findElements(subscribeButton);

            for (WebElement btn : buttons) {
                if (btn.isDisplayed()) {
                    return btn;
                }
            }
            return null;

        } catch (Exception e) {
            return null;
        }
    }


    public WebElement getFirstCourseWithSubscribe(List<WebElement> courses) {
        for (WebElement course : courses) {
            WebElement btn = getSubscribeButton(course);
            if (btn != null) {
                return course;
            }
        }
        return null;
    }
    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }
    public void clickElement(WebElement element) {
        try {
            element.click();
        } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
    public List<WebElement> getValidCourseCards() {
        List<WebElement> validCards = new ArrayList<>();

        List<WebElement> allCards = getCourseCards();
        int total = allCards.size();

        for (int i = 0; i < total; i++) {
            try {
                List<WebElement> freshCards = driver.findElements(courseCards);
                if (i >= freshCards.size()) break;
                WebElement card = freshCards.get(i);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", card);
                WebElement image = getCourseImage(card);
                String title = getCourseTitleText(card);

                if (image != null && !title.isEmpty()) {
                    validCards.add(card);
                }

            } catch (Exception e) {
                System.out.println("Skipping card at index " + i + ": " + e.getMessage());
            }
        }

        System.out.println("Valid Courses: " + validCards.size());
        return validCards;
    }
}