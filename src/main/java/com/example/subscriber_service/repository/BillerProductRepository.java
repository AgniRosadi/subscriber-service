package com.example.subscriber_service.repository;

import com.example.subscriber_service.model.BillerCategory;
import com.example.subscriber_service.model.BillerProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillerProductRepository extends JpaRepository<BillerProduct, Long> {
}
