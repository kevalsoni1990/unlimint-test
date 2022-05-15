package com.javadeveloper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javadeveloper.model.OrderData;
import com.javadeveloper.model.ResponseMessage;
import com.javadeveloper.service.OrderService;
import com.javadeveloper.utility.FileHelper;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/file")
public class JavaTestController {

  @Autowired
  OrderService orderService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("csvfile") MultipartFile csvfile,@RequestParam("jsonfile") MultipartFile jsonfile) {
    String message = "";

    if (FileHelper.hasCSVFormat(csvfile) && FileHelper.hasJsonFormat(jsonfile)) {
      try {
    	  Thread t1 = new Thread() {
              public synchronized void run() {
            	  orderService.saveCSV(csvfile);
              }
          };

          Thread t2 = new Thread() {
              public synchronized void run() {
            	  orderService.saveJSON(jsonfile);
              }
          };
    	  
          t1.start();
          t2.start();
    	 
        message = "Uploaded the file successfully: " + csvfile.getOriginalFilename();
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/csv/download/")
                .path(csvfile.getOriginalFilename())
                .toUriString();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
      } catch (Exception e) {
        message = "Could not upload the file: " + csvfile.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
      }
    }

    message = "Please upload a csv and json files only !";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
  }

  
  @GetMapping("/getOrders")
  public ResponseEntity<List<OrderData>> getAllOrders() {
    try {
      List<OrderData> orders = orderService.getAllOrders();

      if (orders.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(orders, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/download/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
    //InputStreamResource file = new InputStreamResource(fileService.load());
	  InputStreamResource file = new InputStreamResource(orderService.loadOrder());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }
}
