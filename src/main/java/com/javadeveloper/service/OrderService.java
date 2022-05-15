package com.javadeveloper.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javadeveloper.model.OrderData;
import com.javadeveloper.repo.OrderRepository;
import com.javadeveloper.utility.FileHelper;

@Service
public class OrderService {
  @Autowired
  OrderRepository orderrepo;

  public void saveCSV(MultipartFile file) {
    try {
      List<OrderData> orders = FileHelper.csvToOrders(file.getInputStream(),file.getOriginalFilename());
      orderrepo.saveAll(orders);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }


public List<OrderData> getAllOrders() {
	// TODO Auto-generated method stub
	return orderrepo.findAll();
}

public InputStream loadOrder() {
	ByteArrayInputStream in = FileHelper.orderToCSV(getAllOrders());
    return in;
}


public void saveJSON(MultipartFile jsonfile) {
	// TODO Auto-generated method stub
	try {
		List<OrderData> orders = FileHelper.jsonToOrders(jsonfile.getInputStream(),jsonfile.getOriginalFilename());
		orderrepo.saveAll(orders);
	} catch (IOException e) {
		throw new RuntimeException("fail to store json data: " + e.getMessage());
	}
}
}

