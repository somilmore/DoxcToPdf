package com.kyzer.apachePdf.service;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.*;

@Service
public class PdfGenerator {

	public void fillTemplate(int num) throws Exception {
		
		ClassPathResource classPath = null;
		
		if(num==15)
			classPath = new ClassPathResource("/templates/TRADE_ADVICE1_015.docx");
			
		else if(num==16)
			classPath = new ClassPathResource("/templates/TRADE_ADVICE1_016.docx");
			
		else if(num==17) 
			classPath = new ClassPathResource("/templates/TRADE_ADVICE1_017.docx");
			
		else 
			classPath = new ClassPathResource("/templates/TRADE_ADVICE_026.docx");
			
		String outputFile = "/kyzer/test/output.docx";

		Map<String, String> placeholderMap = new HashMap<>();
		placeholderMap.put("[Our_Reference_Number_Header]", "6QYNKW");
		placeholderMap.put("[Date]", "24th April");
		placeholderMap.put("[Advise_To_Party]", "Somil More Inc.");
		placeholderMap.put("[Advise_To_Party_Address]", "Bhayander");
		placeholderMap.put("[Our_Reference_Number_Body]", "69696969696");
		placeholderMap.put("[Issuing_Bank_LC_Ref]", "6969696969");
		placeholderMap.put("[LC_Issuing_Bank]", "RBL Bank Pvt. Ltd.");
		placeholderMap.put("[LC_Issuing_Bank_City]", "Mumbai");
		placeholderMap.put("[LC_Issuing_Bank_cnty]", "Maharashtra");
		placeholderMap.put("[Currency]", "Rs.");
		placeholderMap.put("[Amount]", "7,00,00,000");
		placeholderMap.put("[Currency_Amount_in_words]", "7 Crores Rupees Only");
		placeholderMap.put("[Issue_Date]", "24th April, 2024");
		placeholderMap.put("[Expiry_Date]", "24th April, 2024");
		placeholderMap.put("[LC_Applicant]", "Somil More");
		placeholderMap.put("[Beneficiary_Name]", "Harshal Vaidya");
		placeholderMap.put("[Latest_Ship_Date]", "24th April, 2024");
		placeholderMap.put("[Debit_Account_No]", "1002130090");
		placeholderMap.put("[Amount_In_INR]", "7,00,00,000");
		placeholderMap.put("[Commission]", "INR 150");
		placeholderMap.put("[Total_Amount]", "7,00,00,150");
		placeholderMap.put("[BANK_ADDR]", "Mumbai");
		placeholderMap.put("[BRANCH_GSTIN]", "GST1020971001212");
		placeholderMap.put("[BRANCH_STATE_CODE]", "081");
		placeholderMap.put("[CUST_NAME]", "Somil More");
		placeholderMap.put("[APPLICANT_NAME]", "Siddharth Jain");
		placeholderMap.put("[CUST_ADDR]", "Bhayander");
		placeholderMap.put("[APPLICANT_ADDRESS]", "Kandivali");
		placeholderMap.put("[CUST_ACCT]", "210102801901");
		placeholderMap.put("[CUST_GSTIN]", "GST9812u982u1");
		placeholderMap.put("[CUST_STATE_CODE]", "081");
		placeholderMap.put("[INVOICE_NO]", "INV1890090");
		placeholderMap.put("[INVOICE_DATE]", "5th February, 2024");
		placeholderMap.put("[DATE_OF_SUPPLY]", "10th February, 2024");
		placeholderMap.put("[PLACE_OF_SUPPLY]", "Bhayander");
		placeholderMap.put("[BILL_ID]", "BILL912201");
		placeholderMap.put("[DUE_DATE]", "7th February, 2024");
		placeholderMap.put("[BILL_CRNCY]", "Rs.");
		placeholderMap.put("[BILL_AMT]", "10000");
		placeholderMap.put("[BILL_RATE]", "5%");
		placeholderMap.put("[TaxonBankCharges] ", "Rs. 500");
		placeholderMap.put("[Rate_IGST]", "8%");
		placeholderMap.put("[CollectedAmount_IGST]", "Rs. 1200");
		placeholderMap.put("[Rate_CGST]", "7%");
		placeholderMap.put("[CollectedAmount_CGST]", "Rs. 1200");
		placeholderMap.put("[Rate_SGST]", "7%");
		placeholderMap.put("[CollectedAmount_SGST]", "Rs. 1200");
		placeholderMap.put("[Rate_UGST]", "7%");
		placeholderMap.put("[CollectedAmount_UGST]", "Rs. 1200");
		placeholderMap.put("[TotalAmount]", "15000");

		try {
			// Load the input Word document
			FileInputStream fis = new FileInputStream(classPath.getFile());
			XWPFDocument document = new XWPFDocument(fis);

			// Replace placeholders with values
			replacePlaceholders(document, placeholderMap);

			// Save the modified document
			FileOutputStream fos = new FileOutputStream(outputFile);
			document.write(fos);

			// Close streams
			fis.close();
			fos.close();

			System.out.println("Word document with placeholders replaced and saved successfully.");

			String outputPdf = "/kyzer/test";
			
			convertDocxToPdfV3(outputFile,outputPdf);

			System.out.println("PDF created successfully.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void replacePlaceholders(XWPFDocument document, Map<String, String> placeholderMap) {
		// Replace placeholders in paragraphs
		for (XWPFParagraph paragraph : document.getParagraphs()) {
			for (Map.Entry<String, String> entry : placeholderMap.entrySet()) {
				String placeholder = entry.getKey();
				String replacement = entry.getValue();
				replaceTextInParagraph(paragraph, placeholder, replacement);
			}
		}

		for (XWPFTable table : document.getTables()) {
			for (XWPFTableRow row : table.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						for (Map.Entry<String, String> entry : placeholderMap.entrySet()) {
							String placeholder = entry.getKey();
							String replacement = entry.getValue();
							replaceTextInParagraph(paragraph, placeholder, replacement);
						}
					}
				}
			}
		}
	}

	private static void replaceTextInParagraph(XWPFParagraph paragraph, String placeholder, String replacement) {
		String text = paragraph.getText();
		if (text.contains(placeholder)) {
			// Clear existing runs
			while (paragraph.getRuns().size() > 0) {
				paragraph.removeRun(0);
			}
			// Add new run with replaced text
			XWPFRun run = paragraph.createRun();
			run.setText(text.replace(placeholder, replacement));
		}
	}
	
	public void convertDocxToPdfV3(String inputPath, String outputPath) throws Exception {
		
		 //For Windows
		//Process process = Runtime.getRuntime().exec("soffice --headless --invisible --convert-to pdf " + inputPath + " --outdir " + outputPath);
		
		//For Linux
		Process process = Runtime.getRuntime().exec("libreoffice24.2 --headless --invisible --convert-to pdf " + inputPath + " --outdir " + outputPath);  
		
        int exitCode = process.waitFor();
        
        if (exitCode == 0) {
            System.out.println("Conversion successful!");
        } else {
            System.out.println("Conversion failed!");
        }
	}

}
