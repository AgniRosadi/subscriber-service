package com.example.subscriber_service.repository;

import com.example.subscriber_service.model.BillerCategory;
import com.example.subscriber_service.model.BillerPrefix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillerCategoryRepository extends JpaRepository<BillerCategory, Long> {
}
