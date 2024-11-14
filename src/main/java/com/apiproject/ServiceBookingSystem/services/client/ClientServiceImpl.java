package com.apiproject.ServiceBookingSystem.services.client;

import com.apiproject.ServiceBookingSystem.dto.AdDTO;
import com.apiproject.ServiceBookingSystem.dto.AdDetailsForClientDTO;
import com.apiproject.ServiceBookingSystem.dto.ReservationDTO;
import com.apiproject.ServiceBookingSystem.dto.ReviewDTO;
import com.apiproject.ServiceBookingSystem.entity.Ad;
import com.apiproject.ServiceBookingSystem.entity.Reservation;
import com.apiproject.ServiceBookingSystem.entity.Review;
import com.apiproject.ServiceBookingSystem.entity.User;
import com.apiproject.ServiceBookingSystem.enums.ReservationStatus;
import com.apiproject.ServiceBookingSystem.enums.ReviewStatus;
import com.apiproject.ServiceBookingSystem.repository.AdRepository;
import com.apiproject.ServiceBookingSystem.repository.ReservationRepository;
import com.apiproject.ServiceBookingSystem.repository.ReviewRepository;
import com.apiproject.ServiceBookingSystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService{
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<AdDTO> getAllAds(){
        log.error("Entered get all ads");
        return adRepository.findAll().stream().map(Ad::getAdDTO).collect(Collectors.toList());
    }

    public List<AdDTO> searchAdByName(String name){
        return adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getAdDTO).collect(Collectors.toList());
    }

    public boolean bookService(ReservationDTO reservationDTO){
        Optional<Ad> optionalAd = adRepository.findById(reservationDTO.getAdId());
        Optional<User> optionalUser = userRepository.findById(reservationDTO.getUserId());

        if(optionalAd.isPresent() && optionalUser.isPresent()){
            Reservation reservation = new Reservation();

            reservation.setBookDate(reservationDTO.getBookDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservation.setUser(optionalUser.get());

            reservation.setAd(optionalAd.get());
            reservation.setCompany(optionalAd.get().getUser());
            reservation.setReviewStatus(ReviewStatus.FALSE);

            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public AdDetailsForClientDTO getAdDetailsByAdId(Long adId){
        Optional<Ad> optionalAd = adRepository.findById(adId);
        AdDetailsForClientDTO adDetailsForClientDTO = new AdDetailsForClientDTO();
        if(optionalAd.isPresent()){
            adDetailsForClientDTO.setAdDTO(optionalAd.get().getAdDTO());
        }
        return adDetailsForClientDTO;
    }

    public List<ReservationDTO> getAllBookingsByUserId(long userId)
    {
        return reservationRepository.findAllByUserId(userId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
    }

    public Boolean giveReview(ReviewDTO reviewDTO){
        Optional<User> optionalUser = userRepository.findById(reviewDTO.getUserId());
        log.error("The review id is : "+reviewDTO.getUserId());
        if(reviewDTO.getUserId()!=null) {
            Optional<Reservation> optionalBooking = reservationRepository.findById(reviewDTO.getBookId());

            if (optionalUser.isPresent() && optionalBooking.isPresent()) {
                Review review = new Review();

                review.setReviewDate(new Date());
                review.setReview(reviewDTO.getReview());
                review.setRating(reviewDTO.getRating());

                review.setUser(optionalUser.get());
                review.setAd(optionalBooking.get().getAd());

                reviewRepository.save(review);

                Reservation booking = optionalBooking.get();
                booking.setReviewStatus(ReviewStatus.TRUE);


                reservationRepository.save(booking);

                return true;
            }
        }
        return false;
    }
}
