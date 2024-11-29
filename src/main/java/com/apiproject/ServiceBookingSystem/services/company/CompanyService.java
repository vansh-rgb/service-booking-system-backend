package com.apiproject.ServiceBookingSystem.services.company;

import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.dto.ReservationDTO;
import com.apiproject.ServiceBookingSystem.entity.Ad;

import java.io.IOException;
import java.util.List;

public interface CompanyService {

    Ad postAd(Long userId, AdDTO adDTO) throws IOException;
    List<AdDTO> getAllAds(long userId) ;
    AdDTO getAdById(long adId);
    boolean updateAd(long adId,AdDTO adDTO) throws  IOException;
    boolean deleteAd(long adId);
    List<ReservationDTO> getAllAdBookings(long companyId);
    boolean changeBookingStatus(long bookingId,String status);
}
