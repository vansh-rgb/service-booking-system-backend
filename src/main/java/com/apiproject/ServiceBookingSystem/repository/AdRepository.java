package com.apiproject.ServiceBookingSystem.repository;

import com.apiproject.ServiceBookingSystem.entity.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apiproject.ServiceBookingSystem.dto.AdDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findAllByUserId(long userId);
    Page<Ad> findAllByServiceNameContaining(String name, Pageable pageable);
}
