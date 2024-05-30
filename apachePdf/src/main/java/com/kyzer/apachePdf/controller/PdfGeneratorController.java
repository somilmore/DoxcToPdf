package com.kyzer.apachePdf.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kyzer.apachePdf.service.PdfGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@RestController
@RequestMapping("/home")
public class PdfGeneratorController {
	
	@Autowired
	PdfGenerator filler;
	
	@GetMapping("/fillForm/{num}")
	public void generateFile(@PathVariable int num) throws Exception {
		filler.fillTemplate(num);
	}
	
}
