package coindeskapi.coindesk.controller;

import coindeskapi.coindesk.entity.CoinType;
import coindeskapi.coindesk.service.CoinTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0.0/coin")
public class CoinTypeController {
    private final CoinTypeService coinTypeService;

    public CoinTypeController(CoinTypeService coinTypeService) {
        this.coinTypeService = coinTypeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CoinType>> listAll() {
        return ResponseEntity.ok(coinTypeService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<CoinType> add(@RequestBody CoinType coinType) {
        return ResponseEntity.ok(coinTypeService.add(coinType));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CoinType> update(@PathVariable String id, @RequestBody CoinType coinType) {
        return ResponseEntity.ok(coinTypeService.update(id, coinType));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        coinTypeService.delete(id);
        return ResponseEntity.ok("Deleted coin id = " + id);
    }
}
