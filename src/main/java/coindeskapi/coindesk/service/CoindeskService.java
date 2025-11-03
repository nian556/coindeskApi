package coindeskapi.coindesk.service;

import coindeskapi.coindesk.Repository.CoinTypeRepository;
import coindeskapi.coindesk.entity.CoinType;
import coindeskapi.coindesk.vo.CoindeskResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CoindeskService {
    private final RestTemplate restTemplate;
    private final CoinTypeRepository coinTypeRepository;

    @Autowired
    public CoindeskService(RestTemplateBuilder builder, CoinTypeRepository repo) {
        this.restTemplate = builder.build();
        this.coinTypeRepository = repo;
    }

    public Map<String, Object> fetchAndTransform() {
        String url = "https://kengp3.github.io/blog/coindesk.json";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // ✅ 正確解析時間格式
            String updatedISO = root.path("time").path("updatedISO").asText();
            OffsetDateTime odt = OffsetDateTime.parse(updatedISO);
            ZonedDateTime taipeiTime = odt.atZoneSameInstant(ZoneId.of("Asia/Taipei"));
            String formattedTime = taipeiTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

            // 幣別資料轉換
            JsonNode bpiNode = root.path("bpi");
            List<Map<String, Object>> coinList = new ArrayList<>();
            bpiNode.fields().forEachRemaining(entry -> {
                String code = entry.getKey();
                JsonNode coin = entry.getValue();

                CoinType entity = coinTypeRepository.findById(code).orElse(null);

                Map<String, Object> map = new HashMap<>();
                map.put("code", code);
                map.put("rate", coin.path("rate_float").asDouble());
                map.put("nameZh", entity != null ? entity.getDescriptionZh() : "未知幣別");

                coinList.add(map);
            });

            Map<String, Object> result = new HashMap<>();
            result.put("updateTime", formattedTime);
            result.put("coins", coinList);
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Coindesk API 轉換失敗", e);
        }
    }
}
