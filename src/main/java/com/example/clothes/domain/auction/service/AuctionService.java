package com.example.clothes.domain.auction.service;

import com.example.clothes.domain.auction.dao.AuctionRepository;
import com.example.clothes.domain.auction.domain.Auction;
import com.example.clothes.domain.auction.domain.AuctionStatus;
import com.example.clothes.domain.auction.dto.AuctionRequest;
import com.example.clothes.domain.auction.dto.AuctionResponse;
import com.example.clothes.domain.auction.dto.AuctionUpdateRequest;
import com.example.clothes.domain.bid.dao.BidRepository;
import com.example.clothes.domain.bid.domain.Bid;
import com.example.clothes.domain.item.dao.ClothesRepository;
import com.example.clothes.domain.item.domain.Clothes;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ClothesRepository clothesRepository;
    private final BidRepository bidRepository;

    // 새로운 경매 생성
    public AuctionResponse save(AuctionRequest request) {
        Clothes clothes = clothesRepository.findById(request.clothesId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
        Auction auction = Auction.builder()
                .clothes(clothes)
                .startPrice(request.startPrice())
                .currentPrice(request.startPrice())
                .startTime(request.startTime())
                .status(AuctionStatus.WAITING)
                .build();
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
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 경매입니다."));

        Auction updatedAuction = Auction.builder()
                .auctionId(auction.getAuctionId())
                .clothes(auction.getClothes())
                .startPrice(request.startPrice() != null ? request.startPrice() : auction.getStartPrice())
                .startTime(request.startTime() != null ? request.startTime() : auction.getStartTime())
                .status(request.status() != null ? request.status() : auction.getStatus())
                .currentPrice(request.currentPrice() != null ? request.currentPrice() : auction.getCurrentPrice())
                .build();
        return AuctionResponse.fromEntity(auctionRepository.save(updatedAuction));
    }

    // 경매 삭제
    public void delete(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).get();
        auctionRepository.delete(auction);
    }

    @Scheduled(cron = "0 * * * * *")
    public void AuctionStartScheduler() {
        List<Auction> auctions = auctionRepository.findByStatus(AuctionStatus.WAITING);
        LocalDateTime currentTime = LocalDateTime.now();
        for (Auction auction : auctions) {
            LocalDateTime auctionStartTime = auction.getStartTime();
            if (currentTime.isAfter(auctionStartTime)) {
                startAuction(auction);
            }
        }
    }

    @Async
    public void startAuction(Auction auction) {

        update( // 경매 상태 낙찰로 변경
                new AuctionUpdateRequest(null, null, AuctionStatus.IN_PROGRESS, null),
                auction.getAuctionId()
        );
        AuctionStatus status = auction.getStatus();
        while (!status.isCompleted()) {

            long currentPrice = auction.getCurrentPrice();
            try {
                Thread.sleep(3000);
                List<Bid> bidByAuction = bidRepository.findHighestBidPriceByCurrentAuction(auction);
                long highestBidPrice = bidByAuction.isEmpty() ? currentPrice : bidByAuction.get(0).getPrice();

                if (currentPrice == highestBidPrice) {
                    status = AuctionStatus.COMPLETED;
                } else {
                    update(new AuctionUpdateRequest(null, null, null, highestBidPrice),
                            auction.getAuctionId());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        update( // 경매 상태 낙찰로 변경
                new AuctionUpdateRequest(null, null, AuctionStatus.COMPLETED, null),
                auction.getAuctionId()
        );
    }
}
