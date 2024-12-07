package com.apiproject.ServiceBookingSystem.controller;

import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.dto.ReservationDTO;
import com.apiproject.ServiceBookingSystem.dto.ReviewDTO;
import com.apiproject.ServiceBookingSystem.enums.ApiErrorCode;
import com.apiproject.ServiceBookingSystem.exceptions.BadRequestException;
import com.apiproject.ServiceBookingSystem.exceptions.ConflictException;
import com.apiproject.ServiceBookingSystem.exceptions.ResourceNotFoundException;
import com.apiproject.ServiceBookingSystem.services.client.ClientService;
import com.apiproject.ServiceBookingSystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("client/ads")
    public ResponseEntity<Page<AdDTO>> getAllAds(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<AdDTO> ads = clientService.getAllAds(pageable);
            return ResponseEntity.ok(ads);
        } catch (Exception e) {
        }            throw new RuntimeException("An error occurred while fetching ads.");

    }


    @GetMapping("ads/search/{name}")
    public ResponseEntity<Page<AdDTO>> searchAdByService(
            @PathVariable String name,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AdDTO> ads = clientService.searchAdByName(name, pageable);
        if (ads.isEmpty()) {
            throw new ResourceNotFoundException("No ads found for service name: " + name);
        }
        return ResponseEntity.ok(ads);
    }


    @PostMapping("client/book-service")
    public ResponseEntity<?> bookService(@RequestBody ReservationDTO reservationDTO) {
        try {
            // Call the service layer method
            String result = clientService.bookService(reservationDTO);

            // Handle success and specific error messages
            switch (result) {
                case "OK":
                    return ResponseUtil.buildErrorResponse(ApiErrorCode.CLIENT_BOOKING_FAILED.getCode(),
                            ApiErrorCode.CLIENT_BOOKING_FAILED.getMessage(), HttpStatus.OK);
//                return ResponseEntity.status(HttpStatus.OK).body("Booking successful.");
                case "Ad not Present":
                    return ResponseUtil.buildErrorResponse(ApiErrorCode.AD_NOT_FOUND.getCode(),
                        ApiErrorCode.AD_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                case "User not Present":
                    return ResponseUtil.buildErrorResponse(ApiErrorCode.CLIENT_DOES_NOT_EXIST.getCode(),
                        ApiErrorCode.CLIENT_DOES_NOT_EXIST.getMessage(), HttpStatus.NOT_FOUND);
                case "Conflict: Booking already exists for the given ad and date.":
                    return ResponseUtil.buildErrorResponse(ApiErrorCode.BOOKING_EXIST.getCode(),
                        ApiErrorCode.BOOKING_EXIST.getMessage(), HttpStatus.CONFLICT);
                default:
                    return ResponseUtil.buildErrorResponse(ApiErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        ApiErrorCode.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.OK);
                    // Catch any unexpected messages
            }
        } catch (Exception e) {
            // General exception handling (optional for unexpected scenarios)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }


    @GetMapping("client/ad/{adId}")
    public ResponseEntity<?> getAdDetailsByAdId(@PathVariable Long adId) {
        try {
        var adDetails = clientService.getAdDetailsByAdId(adId);
        if (adDetails == null) {
            throw new ResourceNotFoundException("Ad with ID " + adId + " not found.");
        }

        return ResponseEntity.ok(adDetails);
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    }

    @GetMapping("client/my-bookings/{userId}")
    public ResponseEntity<?> getAllBookingsByUserId(@PathVariable long userId) {
        var bookings = clientService.getAllBookingsByUserId(userId);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for user ID: " + userId);
        }
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("client/review")
    public ResponseEntity<?> giveReview(@RequestBody ReviewDTO reviewDTO) {
        log.info("Review submission request by userId: " + reviewDTO.getUserId());
        boolean success = clientService.giveReview(reviewDTO);
        if (!success) {
            throw new ConflictException("Review submission failed. Review may already exist.");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
