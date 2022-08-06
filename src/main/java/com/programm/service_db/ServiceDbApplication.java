package com.programm.service_db;

import com.programm.service_db.Exceptions.InputException;
import com.programm.service_db.Exceptions.TypeOfExceptions;
import com.programm.service_db.Utils.JsonReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ServiceDbApplication {

	public static void main(String[] args) throws InputException {
		List<String> arguments = new ArrayList<>();
		boolean flag = false;
		if(Arrays.stream(args).noneMatch(x -> x.equals("search")) ||
		args.length < 3){
			throw new InputException(TypeOfExceptions.WRONG_INPUT_VALUE);
		}
		for(String arg : args){
			if(flag){
				arguments.add(arg);
			}
			if(arg.equals("search") || arg.equals("stat")){
				flag = true;
				arguments.add(arg);
			}
		}
		JsonReader.choose(arguments);
		SpringApplication.run(ServiceDbApplication.class, args);
	}

}
