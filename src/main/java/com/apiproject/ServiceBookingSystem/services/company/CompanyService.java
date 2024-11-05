package com.apiproject.ServiceBookingSystem.services.company;

import com.apiproject.ServiceBookingSystem.dto.AdDTO;

import java.io.IOException;
import java.util.List;

public interface CompanyService {

    boolean postAd(Long userId, AdDTO adDTO) throws IOException;
    List<AdDTO> getAllAds(long userId) ;
    AdDTO getAdById(long adId);
}
