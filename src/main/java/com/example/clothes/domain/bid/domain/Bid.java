package com.example.clothes.domain.bid.domain;

import com.example.clothes.domain.auction.domain.Auction;
import com.example.clothes.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    private Long bidId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User bidder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @Column(name = "bid_price")
    private Long price;

    @Column(name = "bid_time")
    private LocalDateTime bidTime;

    protected Bid() {
    }

    public Bid(User bidder, Auction auction, Long price, LocalDateTime bidTime) {
        this.bidder = bidder;
        this.auction = auction;
        this.price = price;
        this.bidTime = bidTime;
    }
}
