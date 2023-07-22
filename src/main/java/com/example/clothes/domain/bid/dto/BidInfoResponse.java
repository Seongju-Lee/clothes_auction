package com.example.clothes.domain.bid.dto;

import com.example.clothes.domain.auction.domain.Auction;
import com.example.clothes.domain.bid.domain.Bid;
import com.example.clothes.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BidInfoResponse(
        String bidderName,
        String itemName,
        Long bidPrice,
        LocalDateTime bidTime
) {

    public static BidInfoResponse fromEntity(Bid bid) {
        User bidder = bid.getBidder();
        Auction auction = bid.getAuction();
        return new BidInfoResponse(bidder.getName(), auction.getClothes().getName(), bid.getPrice(), bid.getBidTime());
    }

    public static List<BidInfoResponse> fromEntities(List<Bid> bids) {
        return bids.stream()
                .map(BidInfoResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
