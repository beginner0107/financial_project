# Financial Project

이 프로젝트는 Python과 Spring Boot를 사용한 gRPC 기반 마이크로서비스 아키텍처를 보여줍니다. 

Python 서비스는 Alpha Vantage API를 통해 실시간 주식 데이터를 가져오고, Spring Boot 서비스는 gRPC를 통해 Python 서비스와 상호 작용합니다.

## Project Structure

```bash
financial_project/
├─ python_p/
│  ├─ proto_generated/       # Generated Protobuf files
│  └─ main.py                # gRPC Python server implementation
├─ spring_p/
│  ├─ src/
│  │  ├─ main/
│  │  │  ├─ java/
│  │  │  │  └─ com/example/grpc/
│  │  │  │      ├─ controller/  # REST controllers
│  │  │  │      ├─ dto/         # Data Transfer Objects
│  │  │  │      └─ service/     # gRPC client service
│  │  └─ resources/
└─ README.md                    # Project documentation
```

## Prerequisites
- Python 3.10+
- Java 17
- Alpha Vantage API Key (set in `.env` for Python)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/beginner0107/financial_project.git
cd financial_project
```

### 2. Python gRPC Server Setup

#### 1. Install dependencies:
```bash
cd python_p
python -m venv financial_p
source financial_p/bin/activate  # On Windows: financial_p\Scripts\activate
pip install -r requirements.txt
```

#### 2. Set up .env file with your API key:
```bash
echo "ALPHA_VANTAGE_API_KEY=your_api_key" > .env
```

#### 3. Run the Python gRPC server:
```bash
python main.py
```

### 3. Spring Boot Client Setup

#### 1. Navigate to the Spring project:


```bash
cd spring_p
```

#### 2. Build and run the Spring Boot application:

```bash
./gradlew bootRun
```

### 4. Test the Application
Python 및 Spring 서비스가 모두 실행되면 Spring Boot 서비스에 액세스하여 주가를 요청할 수 있습니다:

```bash
http://localhost:8080/stock-price?symbol=AAPL
```

이렇게 하면 Alpha Vantage에서 주식 데이터를 가져오는 Python 서비스에 대한 gRPC 요청이 트리거됩니다.

## Protobuf Definitions

The communication between the Python and Spring services is enabled by gRPC using the following .proto file:

```proto
syntax = "proto3";

package com.example.grpc;

service FinancialDataService {
  rpc GetStockPrice (StockRequest) returns (StockResponse);
}

message StockRequest {
  string symbol = 1;
}

message StockResponse {
  string symbol = 1;
  float price = 2;
  string timestamp = 3;
}
```

## Additional Notes

### Run the Project on Windows or macOS

- Windows: To activate the virtual environment:

```bash
financial_p\Scripts\activate
```
- macOS/Linux: To activate the virtual environment:

```bash
source financial_p/bin/activate
```

### Testing the gRPC Server with curl

- You can also use curl to test the Spring Boot REST API after both servers are running:

```bash
curl "http://localhost:8080/stock-price?symbol=AAPL"
```
