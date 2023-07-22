package com.example.clothes.domain.bid.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record BidRequest(Long bidderId,
                         Long auctionId,
                         Long bidPrice,
                         @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime bidTime
) {
}
