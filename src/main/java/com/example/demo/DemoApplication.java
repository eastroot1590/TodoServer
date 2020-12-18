package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
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

		try {
			FileInputStream token = new FileInputStream("src/main/resources/firebase/firebase_service_key.json");

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(token))
					.build();

			FirebaseApp.initializeApp(options);

			SpringApplication.run(DemoApplication.class, args);
		} catch(FileNotFoundException e) {
			System.out.print(e);
		} catch(IOException e) {
			System.out.print(e);
		}

	}


	@ExceptionHandler(DataNotFoundException.class)
	public CustomError DataNotFound(DataNotFoundException e) {
		CustomError error = new CustomError(e.getErrorCode(), "no data for id");
		return error;
	}

}
