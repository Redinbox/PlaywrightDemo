package tests;

import fileFactory.FileFactory;
import base.BaseTest;
import com.microsoft.playwright.Page;
import config.ConfigurationManager;
import config.ProjectConfig;

import io.qameta.allure.Step;
import listeners.TestListener;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;

import utils.AllureUtils;
import utils.RetryAnalyzer;

import static factory.PlaywrightFactory.takeScreenshot;

@Listeners({ TestListener.class })
public class LoginPageTest extends BaseTest {

    FileFactory fileManager=new FileFactory();
    private Page page;
    ProjectConfig configuration = ConfigurationManager.config();

    //@BeforeTest
    public JSONObject getTestData() throws FileNotFoundException, ParseException {
        JSONObject testArray=fileManager.readAPITestData("validateStockUpdateSTG");
        return testArray;
    }

    @Test(priority = 1)
    @Step("Home navigation page ")
    public void loginPageNavigationTest() {
        takeScreenshot();
        loginPage = homePage.navigateToLoginPage();
        String actLoginPageTitle = loginPage.getLoginPageTitle();
        System.out.println("page act title: " + actLoginPageTitle);
    }


    @Test(priority = 2)
    @Step("User Login page ")
    public void appLoginTest() {
        String uname=configuration.getUserName().trim();
        String pwd=configuration.getPassword().trim();
        loginPage.doLogin(uname, pwd);
    }

    @Test(priority = 3)
    @Step("Navigating to item page")
    public void navigateToItemsPage() throws FileNotFoundException, ParseException {
        JSONObject tcData=getTestData();
        String param1=tcData.get("shopUrl").toString().trim();
        System.out.println(param1);
        String param2= tcData.get("$..rmsItemManagementNumber").toString();
        System.out.println(param2);
        String buildUrl=configuration.baseUrl()+"/"+ param1 + "/"+param2;
        loginPage.validateItemlist(param1,param2);
    }

}
