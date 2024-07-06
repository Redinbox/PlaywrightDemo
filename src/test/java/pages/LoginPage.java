package pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import config.ConfigurationManager;
import config.ProjectConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.testng.Assert;
import utils.AllureUtils;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

import static factory.PlaywrightFactory.takeScreenshot;
import static io.restassured.RestAssured.expect;

public class LoginPage {
    private Page page;

    // 1. String Locators - OR
    private String emailId = "#loginInner_u";
    private String password = "#loginInner_p";
    private String loginBtn = "//input[@name='submit']";
    private String forgotPwdLink = "//div[@class='form-group']//a[normalize-space()='Forgotten Password']";
    private String logoutLink = "//div[contains(text(),'test')]";

    private String eleItemlist="//*[@class='normal-deliveryDate']";

    ProjectConfig configuration = ConfigurationManager.config();

    // 2. page constructor:
    public LoginPage(Page page) {
        this.page = page;
    }

    // 3. page actions/methods:
    public String getLoginPageTitle() {
        takeScreenshot();
        return page.title();
    }

    public boolean isForgotPwdLinkExist() {
        return page.isVisible(forgotPwdLink);
    }

    public boolean doLogin(String appUserName, String appPassword) {
        try{
        System.out.println("App creds: " + appUserName + ":" + appPassword);
        page.fill(emailId, appUserName);
        page.fill(password, appPassword);
        page.click(loginBtn);

        takeScreenshot();

        //page.locator(logoutLink).waitFor();
        if(page.locator(logoutLink).isVisible()) {
            System.out.println("user is logged in successfully....");
            return true;
        }else {
            System.out.println("user is not logged in successfully....");
            return false;
        }
        }catch (Exception ex) {
            takeScreenshot();
            throw ex;
        }
    }

    @Step("Validating item page")
    public void validateItemlist(String shopUrl,String itemNumber) {
        try {

            String itemURL=configuration.itemUrl();
            String navURL=itemURL+"/"+shopUrl+"/"+itemNumber;
            System.out.println("Navigating to : " + navURL);

            page.navigate(navURL);

            String expText="0:00time";
            String actualText=page.locator(eleItemlist).textContent();
            System.out.println("actual text:"+actualText);
            Assert.assertTrue(actualText.contains(expText),itemNumber+" is listed");
            takeScreenshot();

        } catch (Exception ex) {
            takeScreenshot();
            throw ex;
        }
    }

}
