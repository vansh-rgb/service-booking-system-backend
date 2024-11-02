package com.apiproject.ServiceBookingSystem.services.company;

import com.apiproject.ServiceBookingSystem.dto.AdDTO;

import java.io.IOException;

public interface CompanyService {

    boolean postAd(Long userId, AdDTO adDTO) throws IOException;
}
