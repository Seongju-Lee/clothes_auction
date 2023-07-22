package com.example.clothes.domain.bid.controller;

import com.example.clothes.domain.bid.dto.BidInfoResponse;
import com.example.clothes.domain.bid.dto.BidRequest;
import com.example.clothes.domain.bid.dto.BidResponse;
import com.example.clothes.domain.bid.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping("/bid")
    public ResponseEntity<BidResponse> save(@RequestBody BidRequest request) {
        BidResponse response = bidService.save(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bids")
    public ResponseEntity<List<BidInfoResponse>> findAll(
            @RequestParam(required = false, name = "bId") Long bidderId,
            @RequestParam(required = false, name = "aId") Long auctionId
    ) {
        List<BidInfoResponse> response = bidService.findByCriteria(bidderId, auctionId);
        return ResponseEntity.ok(response);
    }
}
