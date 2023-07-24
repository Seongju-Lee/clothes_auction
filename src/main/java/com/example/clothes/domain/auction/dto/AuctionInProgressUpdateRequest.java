package com.example.clothes.domain.auction.dto;

import com.example.clothes.domain.auction.domain.AuctionStatus;

public record AuctionInProgressUpdateRequest(Long auctionId, AuctionStatus status, Long currentPrice) {

}
