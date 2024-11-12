package com.apiproject.ServiceBookingSystem.repository;

import com.apiproject.ServiceBookingSystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
