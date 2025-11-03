國泰世華Java Engineer線上作業

使用的springboot版本是2.7.18，JDK是1.8
API呼叫之URL:
GET, http://localhost:8080/coindesk/api/v1.0.0/coin/list
POST, http://localhost:8080/coindesk/api/v1.0.0/coin/add
PUT, http://localhost:8080/coindesk/api/v1.0.0/coin/update
DELETE, http://localhost:8080/coindesk/api/v1.0.0/coin/delete

測試資料:
{
"code": "BTC",
"symbol": "&#x20BF;",
"rateFloat": 68000.25,
"description": "Bitcoin",
"descriptionZh": "比特幣"
}

資料轉換: GET, http://localhost:8080/coindesk/api/v1.0.0/coin/transform

h2 console URL: http://localhost:8080/coindesk/h2-console/
