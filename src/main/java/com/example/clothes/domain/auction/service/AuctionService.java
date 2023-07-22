package com.example.clothes.domain.auction.service;

import com.example.clothes.domain.auction.dao.AuctionRepository;
import com.example.clothes.domain.auction.domain.Auction;
import com.example.clothes.domain.auction.domain.AuctionBuilder;
import com.example.clothes.domain.auction.domain.AuctionStatus;
import com.example.clothes.domain.auction.dto.AuctionRequest;
import com.example.clothes.domain.auction.dto.AuctionResponse;
import com.example.clothes.domain.auction.dto.AuctionUpdateRequest;
import com.example.clothes.domain.item.dao.ClothesRepository;
import com.example.clothes.domain.item.domain.Clothes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ClothesRepository clothesRepository;

    // 새로운 경매 생성
    public AuctionResponse save(AuctionRequest request) {
        Clothes clothes = clothesRepository.findById(request.clothesId()).get();
        Auction auction = new Auction(clothes,request.startPrice(),request.startTime());
        Auction savedAuction = auctionRepository.save(auction);
        return AuctionResponse.fromEntity(savedAuction);
    }

    // 상품 아이디로 경매 조회
    public AuctionResponse findByClothesId(Long clothesId) {
        Clothes clothes = clothesRepository.findById(clothesId).get();
        Auction auction = auctionRepository.findByClothes(clothes);
        return AuctionResponse.fromEntity(auction);
    }

    // 경매 아이디로 경매 조회
    public AuctionResponse findById(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).get();
        return AuctionResponse.fromEntity(auction);
    }

    // 경매 상태별 조회(시작 전, 진행 중, 낙찰 완료)
    public List<AuctionResponse> findByStatuses(List<AuctionStatus> statuses) {
        List<Auction> auctions = auctionRepository.findByStatuses(statuses);
        return AuctionResponse.fromEntities(auctions);
    }

    // 모든 경매 조회
    public List<AuctionResponse> findAll() {
        List<Auction> auctions = auctionRepository.findAll();
        return AuctionResponse.fromEntities(auctions);
    }


    // 업데이트
    public AuctionResponse update(AuctionUpdateRequest request, Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).get();
        Auction updatedAuction = new AuctionBuilder()
                .status(request.status())
                .startPrice(request.startPrice())
                .startTime(request.startTime())
                .currentPrice(request.currentPrice())
                .build(auction);
        return AuctionResponse.fromEntity(auctionRepository.save(updatedAuction));
    }

    // 경매 삭제
    public void delete(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).get();
        auctionRepository.delete(auction);
    }
}
