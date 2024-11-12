package com.apiproject.ServiceBookingSystem.repository;

import com.apiproject.ServiceBookingSystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCompanyId(long companyId);
    List<Reservation> findAllByUserId(long userId);
}
