package pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import utils.AllureUtils;

import java.nio.file.Paths;

import static com.microsoft.playwright.options.LoadState.NETWORKIDLE;
import static factory.PlaywrightFactory.takeScreenshot;

public class HomePage {

    private Page page;

    private String search = "#common-header-search-input||#ri-cmn-hdr-sitem";
    String[] selectorOptions = {"#common-header-search-input", "#ri-cmn-hdr-sitem"};

    String[] searchButtons= {"#ri-cmn-hdr-button","a[aria-label='test']"};
    private String suggestionList="//div[contains(@class,'suggestion-list--TfrLB)]";
    private String suggetionItem="MacBook";
    private String searchIconBefore = "//a[@aria-label='test']";
    private String searchIconAfter = "//a[@class='link--2nzHQ link--1veQk']";
    private String searchPageHeader = "div[@class='_big section keyword _break']";
    private String loginLink = "//a[contains(text(),'test')]";
    private String myAccountLink = "a[title='My Account']";
    public HomePage(Page page) {
        this.page = page;
    }

    public String getHomePageTitle() {
        String title =  page.title();
        System.out.println("page title: " + title);
        takeScreenshot();
        return title;
    }

    public String getHomePageURL() {
        String url =  page.url();
        System.out.println("page url : " + url);
        takeScreenshot();
        return url;
    }

    public LoginPage navigateToLoginPage() {
        takeScreenshot();
        page.click(loginLink);
        takeScreenshot();
        return new LoginPage(page);
    }
}
