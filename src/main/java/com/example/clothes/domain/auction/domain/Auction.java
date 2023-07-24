package com.example.clothes.domain.auction.domain;

import com.example.clothes.domain.item.domain.Clothes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

    protected Auction() {}
}