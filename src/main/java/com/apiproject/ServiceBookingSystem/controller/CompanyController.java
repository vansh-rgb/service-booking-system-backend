package com.apiproject.ServiceBookingSystem.controller;
import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.dto.ReservationDTO;
import com.apiproject.ServiceBookingSystem.entity.Ad;
import com.apiproject.ServiceBookingSystem.enums.ApiErrorCode;
import com.apiproject.ServiceBookingSystem.services.company.CompanyService;
import com.apiproject.ServiceBookingSystem.util.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
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
@RequestMapping("/api/v1/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    //change here
    @PostMapping("/ad/{userId}")
    public ResponseEntity<?> postAd(@PathVariable Long userId, @ModelAttribute AdDTO adDTO) throws IOException {
        if (!userId.equals(adDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Mismatch between userId in path and payload. Path userId: " + userId +
                            ", Payload userId: " + adDTO.getUserId());
        }
        try {
            Ad createdAd = companyService.postAd(userId, adDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.buildErrorResponse(ApiErrorCode.AD_POST_FAILED.getCode(),
                    ApiErrorCode.AD_POST_FAILED.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the ad.");
        }
    }
    @GetMapping("/ads/{userId}")
    public ResponseEntity<?> getAllAdsByUserId(@PathVariable long userId)
    {
        List<AdDTO> ads = companyService.getAllAds(userId);

        if (ads.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No ads found for the user with ID: " + userId);
        }

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
                    ApiErrorCode.AD_NOT_FOUND.getMessage(),HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping("/update/{addId}")
    public ResponseEntity<?> updateAd(@PathVariable Long addId,  @ModelAttribute AdDTO adDTO) throws IOException {
        boolean success = companyService.updateAd(addId, adDTO);
        if (success) {
            AdDTO updatedAd = companyService.getAdById(addId); // Fetch updated ad if necessary
            return ResponseEntity.status(HttpStatus.OK).body(updatedAd);
        } else {
            return ResponseUtil.buildErrorResponse(ApiErrorCode.AD_UPDATE_FAILED.getCode(),
                    ApiErrorCode.AD_UPDATE_FAILED.getMessage(), HttpStatus.NOT_FOUND);        }
    }

    @DeleteMapping("/delete/{adId}")
    public ResponseEntity<?> deletedAd(@PathVariable Long adId){
        boolean success=companyService.deleteAd(adId);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).body("Advertisement deleted successfully.");

        }
        else{
            return ResponseUtil.buildErrorResponse(ApiErrorCode.AD_DELETE_FAILED.getCode(),
                    ApiErrorCode.AD_DELETE_FAILED.getMessage(), HttpStatus.BAD_REQUEST);        }
    }

    @GetMapping("/bookings/{companyId}")
    public  ResponseEntity<?> getAllAdBookings(@PathVariable long companyId)
    {
        try {
            List<ReservationDTO> bookings = companyService.getAllAdBookings(companyId);

            // If no bookings are found, return a not found error
            if (bookings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found for the company with ID: " + companyId);
            }

            // Return list of bookings if found
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            // Generic exception handler for other unforeseen errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the bookings.");
        }
    }

    @GetMapping("/booking/{bookingId}/{status}")
    public  ResponseEntity<?> changeBookingStatus(@PathVariable long bookingId,@PathVariable String status,HttpServletResponse response) throws JSONException, IOException {
        boolean success= companyService.changeBookingStatus(bookingId,status);
        if(success)
        {
            response.getWriter().write(new JSONObject()
                    .put("message", "\t\n" +
                            "Booking status updated successfully.")
                    .toString());
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No bookings found for this Booking Id: " + bookingId);
        }
    }
}