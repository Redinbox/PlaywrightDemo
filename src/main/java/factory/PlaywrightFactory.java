package factory;


import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Proxy;
import config.ConfigurationManager;
import config.ProjectConfig;
import io.qameta.allure.Allure;
import lombok.var;
import net.minidev.json.parser.ParseException;
import utils.AllureUtils;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;

import static utils.AllureUtils.attachScreenshot;

public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Properties prop;
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    ProjectConfig configuration = ConfigurationManager.config();

    public Page initBrowser(ProjectConfig config,BrowserOptions browserOptions) throws FileNotFoundException, ParseException {

        String browserName = browserOptions.getBrowserName().trim();
        boolean isHeadLess = config.isHeadless();
        boolean isVideoRecord = config.isVideoRecord();
        System.out.println("browser name is : " + browserName);
        System.out.println("headMode name is : " + isHeadLess);
        System.out.println("browser name is in the config: " + config.browser());
        System.out.println("Staging proxy: " + config.stgProxy());
        System.out.println("ENVIRONMENT: " + config.environment());

        // playwright = Playwright.create();
        tlPlaywright.set(Playwright.create());

        BrowserType.LaunchOptions launchOptions;
        // Configure proxy settings

        if(config.getProxyMode()) {
            launchOptions = new BrowserType.LaunchOptions();
            launchOptions.setProxy(new Proxy(config.stgProxy().toString())
                            .setBypass(config.stgByPass().toString()));
            launchOptions.setHeadless(isHeadLess).setSlowMo(config.slowMotion());
        }else{
            launchOptions = new BrowserType.LaunchOptions().setHeadless(isHeadLess);
        }

        switch (browserName.toLowerCase()) {
            case "chromium":
                tlBrowser.set(
                        getPlaywright().chromium().launch(launchOptions));
                break;
            case "chrome":
                tlBrowser.set(
                        getPlaywright().chromium().launch(launchOptions.setChannel("chrome")));
                break;
            case "edge":
                tlBrowser.set(
                        getPlaywright().chromium().launch(launchOptions.setChannel("msedge")));
                break;
            case "firefox":
                tlBrowser.set(
                        getPlaywright().firefox().launch(launchOptions));
                break;
            case "safari":
                tlBrowser.set(
                        getPlaywright().webkit().launch(launchOptions));
                break;

            default:
                System.out.println("please pass the right browser name......");
                break;
        }

        BrowserContext context;

        if (config.enableTrace()) {
            context = getBrowser().newContext();
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(false));
        }

        if(isVideoRecord){
            // Enable video recording
             context = getBrowser().newContext(new Browser.NewContextOptions()
                        .setRecordVideoDir(Paths.get("videos/")) // Set the video recording directory
                        .setRecordVideoSize(950, 900)
                        .setIgnoreHTTPSErrors(true)
                        .setJavaScriptEnabled(true));

        }else{
            context=getBrowser().newContext(new Browser.NewContextOptions()
                    .setIgnoreHTTPSErrors(true)
                    .setJavaScriptEnabled(true));
        }

        tlBrowserContext.set(context);

        tlPage.set(getBrowserContext().newPage());
        getPage().setDefaultTimeout(config.timeout());
        getPage().onConsoleMessage(msg -> System.out.println(msg.text()));
        getPage().navigate(config.baseUrl().trim());
        takeScreenshot();
        System.out.println("check");
        return getPage();

    }

    private BrowserType.LaunchOptions options() {
        return new BrowserType.LaunchOptions()
                .setHeadless(configuration.isHeadless())
                .setSlowMo(configuration.slowMotion());
    }


    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";

        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64Path = Base64.getEncoder().encodeToString(buffer);
        captureAndAttachScreenshot(buffer,"test the attachement");
        return base64Path;

    }

    //To capture & attach screenshot to allure report
    private static void captureAndAttachScreenshot(byte[] screenshot,String attachmentName) {
        attachScreenshot(screenshot);
    }
}
