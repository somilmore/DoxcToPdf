package com.kyzer.apachePdf;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
public class SpringBootHelloWorld {
	
	static Logger logger = LoggerFactory.getLogger(SpringBootHelloWorld.class);

	@GetMapping("/")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		String version="1.0.3";
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.info("EBG NeSL API App Started...");
		logger.info("Version :: "+version);
		logger.info("Released By :: KYZ - 188");
		logger.info("Released On :: 06-03-2024 04:00 PM");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		logger.info("Hello World..");
		return "Welcome home! The client locale is {}.";
	}
}
