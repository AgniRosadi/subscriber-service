package com.example.subscriber_service.service;


import com.api.common.dto.PrefixDataReq;
import com.api.common.dto.PrefixDataRes;
import com.example.subscriber_service.model.BillerCategory;
import com.example.subscriber_service.model.BillerPrefix;
import com.example.subscriber_service.model.BillerProduct;
import com.example.subscriber_service.repository.BillerCategoryRepository;
import com.example.subscriber_service.repository.BillerPrefixRepository;
import com.example.subscriber_service.repository.BillerProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.api.common.constant.RedisConstant.REDIS_PREFIX_CACHE;


@Slf4j
@Service
public class PrefixService {

    @Autowired
    private BillerPrefixRepository prefixRepository;

    @Autowired
    private BillerCategoryRepository billerCategoryRepository;

    @Autowired
    private BillerProductRepository billerProductRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    HashOperations<String, String, Object> redisOps;

    public List<PrefixDataRes> loadPrefixData() {
        try {
            List<Object[]> rows = prefixRepository.findPrefixData();
            log.info("Fetched prefix data from DB. Total rows: {}", rows.size());

            List<PrefixDataRes> result = new ArrayList<>();

            redisOps.getOperations().delete(REDIS_PREFIX_CACHE.getName());

            for (Object[] row : rows) {
                String prefix = (String) row[0];
                Long categoryId = ((Number) row[1]).longValue();
                Boolean isHandler = (Boolean) row[2];

                PrefixDataRes data = new PrefixDataRes(prefix, categoryId, isHandler);

                // Simpan ke Redis
                redisOps.put(REDIS_PREFIX_CACHE.getName(), prefix, data);
                result.add(data);
            }

            log.info("Prefix data successfully loaded to Redis. Total entries: {}", result.size());
            return result;

        } catch (Exception e) {
            log.error("Failed to load prefix data to Redis", e);
            throw new RuntimeException("Failed to load prefix data", e);
        }
    }

    public void savePrefix(PrefixDataReq req) {
        log.info("Start savePrefix");
        BillerCategory category = billerCategoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        BillerProduct product = billerProductRepository.findById(req.getBillerId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        BillerPrefix prefix = new BillerPrefix();
        prefix.setPrefix(req.getPrefix());
        prefix.setBillerCategory(category);
        prefix.setBillerProduct(product);

        prefixRepository.save(prefix);
        refreshPrefixData();
    }

    @Transactional
    public void updatePrefix(PrefixDataReq req) {
        log.info("Start updatePrefix");
        Optional<BillerPrefix> prefixOld = prefixRepository.findById(req.getId());

        if (prefixOld.isEmpty()) {
            throw new IllegalArgumentException("Prefix ID " + req.getId() + " not found");
        }

        BillerPrefix prefix = prefixOld.get();

        BillerCategory category = billerCategoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        BillerProduct product = billerProductRepository.findById(req.getBillerId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        prefix.setPrefix(req.getPrefix());
        prefix.setBillerCategory(category);
        prefix.setBillerProduct(product);
        prefixRepository.save(prefix);
        refreshPrefixData();
    }

    public void refreshPrefixData() {
        log.info("Start refreshPrefixData");
        loadPrefixData();
        redisTemplate.convertAndSend(REDIS_PREFIX_CACHE.getName(), "refresh");
    }
}