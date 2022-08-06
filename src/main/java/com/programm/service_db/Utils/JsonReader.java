package com.programm.service_db.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programm.service_db.Model.JsonView.Criterias;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonReader {
    static String input;
    static String output;

    public static void choose(List<String> type) {
        input = type.get(1);
        output = type.get(2);
        if (type.contains("search")) {
            search();
        } else {
            stat();
        }
    }

    static void search() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (FileInputStream input = new FileInputStream("src/main/java/com/programm/service_db/JsonFiles/" + JsonReader.input);
             FileOutputStream output = new FileOutputStream("src/main/java/com/programm/service_db/JsonFiles" + JsonReader.output)) {
             Criterias criterias = objectMapper.readValue(input, Criterias.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void stat() {

    }

}
