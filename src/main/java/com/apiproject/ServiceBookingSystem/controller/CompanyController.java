package com.apiproject.ServiceBookingSystem.controller;
import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.dto.ReservationDTO;
import com.apiproject.ServiceBookingSystem.enums.ApiErrorCode;
import com.apiproject.ServiceBookingSystem.services.company.CompanyService;
import com.apiproject.ServiceBookingSystem.util.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
            return ResponseUtil.buildErrorResponse(ApiErrorCode.AD_POST_FAILED.getCode(),
                    ApiErrorCode.AD_POST_FAILED.getMessage(), HttpStatus.CONFLICT);

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
            return ResponseUtil.buildErrorResponse(ApiErrorCode.AD_NOT_FOUND.getCode(),
                    ApiErrorCode.AD_NOT_FOUND.getMessage(), HttpStatus.CONFLICT);

        }
    }

    @PutMapping("/update/{addId}")
    public ResponseEntity<?> updateAd(@PathVariable Long addId,  @ModelAttribute AdDTO adDTO) throws IOException {
        boolean success = companyService.updateAd(addId, adDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseUtil.buildErrorResponse(ApiErrorCode.AD_UPDATE_FAILED.getCode(),
                    ApiErrorCode.AD_UPDATE_FAILED.getMessage(), HttpStatus.CONFLICT);        }
    }
    @DeleteMapping("/ad/{adId}")
    public ResponseEntity<?> deletedAd(@PathVariable Long adId){
        boolean success=companyService.deleteAd(adId);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseUtil.buildErrorResponse(ApiErrorCode.AD_DELETE_FAILED.getCode(),
                    ApiErrorCode.AD_DELETE_FAILED.getMessage(), HttpStatus.CONFLICT);        }
    }

    @GetMapping("/bookings/{companyId}")
    public  ResponseEntity<List<ReservationDTO>> getAllAdBookings(@PathVariable long companyId)
    {
        return  ResponseEntity.ok(companyService.getAllAdBookings((companyId)));
    }

    @GetMapping("/booking/{bookingId}/{status}")
    public  ResponseEntity<?> changeBookingStatus(@PathVariable long bookingId,@PathVariable String status)
    {
        boolean success= companyService.changeBookingStatus(bookingId,status);
        if(success) return ResponseEntity.ok().build();
        return  ResponseEntity.notFound().build();
    }
}