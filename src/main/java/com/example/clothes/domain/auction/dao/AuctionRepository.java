package com.example.clothes.domain.auction.dao;

import com.example.clothes.domain.auction.domain.Auction;
import com.example.clothes.domain.auction.domain.AuctionStatus;
import com.example.clothes.domain.item.domain.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    Auction findByClothes(Clothes clothes);

    @Query("SELECT a FROM Auction a WHERE a.status in :statuses")
    List<Auction> findByStatuses(@Param("statuses") List<AuctionStatus> statuses);
}
