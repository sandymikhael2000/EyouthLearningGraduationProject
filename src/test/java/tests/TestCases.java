package tests;
import base.BaseTest;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Set;
import static org.testng.Assert.*;

@Listeners({AllureTestNg.class})
public class TestCases extends BaseTest {
    @Test(priority = 1)
    public void searchWithValidKeyword() {
        String keyword = "كيف تنضم الي البنك";
        homePage.openSearch();
        homePage.enterKeyword(keyword);
        homePage.clickSearch();

        assertTrue(searchPage.getTitle().contains("كيف تنضم إلى البنك؟"), "You are in the wrong page");
        getWindowManger().goBack();
    }

    @Test(priority = 2)
    public void openCourseDetails() {
        homePage.clickOnCourses();
        coursesPage.getCourseCards();
        assertTrue(courseDetailsPage.isAboutCourseDisplayed(),
                "About course section is NOT displayed");
        getWindowManger().goBack();

    }

    @Test(priority = 3)
    public void openRegistrationPage()
    {
        homePage.clickRegister();
        assertTrue(registrationPage.isFormDisplayed(),
                "Form is not displayed");

        registrationPage.clickOnRegistration();
        wait.until(ExpectedConditions.urlContains("/register"));

        assertTrue(registrationPage.getCurrentUrl().contains("/register"),
                "URL does not contain /register after clicking registration button");

    }
    @Test(priority = 4)
    public void registrationWithEmptyUserName(){
        homePage.clickRegister();
        registrationPage.clickOnRegistration();
        registrationPage.setUsername("");
        registrationPage.setEmail("sandymikhael2000@gmail.com");
        registrationPage.selectCountry();
        registrationPage.selectCity();
        registrationPage.selectGender();
        registrationPage.setPhoneNumber();
        registrationPage.setPassword();
        registrationPage.setConfirmationPassword();
        registrationPage.clickAgree();
        registrationPage.clickCreateAccount();
        assertEquals(registrationPage.validateMessageName(),"الاسم مطلوب","message is incorrect");
    }

    @Test(priority = 5)
    public void loginWithInvalidCredentials() {

        loginPage.openLoginPage();

        String invalidUsername = "sandy@mmm";
        String invalidPassword = "wrongpass123";

        loginPage.enterUsername(invalidUsername);
        loginPage.enterPassword(invalidPassword);
        loginPage.clickLogin();

        String expectedError = "لم يتم العثور على حساب نشط للبيانات المقدمة";
        String actualError = loginPage.getErrorMessage();
        assertTrue(actualError.contains(expectedError),
                "Error message not displayed or incorrect. Actual: " + actualError);
        getWindowManger().goBack();
    }



    @Test(priority = 6)
    public void loginWithEmptyFields() {
        loginPage.openLoginPage();
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLogin();

        assertTrue(loginPage.getUsernameRequiredMessage().contains("البريد الإلكتروني مطلوب"), "رسالة اسم المستخدم المطلوبة غير موجودة " );
        assertTrue(  loginPage.getPasswordRequiredMessage().contains("كلمة المرور مطلوبة"), "رسالة كلمة المرور المطلوبة غير موجودة " );
    }

    @Test(priority = 7)
    public void endToEndSubscribeCourse() {
        loginPage.openLoginPage();
        loginPage.enterUsername("sandymikhael2000@gmail.com");
        loginPage.enterPassword("Route@1234");
        loginPage.clickLogin();
        homePage.clickOnCourses();

        List<WebElement> validCourses = coursesPage.getValidCourseCards();
        assertTrue(validCourses.size() > 0, "No valid courses found!");

        WebElement firstCourse = coursesPage.getFirstCourseWithSubscribe(validCourses);
        assertNotNull(firstCourse, "No course with subscribe button found!");

        String courseName = coursesPage.getCourseTitleText(firstCourse).trim();
        coursesPage.scrollIntoView(firstCourse);
        WebElement subscribeBtn = coursesPage.getSubscribeButton(firstCourse);
        assertNotNull(subscribeBtn, "Subscribe button became null after scroll!");

        coursesPage.clickElement(subscribeBtn);
        homePage.clickOnCourses();

        List<WebElement> updatedCourses = coursesPage.getValidCourseCards();
        boolean courseFound = updatedCourses.stream()
                .anyMatch(card -> {
                    String title = coursesPage.getCourseTitleText(card).trim();
                    return title.contains(courseName) || courseName.contains(title);
                });

        assertTrue(courseFound, "Subscribed course not found in My Courses: " + courseName);
    }

    @Test(priority = 8)
    public void verifyFacebookLink()
    {
        homePage.clickFacebookIcon();

    }
    @Test(priority = 9)
    public void verifyLinkeninLink()
    {

        homePage.clickLinkedinIcon();
    }

    @Test(priority = 10)
    public void verifyInstagramLink()
    {
        String mainWindow = driver.getWindowHandle();
        homePage.clickInstagramIcon();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        System.out.println("Instagram URL: " + driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(mainWindow);
        System.out.println("Home URL: " + driver.getCurrentUrl());
    }


    @Test(priority = 11)
    public void verifyCourseCardUI() {
        List<WebElement> validCourses = coursesPage.getValidCourseCards();
        System.out.println("Valid Courses: " + validCourses.size());
        assertTrue(validCourses.size() > 0, "No valid course cards found!");


        WebElement card = validCourses.get(4);
        coursesPage.scrollIntoView(card);

        String title = coursesPage.getCourseTitleText(card);
        String instructor = coursesPage.getInstructorNameText(card);
        WebElement image = coursesPage.getCourseImage(card);
        WebElement subscribeBtn = coursesPage.getSubscribeButton(card);

        System.out.println("===== Course Card =====");
        System.out.println("Title: " + title);
        System.out.println("Instructor: " + instructor);
        System.out.println("Image: " + (image != null));
        System.out.println("Subscribe: " + (subscribeBtn != null && subscribeBtn.isDisplayed()));

        assertFalse(title.isEmpty(), " Course title is empty");
        assertNotNull(image, " Course image is missing");


        if (instructor.isEmpty()) {
            System.out.println(" Instructor not available for this course");
        } else {
            assertFalse(instructor.isEmpty(), "Instructor name should not be empty");
        }

        if (subscribeBtn == null) {
            System.out.println("Subscribe button not found");
        } else {
            assertTrue(subscribeBtn.isDisplayed(), "Subscribe button not visible");
        }

    }

}