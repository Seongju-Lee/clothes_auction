package com.example.clothes.domain.auction.domain;

public enum AuctionStatus {

    WAITING("시작전 대기"),
    IN_PROGRESS("진행중"),
    COMPLETED("낙찰"),
    CANCEL("취소");

    private final String status;

    AuctionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public boolean isCompleted() {
        return this == COMPLETED;
    }

    public boolean isInProgress() {
        return this == IN_PROGRESS;
    }
}
