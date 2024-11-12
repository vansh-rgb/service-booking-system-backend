package com.apiproject.ServiceBookingSystem.services.company;

import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.dto.AdDetailsForClientDTO;
import com.apiproject.ServiceBookingSystem.dto.ReservationDTO;
import com.apiproject.ServiceBookingSystem.entity.Ad;
import com.apiproject.ServiceBookingSystem.entity.Reservation;
import com.apiproject.ServiceBookingSystem.entity.User;
import com.apiproject.ServiceBookingSystem.enums.ReservationStatus;
import com.apiproject.ServiceBookingSystem.repository.AdRepository;
import com.apiproject.ServiceBookingSystem.repository.ReservationRepository;
import com.apiproject.ServiceBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdRepository adRepository;
    @Autowired
    private ReservationRepository reservationRepository;


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

    public boolean updateAd(long adId, AdDTO adDTO) throws IOException {
        Optional<Ad> optionalAd =adRepository.findById(adId);
        if(optionalAd.isPresent())
        {
            Ad ad =optionalAd.get();
            ad.setServiceName(adDTO.getServiceName());
            ad.setDescription(adDTO.getDescription());
            ad.setPrice(adDTO.getPrice());

            if(adDTO.getImg()!=null)
            {
                ad.setImg(adDTO.getImg().getBytes());
            }
            adRepository.save(ad);
            return  true;
        }
        return false;
    }

    public boolean deleteAd(long AdId){
        Optional<Ad> optionalAd=adRepository.findById(AdId);
        if(optionalAd.isPresent()){
            adRepository.delete(optionalAd.get());
            return true;
        }
        return false;
    }

    public List<ReservationDTO> getAllAdBookings(long companyId)
    {
        return reservationRepository.findAllByCompanyId(companyId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
    }

    public boolean changeBookingStatus(long bookingId,String status)
    {
        Optional<Reservation> optionalReservation = reservationRepository.findById(bookingId);
        if(optionalReservation.isPresent())
        {
            Reservation existingReservation = optionalReservation.get();
            if(Objects.equals(status,"Approve"))
            {
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);
            }
            else {
                existingReservation.setReservationStatus(ReservationStatus.REJECTED);
            }
            reservationRepository.save(existingReservation);
            return true;
        }
        return false;
    }






}
