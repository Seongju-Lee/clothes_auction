package com.example.clothes.domain.auction.dto;

import com.example.clothes.domain.auction.domain.AuctionStatus;

import java.time.LocalDateTime;

public record AuctionUpdateRequest(
        Long startPrice,
        LocalDateTime startTime,
        AuctionStatus status,
        Long currentPrice
){
}
