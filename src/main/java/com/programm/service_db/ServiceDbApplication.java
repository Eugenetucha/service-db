package com.programm.service_db;

import com.programm.service_db.Exceptions.InputException;
import com.programm.service_db.Model.JsonView.ExceptionJson;
import com.programm.service_db.Utils.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ServiceDbApplication implements ApplicationRunner {
    @Autowired
    JsonReader jsonReader;

    public static void main(String[] args) {
        SpringApplication.run(ServiceDbApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> arguments = new ArrayList<>();
        boolean flag = false;
        if (Arrays.stream(args.getSourceArgs()).noneMatch(x -> x.contains("stat") || x.contains("search")) ||
                args.getSourceArgs().length < 3) {
            throw new InputException(new ExceptionJson("error", "неправильный формат аргументов"));
        }
        for (String arg : args.getSourceArgs()) {
            if (flag) {
                arguments.add(arg);
            }
            if (arg.equals("search") || arg.equals("stat")) {
                flag = true;
                arguments.add(arg);
            }
        }
        jsonReader.choose(arguments);
    }
}
