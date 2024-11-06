package com.apiproject.ServiceBookingSystem.controller;
import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.services.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @PostMapping("/ad/{userId}")
    public ResponseEntity<?> postAd(@PathVariable Long userId, @ModelAttribute AdDTO adDTO) throws IOException {
        boolean success = companyService.postAd(userId, adDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/ads/{userId}")
    public ResponseEntity<?> getAllAdsByUserId(@PathVariable long userId)
    {
        return ResponseEntity.ok(companyService.getAllAds(userId));
    }

    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> getAdById(@PathVariable long adId)
    {
        AdDTO adDTO= companyService.getAdById(adId);
        if(adDTO!=null)
        {
            return ResponseEntity.ok(adDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
//    @PutMapping("/update/{addId}")
//    public ResponseEntity<String> updateAd(@PathVariable Long addId, @RequestBody Map<String, String> payload) {
//        String name = payload.get("name");
//        // Log or print the name to verify it's being received
//        System.out.println("Received name: " + name + " for addId: " + addId);
//
//        // Just a simple response to indicate success
//        return ResponseEntity.status(HttpStatus.OK).body("Ad updated successfully for ID: " + addId);
//    }

    @PutMapping("/update/{addId}")
    public ResponseEntity<?> updateAd(@PathVariable Long addId,  @ModelAttribute AdDTO adDTO) throws IOException {
        boolean success = companyService.updateAd(addId, adDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}