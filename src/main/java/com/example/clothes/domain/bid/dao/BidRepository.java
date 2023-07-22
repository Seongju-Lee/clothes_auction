package com.example.clothes.domain.bid.dao;

import com.example.clothes.domain.auction.domain.Auction;
import com.example.clothes.domain.bid.domain.Bid;
import com.example.clothes.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByBidder(User bidder);
    List<Bid> findByAuction(Auction auction);
}
