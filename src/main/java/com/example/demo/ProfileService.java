package com.example.demo;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.netty.handler.codec.base64.Base64Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RestController
public class ProfileService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/profile")
    public ProfileData responseProfile(@RequestParam(value = "token")String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();

//            Query query = new Query(Criteria.where("uid").is(uid));
            Query query = new Query(Criteria.where("uid").is("007"));

            ProfileData profile = mongoTemplate.findOne(query, ProfileData.class, "profile");

            if (profile == null) {
                throw new DataNotFoundException(100);
            }

            return profile;
        } catch(FirebaseAuthException e) {
           System.out.print(e);
        }

        return null;
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

    @PostMapping("/signup")
    public String SignUp(@RequestBody SignUpData body) {
        try {
            ProfileData data = new ProfileData();

            // custom uid
            data.setUid("007");

//            String token = body.getToken();
//            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
//            String uid = decodedToken.getUid();
//            data.setUid(uid);

            data.setName(body.getName());
            data.setProfileImagePath(UUID.randomUUID().toString() + ".jpg");
            data.setExp(0);
            data.setTier(1);

            var filePath = "D:/profile/" + data.getProfileImagePath();
            FileOutputStream fileStream = new FileOutputStream(filePath);
            var profileImage = body.getProfileImage();
            int cursor = 0;

            while (cursor < profileImage.length) {
                int buffer = 1024;
                if (cursor + buffer > profileImage.length) {
                    buffer = profileImage.length - cursor;
                }

                fileStream.write(profileImage, cursor, buffer);
                cursor += buffer;
            }
            fileStream.write(profileImage, 0, profileImage.length);
            fileStream.close();

            mongoTemplate.insert(data, "profile");

            return "success";
        } catch(Exception e) {
            System.out.println(e);

            return ("fail");
        }
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

            outStream.close();
            fileStream.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
