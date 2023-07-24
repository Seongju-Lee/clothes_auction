package com.example.clothes.domain.auction.controller;

import com.example.clothes.domain.auction.domain.AuctionStatus;
import com.example.clothes.domain.auction.dto.AuctionRequest;
import com.example.clothes.domain.auction.dto.AuctionResponse;
import com.example.clothes.domain.auction.dto.AuctionUpdateRequest;
import com.example.clothes.domain.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/auction")
    public ResponseEntity<String> save(@RequestBody AuctionRequest request) {
        AuctionResponse response = auctionService.save(request);
        return ResponseEntity.ok("새로운 경매가 등록 되었습니다. 경매상품: [" + response.itemName() + "]");
    }

    @GetMapping("/auctions/{auctionId}")
    public ResponseEntity<AuctionResponse> findById(@PathVariable Long auctionId) {
        AuctionResponse response = auctionService.findById(auctionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auctions")
    public ResponseEntity<List<AuctionResponse>> findAll(
            @RequestParam(required = false, name = "status") List<AuctionStatus> statuses
    ) {
        List<AuctionResponse> response =
                (statuses == null ? auctionService.findAll() : auctionService.findByStatuses(statuses));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/auctions/{auctionId}")
    public ResponseEntity<AuctionResponse> update(
            @RequestBody AuctionUpdateRequest request,
            @PathVariable Long auctionId
    ) {
        AuctionResponse response = auctionService.update(request, auctionId);
        return ResponseEntity.ok(response);
    }

}