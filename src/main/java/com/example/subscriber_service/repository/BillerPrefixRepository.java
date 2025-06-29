package com.example.subscriber_service.repository;

import com.example.subscriber_service.model.BillerPrefix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillerPrefixRepository extends JpaRepository<BillerPrefix, Long> {
    @Query(value = """
            SELECT bp.prefix, bc.id, bp2.is_handler
            FROM biller_prefix bp
            JOIN biller_category bc ON bp.id_category = bc.id
            JOIN biller_product bp2 ON bp.id_biller = bp2.id
            WHERE bc.category_type = 1
            """, nativeQuery = true)
    List<Object[]> findPrefixData();

}
