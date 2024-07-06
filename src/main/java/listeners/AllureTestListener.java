package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.internal.IResultListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static factory.PlaywrightFactory.takeScreenshot;
import static io.qameta.allure.Allure.getLifecycle;
import static utils.AllureUtils.attachScreenshot;

public class AllureTestListener implements ITestListener, IResultListener, ISuiteListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllureTestListener.class);
    private static final String SUITE_UID = "SUITE_UID";
    private AllureLifecycle lifecycle = getLifecycle();
    private Set<String> startedTestNames = Collections.newSetFromMap(
            new ConcurrentHashMap<String, Boolean>());
    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onTestFailure(ITestResult testResult) {
    }
}
