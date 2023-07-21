package com.example.clothes.domain.auction.domain;

public enum AuctionStatus {

    WAITING("시작전 대기"),
    IN_PROGRESS("진행중"),
    COMPLETED("낙찰");

    private final String status;

    AuctionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
