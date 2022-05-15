package com.javadeveloper.utility;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadeveloper.model.OrderData;



public class FileHelper {
  public static String CSVTYPE = "text/csv", JSONTYPE = "application/json";

  public static boolean hasCSVFormat(MultipartFile file) {
    if (CSVTYPE.equals(file.getContentType())
    		|| file.getContentType().equals("application/vnd.ms-excel")) {
      return true;
    }

    return false;
  }
  
  public static boolean hasJsonFormat(MultipartFile jsonfile) {
	  if (JSONTYPE.equals(jsonfile.getContentType())
	    		|| jsonfile.getContentType().equals("application/geo+json")) {
	      return true;
	    }

	    return false;
	}
  public static List<OrderData> jsonToOrders(InputStream inputStream, String fileName) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<OrderData>> orderDataType = new TypeReference<List<OrderData>>() {};
			List<OrderData> orderListwithotProcess  = mapper.readValue(inputStream, orderDataType);
			
			List<OrderData> orderListAfterProcess = orderListwithotProcess.stream().map(
					o -> new OrderData(o.getOrderId(),o.getAmount(),o.getCurrency(),o.getComment(),fileName, (orderListwithotProcess.indexOf(o)+1),"OK")
				).toList();
			
			return orderListAfterProcess;
		}catch (IOException e) {
			throw new RuntimeException("fail to parse JSON file: " + e.getMessage());
		}
	}
  public static List<OrderData> csvToOrders(InputStream is, String fileName) {
	  try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<OrderData> orderList = new ArrayList<>();

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		      int cnt = 1;
		      for (CSVRecord csvRecord : csvRecords) {
		    	  OrderData o = new OrderData(
		              Integer.valueOf(csvRecord.get("Order ID")),
		              Float.valueOf(csvRecord.get("amount")),
		              csvRecord.get("currency"),
		              csvRecord.get("comment"),fileName,cnt++,"OK"
		            );

		    	  orderList.add(o);
		      }

		      return orderList;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
	}
  
  public static ByteArrayInputStream orderToCSV(List<OrderData> orderList) {
	    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	      for (OrderData od : orderList) {
	        List<String> data = Arrays.asList(
	              String.valueOf(od.getId()),
	              String.valueOf(od.getOrderId()),
	              String.valueOf(od.getAmount()),
	              String.valueOf(od.getCurrency()),
	              od.getComment(),od.getFilename(),String.valueOf(od.getLine()),od.getResult()
	            );

	        csvPrinter.printRecord(data);
	      }

	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }




}
