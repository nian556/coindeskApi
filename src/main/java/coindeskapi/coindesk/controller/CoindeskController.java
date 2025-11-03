package coindeskapi.coindesk.controller;

import coindeskapi.coindesk.service.CoindeskService;
import coindeskapi.coindesk.vo.CoindeskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1.0.0")
public class CoindeskController {
    private final CoindeskService coindeskService;

    public CoindeskController(CoindeskService coindeskService) {
        this.coindeskService = coindeskService;
    }

    @GetMapping("/coin/transform")
    public ResponseEntity<Map<String, Object>> getTransformedData() {
        return ResponseEntity.ok(coindeskService.fetchAndTransform());
    }
}
