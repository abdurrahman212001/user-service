package com.agilysys.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReport {

    private int id;
    private String jobId;
    private String status;
    private byte[] fileContent;}
