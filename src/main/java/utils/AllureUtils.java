package utils;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class AllureUtils {

    @Attachment(value="Test Case Sceenshot",type="image/png")
    public static byte[] attachScreenshot(byte[] screenshot){
        return screenshot;
    }

    @Attachment(value="[0]",type="text/plain")
    public static String attachTextLogs(String message){
        return message;
    }


    @Attachment
    public String logOutput(List<String> outputList) {
        String output = "";
        for (String o : outputList)
            output += o + "<br/>";
        return output;
    }


}
