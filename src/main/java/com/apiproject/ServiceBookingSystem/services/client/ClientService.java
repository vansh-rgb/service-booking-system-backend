package com.apiproject.ServiceBookingSystem.services.client;

import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.dto.AdDetailsForClientDTO;
import com.apiproject.ServiceBookingSystem.dto.ReservationDTO;

import java.util.List;

public interface ClientService {
    List<AdDTO> getAllAds();
    List<AdDTO> searchAdByName(String name);

    boolean bookService(ReservationDTO reservationDTO);

    AdDetailsForClientDTO getAdDetailsByAdId(Long adId);
    List<ReservationDTO> getAllBookingsUserId(long userId);

}
