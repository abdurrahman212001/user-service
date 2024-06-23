package com.agilysys.user_service.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    private int userid;
    private String userName;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
