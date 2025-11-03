package coindeskapi.coindesk.integration;

import coindeskapi.coindesk.Repository.CoinTypeRepository;
import coindeskapi.coindesk.entity.CoinType;
import coindeskapi.coindesk.service.CoindeskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class CoindeskIntegrationTest {

    @Autowired
    private CoindeskService coindeskService;

    @Autowired
    private CoinTypeRepository coinTypeRepository;

    @Test
    void testFetchAndTransformFromApiAndSaveToH2() {
        // 1️⃣ 呼叫 Service 直接拿 Map 結果
        Map<String, Object> result = coindeskService.fetchAndTransform();

        // 2️⃣ 驗證 updateTime
        Assertions.assertNotNull(result.get("updateTime"), "updateTime 不應為 null");

        // 3️⃣ 驗證 coins List
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> coins = (List<Map<String, Object>>) result.get("coins");
        Assertions.assertFalse(coins.isEmpty(), "應該至少有一種幣別");

        // 4️⃣ 印出資料
        System.out.println("更新時間：" + result.get("updateTime"));
        coins.forEach(c -> {
            System.out.printf("%s(%s): %.4f%n", c.get("nameZh"), c.get("code"), c.get("rate"));
        });

        // 5️⃣ 驗證 H2 DB 內容
        List<CoinType> dbCoins = coinTypeRepository.findAll();
        Assertions.assertFalse(dbCoins.isEmpty(), "H2 DB 應該有儲存資料");

        CoinType usd = dbCoins.stream()
                .filter(c -> "USD".equals(c.getCode()))
                .findFirst()
                .orElse(null);
        Assertions.assertNotNull(usd, "應有 USD 幣別資料");
        Assertions.assertTrue(usd.getRateFloat() > 0, "匯率應大於 0");
    }
}
