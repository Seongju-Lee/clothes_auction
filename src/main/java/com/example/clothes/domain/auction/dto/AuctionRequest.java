package com.example.clothes.domain.auction.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record AuctionRequest(
        Long clothesId,
        Long startPrice,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startTime
){
}
