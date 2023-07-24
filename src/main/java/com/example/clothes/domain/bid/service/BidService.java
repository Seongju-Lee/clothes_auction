package com.example.clothes.domain.bid.service;

import com.example.clothes.domain.auction.dao.AuctionRepository;
import com.example.clothes.domain.auction.domain.Auction;
import com.example.clothes.domain.bid.dao.BidRepository;
import com.example.clothes.domain.bid.domain.Bid;
import com.example.clothes.domain.bid.dto.BidInfoResponse;
import com.example.clothes.domain.bid.dto.BidRequest;
import com.example.clothes.domain.bid.dto.BidResponse;
import com.example.clothes.domain.user.dao.UserRepository;
import com.example.clothes.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BidService {

    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    // 입찰 로그 추가
    public BidResponse save(BidRequest request) {
        User bidder = userRepository.findById(request.bidderId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다"));

        Auction findAuction = auctionRepository.findById(request.auctionId())
                .filter(auction ->  LocalDateTime.now().isAfter(auction.getStartTime())
                        && auction.getStatus().isInProgress())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않거나 종료된 경매입니다."));

        Bid savedBid = bidRepository.save(new Bid(bidder, findAuction, request.bidPrice(), request.bidTime()));
        return BidResponse.fromEntity(savedBid);
    }

    public List<BidInfoResponse> findByCriteria(Long bidderId, Long auctionId) {
        if (bidderId != null && auctionId != null) {
            throw new IllegalArgumentException("올바르지 못한 형태의 요청입니다.");
        } else if (bidderId != null) {
            return findByBidderId(bidderId);
        } else if (auctionId != null) {
            return findByAuctionId(auctionId);
        }
        return findAll();
    }

    // 모든 입찰 정보 가져오기
    private List<BidInfoResponse> findAll() {
        List<Bid> bids = bidRepository.findAll();
        return BidInfoResponse.fromEntities(bids);
    }

    // 유저가 입찰한 로그 가져오기
    private List<BidInfoResponse> findByBidderId(Long bidderId) {
        User bidder = userRepository.findById(bidderId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));
        List<Bid> bids = bidRepository.findByBidder(bidder);
        return BidInfoResponse.fromEntities(bids);
    }

    // 특정 경매의 입찰 로그 모두 가져오기
    private List<BidInfoResponse> findByAuctionId(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 경매입니다."));
        List<Bid> bids = bidRepository.findByAuction(auction);
        return BidInfoResponse.fromEntities(bids);
    }
}
