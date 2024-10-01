package com.example.grpc.controller;

import com.example.grpc.dto.StockRequestDto;
import com.example.grpc.dto.StockResponseDto;
import com.example.grpc.service.FinancialDataServiceClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    private final FinancialDataServiceClient financialDataServiceClient;

    public StockController(FinancialDataServiceClient financialDataServiceClient) {
        this.financialDataServiceClient = financialDataServiceClient;
    }

    @PostMapping("/stock-price")
    public StockResponseDto getStockPrice(@RequestBody StockRequestDto stockRequestDto) {
        // gRPC 호출 및 결과 반환 (DTO 변환 포함)
        return financialDataServiceClient.getStockPrice(stockRequestDto);
    }
}

