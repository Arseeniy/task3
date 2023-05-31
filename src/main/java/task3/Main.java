package task3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    private static TestsArray testsArray = null;
    private static ValuesArray valuesArray = null;

    public static void main(String[] args) {
        if (args.length == 2) {

            File tests = new File(args[0]);
            File values = new File(args[1]);
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                testsArray = objectMapper.readValue(tests, TestsArray.class);
                valuesArray = objectMapper.readValue(values, ValuesArray.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (testsArray.getTests() != null) {
                iterateTestsList(testsArray.getTests());
            } else throw new RuntimeException("Ошибка! Исходный файл пуст.");

            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);

            try {
                File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                File parentDir = jarFile.getParentFile();
                File newFile = new File(parentDir, "report.json");
                mapper.writeValue(newFile, testsArray);
            } catch (IOException  | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public static void iterateTestsList(List<TestObject> testObjectList) {
        for (TestObject object : testObjectList) {
            updateObject(object);
        }
    }

    public static void updateObject(TestObject object) {
        int id = object.getId();
        for (ValueObject valueObject : valuesArray.getValues()) {
            if (valueObject.getId() == id) {
                object.setValue(valueObject.getValue());
            }
        }
        if (object.getValues() != null) {
            iterateTestsList(object.getValues());
        } else return;
    }
}
