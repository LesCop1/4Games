package fr.bcecb.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

public class JsonLoader {

    public static JSONObject load(String path) {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try (Reader reader = new FileReader(path)) {

            jsonObject = (JSONObject) parser.parse(reader);

        } catch (Exception e) {
            System.out.println(e);
        }

        return jsonObject;
    }

    public static void save(String path, JSONObject jsonObject) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonObject.toJSONString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
