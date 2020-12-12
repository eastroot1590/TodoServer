package com.example.demo;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.DataObjectPropertyName;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.type.ErrorType;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@SpringBootApplication
@RestController
public class DemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@ExceptionHandler(DataNotFoundException.class)
	public CustomError DataNotFound(DataNotFoundException e) {
		CustomError error = new CustomError(e.getErrorCode(), "no data for id");
		return error;
	}

}
