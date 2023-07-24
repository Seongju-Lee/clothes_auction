package com.example.clothes.domain.auction.dto;

import com.example.clothes.domain.auction.domain.Auction;
import com.example.clothes.domain.auction.domain.AuctionStatus;
import com.example.clothes.domain.item.domain.Clothes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record AuctionResponse(
        String itemName,
        String itemDescription,
        String itemImg,
        Long startPrice,
        Long currentPrice,
        LocalDateTime startTime,
        AuctionStatus status
){
    public static AuctionResponse fromEntity(Auction auction) {
        Clothes clothes = auction.getClothes();
        System.out.println("clothes = " + clothes);
        return new AuctionResponse(clothes.getName(), clothes.getDescription(), clothes.getImgSrc(),
                auction.getStartPrice(), auction.getCurrentPrice(), auction.getStartTime(), auction.getStatus());
    }

    public static List<AuctionResponse> fromEntities(List<Auction> auctions) {
        return auctions.stream()
                .map(AuctionResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
