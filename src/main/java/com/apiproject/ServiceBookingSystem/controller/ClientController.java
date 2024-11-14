package com.apiproject.ServiceBookingSystem.controller;

import com.apiproject.ServiceBookingSystem.dto.ReservationDTO;
import com.apiproject.ServiceBookingSystem.dto.ReviewDTO;
import com.apiproject.ServiceBookingSystem.services.client.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;

@Slf4j
@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/ads")
    public ResponseEntity<?> getAllAds(){
        return ResponseEntity.ok(clientService.getAllAds());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchAdByService(@PathVariable String name){
        return ResponseEntity.ok(clientService.searchAdByName(name));
    }

    @PostMapping("/book-service")
    public ResponseEntity<?> bookService(@RequestBody ReservationDTO reservationDTO)
    {
        boolean success = clientService.bookService(reservationDTO);
        if(success)
        {
            return ResponseEntity.status(HttpStatus.OK).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> getAdDetailsByAdId(@PathVariable Long adId){
        return ResponseEntity.ok(clientService.getAdDetailsByAdId(adId));
    }

    @GetMapping("/my-bookings/{userId}")
    public ResponseEntity<?> getAllBookingsByUserId(@PathVariable long userId)
    {
        return ResponseEntity.ok(clientService.getAllBookingsByUserId(userId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@RequestBody ReviewDTO reviewDTO){
        log.error("reviewDTO id is :"+reviewDTO.getUserId());
        Boolean success = clientService.giveReview(reviewDTO);

        if(success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
