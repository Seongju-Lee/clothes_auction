package com.example.clothes.domain.auction.domain;

import com.example.clothes.domain.item.domain.Clothes;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter

public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long auctionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Clothes clothes;

    @Column(name = "auction_start_price")
    private Long startPrice;

    @Column(name = "auction_current_price")
    private Long currentPrice;

    @Column(name = "auction_start_time")

    private LocalDateTime startTime;

    @Column(name = "auction_status")
    @Enumerated(EnumType.STRING)
    private AuctionStatus status = AuctionStatus.WAITING;

    protected Auction() {
    }

    public Auction(Clothes clothes, Long startPrice, LocalDateTime startTime) {
        this.clothes = clothes;
        this.startPrice = startPrice;
        this.startTime = startTime;
    }

    public Auction update(AuctionBuilder builder) {
        if (builder.getStatus() != null) {
            this.status = builder.getStatus();
        }
        if (builder.getStartPrice() != null) {
            this.startPrice = builder.getStartPrice();
        }
        if (builder.getCurrentPrice() != null) {
            this.currentPrice = builder.getCurrentPrice();
        }
        if (builder.getStartTime() != null) {
            this.startTime = builder.getStartTime();
        }
        return this;
    }
}