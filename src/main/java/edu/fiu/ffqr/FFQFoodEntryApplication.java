package edu.fiu.ffqr;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonMappingException;

import edu.fiu.ffqr.dataloader.DataLoader;


@Component
@SpringBootApplication
public class FFQFoodEntryApplication {
	
	public static void main(String[] args) throws JsonMappingException, IOException, InterruptedException {
		ApplicationContext ctx = SpringApplication.run(FFQFoodEntryApplication.class, args);
		
		String loadDataArg = (String) ctx.getBean("loadFoodItemsEnvVar");
		if (loadDataArg.equalsIgnoreCase("true")) {
			DataLoader loader = (DataLoader)ctx.getBean(DataLoader.class);
			loader.load();
		}

	}
	
	@Bean
	public String loadFoodItemsEnvVar(@Value("${mongo.fooditems.load}")String value) {
		return value;
	}

}

