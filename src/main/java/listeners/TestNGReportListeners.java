package listeners;
import org.apache.maven.shared.utils.logging.MessageBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

public class TestNGReportListeners implements ITestListener {

    public static List<String> passedTests = new ArrayList<>();
    public static List<String> failedTests = new ArrayList<>();


    public void onTestStart(ITestResult result) {
        String testClass = result.getTestClass().getName().replaceAll(".+\\.", "");
        String testName = result.getMethod().getMethodName();
        //LOGGER.debug("[TEST START] [{}]->[{}]", testClass, testName);

    }

    public void onTestSuccess(ITestResult result) {
        String testClass = result.getTestClass().getName().replaceAll(".+\\.", "");
        String testName = result.getMethod().getMethodName();

    }

    public void onTestFailure(ITestResult result) {
        String testClass = result.getInstanceName().replaceAll(".+\\.", "");
        String testName = result.getMethod().getMethodName();
    }

    public void onTestSkipped(ITestResult result) {
        String testClass = result.getTestClass().getName().replaceAll(".+\\.", "");
        String testName = result.getMethod().getMethodName();

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {

    }
}
