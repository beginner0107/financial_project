import grpc
from concurrent import futures
import example_pb2
import example_pb2_grpc
import requests
import os
from dotenv import load_dotenv

# .env 파일에서 환경 변수 로드
load_dotenv()

# 환경 변수에서 API 키 가져오기
ALPHA_VANTAGE_API_KEY = os.getenv("ALPHA_VANTAGE_API_KEY")

# API 키가 없으면 예외 처리
if not ALPHA_VANTAGE_API_KEY:
    raise ValueError("API key not set. Please set ALPHA_VANTAGE_API_KEY in the .env file.")

class FinancialDataServiceServicer(example_pb2_grpc.FinancialDataServiceServicer):
    def GetStockPrice(self, request, context):
        # Alpha Vantage API 호출
        response = requests.get(f'https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol={request.symbol}&apikey={ALPHA_VANTAGE_API_KEY}')
        data = response.json()
        stock_price = float(data["Global Quote"]["05. price"])
        timestamp = data["Global Quote"]["07. latest trading day"]

        return example_pb2.StockResponse(
            symbol=request.symbol,
            price=stock_price,
            timestamp=timestamp
        )

def start_grpc_server():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    example_pb2_grpc.add_FinancialDataServiceServicer_to_server(FinancialDataServiceServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("gRPC server running on port 50051")
    server.wait_for_termination()

if __name__ == "__main__":
    # gRPC 서버 실행
    start_grpc_server()
