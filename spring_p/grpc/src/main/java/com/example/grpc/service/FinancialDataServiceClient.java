package com.example.grpc.service;

import com.example.grpc.FinancialDataServiceGrpc;
import com.example.grpc.StockRequest;
import com.example.grpc.StockResponse;
import com.example.grpc.dto.StockRequestDto;
import com.example.grpc.dto.StockResponseDto;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class FinancialDataServiceClient {

    private final FinancialDataServiceGrpc.FinancialDataServiceBlockingStub blockingStub;

    // gRPC 서버에 연결 (생성자)
    public FinancialDataServiceClient() {
        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        blockingStub = FinancialDataServiceGrpc.newBlockingStub(channel);
    }

    // Protobuf 요청을 보내고, 응답을 Protobuf로 받는 로직
    public StockResponse getStockPrice(String symbol) {
        StockRequest request = StockRequest.newBuilder()
                .setSymbol(symbol)
                .build();

        return blockingStub.getStockPrice(request);
    }

    // DTO를 사용하여 gRPC 요청을 처리하는 방법 (DTO -> Protobuf 변환)
    public StockResponseDto getStockPrice(StockRequestDto stockRequestDto) {
        // DTO -> Protobuf
        StockRequest request = StockRequest.newBuilder()
                .setSymbol(stockRequestDto.getSymbol())
                .build();

        // gRPC 호출
        StockResponse response = blockingStub.getStockPrice(request);

        // Protobuf -> DTO 변환
        StockResponseDto responseDto = new StockResponseDto();
        responseDto.setSymbol(response.getSymbol());
        responseDto.setPrice(response.getPrice());
        responseDto.setTimestamp(response.getTimestamp());

        return responseDto;
    }
}
