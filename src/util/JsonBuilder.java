package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonBuilder {

    public static String toJson(HashMap<String, String> hash) {

        Iterator<Map.Entry<String, String>> iterator = hash.entrySet().iterator();
        String jsonString = "";
        jsonString += "{";

        while (iterator.hasNext()) {
            Map.Entry<java.lang.String, java.lang.String> entry = iterator.next();
            jsonString += "\"" + entry.getKey() + "\": " + "\"" + entry.getValue() + "\",";
        }

        // Remove the last comma from string
        jsonString = jsonString.substring(0, jsonString.length() - 1);

        jsonString += "}";

        return jsonString;
    }
}
