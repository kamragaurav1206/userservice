package com.yash.userservice.external.service.feign;

import com.yash.userservice.dto.HotelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelClient {
    @GetMapping("/hotels/{hotelId}")
    HotelDTO getHotel(@PathVariable String hotelId);
}
