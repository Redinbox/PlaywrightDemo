package fileFactory;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileFactory {

    Directories directories=new Directories();
    public JSONObject readAPITestData(String testId) throws FileNotFoundException, ParseException {

        String directoryPath=directories.findRootModulePath("-ui-web");
        JSONParser jsonparser=new JSONParser();
        FileReader reader=new FileReader(directoryPath+"/API_UI_COMMUNICATOR/api-automation.json");
        Object obj=jsonparser.parse(reader);
        JSONObject testdata=(JSONObject)obj;
        JSONObject testArray= (JSONObject) testdata.get(testId);
        System.out.println(testArray);

        return testArray;
    }
}
