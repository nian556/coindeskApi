package coindeskapi.coindesk.integration;

import coindeskapi.coindesk.entity.CoinType;
import coindeskapi.coindesk.Repository.CoinTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoinTypeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CoinTypeRepository coinTypeRepository;

    private String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + port + "/coindesk/api/v1.0.0/coin";
        coinTypeRepository.deleteAll(); // 清空 DB，確保測試環境乾淨
    }

    @Test
    void testAddAndListAndUpdateAndDelete() {
        // 1️⃣ 新增 coin
        CoinType coin = new CoinType();
        coin.setCode("BTC");
        coin.setSymbol("&#x20BF;");
        coin.setRateFloat(60000.0);
        coin.setDescription("Bitcoin");
        coin.setDescriptionZh("比特幣");

        ResponseEntity<CoinType> addResponse = restTemplate.postForEntity(baseUrl + "/add", coin, CoinType.class);
        Assertions.assertEquals(HttpStatus.OK, addResponse.getStatusCode());
        CoinType savedCoin = addResponse.getBody();
        Assertions.assertNotNull(savedCoin);
        Assertions.assertEquals("BTC", savedCoin.getCode());
        System.out.println("Add 執行成功");

        // 2️⃣ 取得列表
        ResponseEntity<CoinType[]> listResponse = restTemplate.getForEntity(baseUrl + "/list", CoinType[].class);
        Assertions.assertEquals(HttpStatus.OK, listResponse.getStatusCode());
        CoinType[] coins = listResponse.getBody();
        Assertions.assertNotNull(coins);
        Assertions.assertEquals(1, coins.length);
        System.out.println("List 執行成功");

        // 3️⃣ 更新 coin
        CoinType updatedCoin = new CoinType();
        updatedCoin.setCode("BTC");
        updatedCoin.setSymbol("₿");
        updatedCoin.setRateFloat(65000.0);
        updatedCoin.setDescription("Bitcoin Updated");
        updatedCoin.setDescriptionZh("比特幣 更新");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CoinType> requestUpdate = new HttpEntity<>(updatedCoin, headers);

        ResponseEntity<CoinType> updateResponse = restTemplate.exchange(
                baseUrl + "/update/BTC",
                HttpMethod.PUT,
                requestUpdate,
                CoinType.class
        );

        Assertions.assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Assertions.assertEquals(65000.0, updateResponse.getBody().getRateFloat());
        System.out.println("Update 執行成功");

        // 4️⃣ 刪除 coin
        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                baseUrl + "/delete/BTC",
                HttpMethod.DELETE,
                null,
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        Assertions.assertTrue(deleteResponse.getBody().contains("Deleted coin id = BTC"));

        // 5️⃣ 驗證 DB 已刪除
        List<CoinType> allCoins = coinTypeRepository.findAll();
        Assertions.assertTrue(allCoins.isEmpty(), "DB 應該沒有資料");
        System.out.println("Delete 執行成功");
    }
}
