package com.example.clothes.domain.bid.dto;

import com.example.clothes.domain.bid.domain.Bid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BidResponse(
        Long bidPrice,
        LocalDateTime bidTime
) {


    public static BidResponse fromEntity(Bid bid) {
        return new BidResponse(bid.getPrice(), bid.getBidTime());
    }

    public static List<BidResponse> fromEntities(List<Bid> bids) {
        return bids.stream()
                .map(BidResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
