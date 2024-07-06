package tests;

import base.BaseTest;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
public class HomePageTest extends BaseTest {

    private Page page;

    @Step("Navigating to Login link")
    @Test(priority = 1)
    public void loginPageNavigationTest() {
        loginPage = homePage.navigateToLoginPage();
        String actLoginPageTitle = loginPage.getLoginPageTitle();
        System.out.println("page act title: " + actLoginPageTitle);
    }

    @Test(priority=1)
    @Step("Verifying the home page title")
    public void homePageTitleTest() {
        String actualTitle = homePage.getHomePageTitle();
        System.out.println("Home page title is: "+actualTitle);
        //Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
    }

    @Test
    @Step("Verifying the home page URL")
    public void homePageURLTest() {
        String actualURL = homePage.getHomePageURL();
        System.out.println("Home page url is: "+actualURL);
    }



}
