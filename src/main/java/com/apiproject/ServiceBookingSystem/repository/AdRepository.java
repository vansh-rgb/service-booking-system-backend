package com.apiproject.ServiceBookingSystem.repository;

import com.apiproject.ServiceBookingSystem.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apiproject.ServiceBookingSystem.dto.AdDTO;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> { List<Ad> findAllByUserId(long userId);

}
