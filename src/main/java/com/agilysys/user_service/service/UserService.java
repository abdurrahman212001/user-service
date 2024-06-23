package com.agilysys.user_service.service;

import com.agilysys.user_service.model.UserEntity;
import com.agilysys.user_service.model.UserReport;
import com.agilysys.user_service.repository.UserReportRepository;
import com.agilysys.user_service.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserReportRepository userReportRepository;

    public static final String TIME_STAMP = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    public static final String FILE_NAME = "AGILYSYS_USER_REPORT_" + TIME_STAMP + ".json";

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String FILE_NAME_HEADER = "user_report.json";

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public void startJob() {
        UserReport userReport = new UserReport();
        String jobId = UUID.randomUUID().toString();
        userReport.setJobId(jobId);
        userReport.setStatus("ACCEPTED");

        List<UserEntity> users = userRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] jsonData = objectMapper.writeValueAsBytes(users);
            userReport.setFileContent(jsonData);
            userReport.setStatus("SUCCESS");
        } catch (Exception e) {
            logger.error("Exception occurred while writing JSON data: ", e);
            userReport.setStatus("FAILED");
        }

        userReportRepository.save(userReport);
    }

    public ResponseEntity<byte[]> download(String jobId) {
        UserReport userReport = userReportRepository.findByJobId(jobId);
        if (userReport == null) {
            return ResponseEntity.notFound().build();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonBytes;
        List<UserEntity> users=new ArrayList<>();
        File outputFile = new File(FILE_NAME);
        byte[] fileContent=null;
        try {
            users = objectMapper.readValue(userReport.getFileContent(), new TypeReference<List<UserEntity>>() {});
//            jsonBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(users);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, users);
            // Read file content into byte array
            fileContent = Files.readAllBytes(outputFile.toPath());


        } catch (Exception e) {
            logger.error("Exception occurred while reading JSON data: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", outputFile.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileContent);
    }
}
