package com.apiproject.ServiceBookingSystem.services.client;

import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.dto.AdDetailsForClientDTO;
import com.apiproject.ServiceBookingSystem.dto.ReservationDTO;
import com.apiproject.ServiceBookingSystem.dto.ReviewDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ClientService {
    Page<AdDTO> getAllAds(Pageable pageable);
    Page<AdDTO> searchAdByName(String name, Pageable pageable);

    boolean bookService(ReservationDTO reservationDTO);

    AdDetailsForClientDTO getAdDetailsByAdId(Long adId);
    List<ReservationDTO> getAllBookingsByUserId(long userId);

    Boolean giveReview(ReviewDTO reviewDTO);
}
