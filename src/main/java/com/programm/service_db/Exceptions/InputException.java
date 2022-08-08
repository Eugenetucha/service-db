package com.programm.service_db.Exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programm.service_db.Model.JsonView.ExceptionJson;

import java.io.*;

public class InputException extends Exception {

    public InputException(ExceptionJson exceptionJson) {

        ObjectMapper objectMapper = new ObjectMapper();
        try (
                FileOutputStream output = new FileOutputStream("output.json")) {
                objectMapper.writeValue(output, exceptionJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
