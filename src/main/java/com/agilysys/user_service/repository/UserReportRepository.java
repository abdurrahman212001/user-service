package com.agilysys.user_service.repository;

import com.agilysys.user_service.model.UserReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportRepository extends MongoRepository<UserReport,Integer> {
    UserReport findByJobId(String jobId);
}
