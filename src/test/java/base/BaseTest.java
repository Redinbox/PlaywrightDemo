package base;

import com.microsoft.playwright.Page;
import config.ConfigurationManager;
import config.ProjectConfig;
import config.PropertyUtil;
import factory.BrowserOptions;
import factory.PlaywrightFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Label;
import lombok.var;
import net.minidev.json.parser.ParseException;
import org.aeonbits.owner.Config;
import org.testng.annotations.*;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static config.ConfigurationManager.config;
import static factory.PlaywrightFactory.getBrowser;
import static factory.PlaywrightFactory.takeScreenshot;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;
import pages.HomePage;
import pages.LoginPage;
import utils.AllureUtils;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class BaseTest {

	PlaywrightFactory pf;

	Page page;

	protected Properties prop;
	protected HomePage homePage;
	protected LoginPage loginPage;

	private static Set<String> addedBrowsers = new HashSet<>();


	@AfterClass
	public void createPlaywrightAndBrowserInstancesAndSetupAllureEnvironment() {

		String browser=System.getProperty("browser");
		System.out.println("browser:"+browser);
		Allure.getLifecycle().updateTestCase(testResult -> {
			testResult.getLabels().add(new Label().setName(browser));
		});

		String browserName=getBrowser().browserType().name();
		setBrowsers(browserName);

		allureEnvironmentWriter(
				ImmutableMap.<String, String>builder()
						.put("Platform", System.getProperty("os.name"))
						.put("Version", System.getProperty("os.version"))
						.put("Browser", addedBrowsers.toString())
						//.put("Browser Version", StringUtils.capitalize(config().browser()))
						.put("Context URL", config().baseUrl())
						.build(),
				config().allureResultsDir() + "/");
	}


	@BeforeClass
	@Parameters( {"browser"} )
	public void setup(@Optional("chrome") String browserName) throws FileNotFoundException, ParseException {
		pf = new PlaywrightFactory();

		ProjectConfig configuration = ConfigurationManager.config();
		PropertyUtil configurationUpdate=null;
		BrowserOptions browserOptions=new BrowserOptions();
		if (browserName != null) {
			browserOptions.setBrowserName(browserName);
			configurationUpdate.updateConfigurationProperty("browser",browserName);
		}

		page = pf.initBrowser(configuration,browserOptions);
		takeScreenshot();
		page.setDefaultTimeout(config().timeout());
		homePage = new HomePage(page);
	}

	public void setBrowsers(String browser){
		if (!addedBrowsers.contains(browser)) {
			/**Allure.getLifecycle().updateTestCase(testResult -> {
				testResult.getLabels().add(Label.builder().name("browser").value(browser).build());
			});*/
			addedBrowsers.add(browser);
		}
	}

	public void setAndConfigurePage(Page page) {
		this.page = page;
		page.setDefaultTimeout(config().timeout());
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		System.out.println("After test: Closing the browser session");
		page.context().browser().close();
	}

}
