package com.example.clothes.domain.auction.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuctionBuilder {

    private Long startPrice;
    private LocalDateTime startTime;
    private AuctionStatus status;
    private Long currentPrice;

    public AuctionBuilder() {
    }

    public AuctionBuilder startPrice(Long startPrice) {
        this.startPrice = startPrice;
        return this;
    }

    public AuctionBuilder startTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public AuctionBuilder status(AuctionStatus status) {
        this.status = status;
        return this;
    }

    public AuctionBuilder currentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    // 업데이트를 위해 빌더 객체를 생성하는 메서드
    public Auction build(Auction auction) {
        return auction.update(this);
    }
}
