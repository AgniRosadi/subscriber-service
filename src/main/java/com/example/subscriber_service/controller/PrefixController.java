package com.example.subscriber_service.controller;

import com.example.subscriber_service.service.PrefixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.common.dto.*;

import java.util.List;

@RestController
@RequestMapping("/prefix")
public class PrefixController {

    @Autowired
    private PrefixService prefixService;

    @GetMapping("/check")
    public List<PrefixDataRes> checkPrefix() {
        return prefixService.loadPrefixData();
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshPrefix() {
        prefixService.refreshPrefixData();
        return ResponseEntity.ok("Prefix data refreshed and broadcasted");
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePrefix(@RequestBody PrefixDataReq req) {
        prefixService.savePrefix(req);
        return ResponseEntity.ok("Prefix saved");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updatePrefix(@RequestBody PrefixDataReq req) {
        prefixService.updatePrefix(req);
        return ResponseEntity.ok("Prefix Updated");
    }

}
