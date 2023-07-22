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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BidService {

    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    // 입찰 로그 추가
    public BidResponse save(BidRequest request) {
        User bidder = userRepository.findById(request.bidderId()).get();
        Auction auction = auctionRepository.findById(request.auctionId()).get();
        Bid savedBid = bidRepository.save(new Bid(bidder, auction, request.bidPrice(), request.bidTime()));
        return BidResponse.fromEntity(savedBid);
    }

    public List<BidInfoResponse> findByCriteria(Long bidderId, Long auctionId) {
        if (auctionId != null && bidderId != null) {
            return findAll();
        } else if (auctionId != null) {
            return findByAuctionId(auctionId);
        } else if (bidderId != null) {
            return findByBidderId(bidderId);
        }
        throw new IllegalArgumentException("올바르지 못한 형태의 요청입니다.");
    }


    // 모든 입찰 정보 가져오기
    private List<BidInfoResponse> findAll() {
        List<Bid> bids = bidRepository.findAll();
        return BidInfoResponse.fromEntities(bids);
    }

    // 유저가 입찰한 로그 가져오기
    private List<BidInfoResponse> findByBidderId(Long bidderId) {
        User bidder = userRepository.findById(bidderId).get();
        List<Bid> bids = bidRepository.findByBidder(bidder);
        return BidInfoResponse.fromEntities(bids);
    }

    // 특정 경매의 입찰 로그 모두 가져오기
    private List<BidInfoResponse> findByAuctionId(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).get();
        List<Bid> bids = bidRepository.findByAuction(auction);
        return BidInfoResponse.fromEntities(bids);
    }
}
