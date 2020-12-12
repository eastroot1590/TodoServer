package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
public class ProfileService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/profile")
    public ProfileData hello(@RequestParam(value = "name", defaultValue = "world")String name) {

        Query query = new Query(Criteria.where("name").is(name));

        ProfileData profile = mongoTemplate.findOne(query, ProfileData.class, "profile");

        if (profile == null) {
//			return HttpStatus.NOT_FOUND;
//			throw new RuntimeException();
            throw new DataNotFoundException(100);
        }
        return profile;

//		return String.format("Hello %s", name);
    }

    @PostMapping("/upload")
    public String UploadImage(@RequestParam("file") MultipartFile file) {
        try {
            var fileName = UUID.fromString(file.getOriginalFilename());
            FileOutputStream fileStream = new FileOutputStream("d:/profile/" + fileName.toString());
            InputStream inputStream = file.getInputStream();

            var buffer = new byte[1024];
            int length = 0;
            while((length = inputStream.read(buffer)) > 0) {
                fileStream.write(buffer, 0, length);
            }

            fileStream.close();
            inputStream.close();
        } catch(FileNotFoundException e) {
            System.out.print(e);
        } catch(IOException e) {
            System.out.print(e);
        }

        return file.getOriginalFilename();
    }

    @GetMapping("/profile/{file}")
    public void GetImage(@PathVariable("file")String fileName, HttpServletResponse response) {
        try {
            FileInputStream fileStream = new FileInputStream("d:/profile/" + fileName);

            response.setHeader("Content-Type", "image/jpeg");

            OutputStream outStream = response.getOutputStream();

            var buffer = new byte[1024];
            int length = 0;
            int temp;

            while ((temp = fileStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, temp);
                length += temp;
            }

            response.setHeader("Content-Length", Integer.toString(length));

        } catch (FileNotFoundException e) {
            System.out.print(e);
        } catch (IOException e) {
            System.out.print(e);
        }
    }
}
