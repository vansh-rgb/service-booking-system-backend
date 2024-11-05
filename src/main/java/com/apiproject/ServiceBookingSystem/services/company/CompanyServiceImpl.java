package com.apiproject.ServiceBookingSystem.services.company;

import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.entity.Ad;
import com.apiproject.ServiceBookingSystem.entity.User;
import com.apiproject.ServiceBookingSystem.repository.AdRepository;
import com.apiproject.ServiceBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdRepository adRepository;


    public boolean postAd(Long userId, AdDTO adDTO) throws IOException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent())
        {
            Ad ad = new Ad();
            ad.setServiceName(adDTO.getServiceName());
            ad.setDescription(adDTO.getDescription());
            ad.setImg(adDTO.getImg().getBytes());
            ad.setPrice(adDTO.getPrice());
            ad.setUser(optionalUser.get());

            adRepository.save(ad);
            return true;
        }
        return false;
    }

    public List<AdDTO> getAllAds(long userId) {
        return adRepository.findAllByUserId(userId)
                .stream()
                .map(Ad::getAdDTO)
                .collect(Collectors.toList());
    }

    public AdDTO getAdById(long adId)
    {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if(optionalAd.isPresent())
        {
            return optionalAd.get().getAdDTO();
        }
        return null;
    }
}
