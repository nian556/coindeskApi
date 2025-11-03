package coindeskapi.coindesk.service;

import coindeskapi.coindesk.Repository.CoinTypeRepository;
import coindeskapi.coindesk.entity.CoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoinTypeService {
    private final CoinTypeRepository coinTypeRepository;

    public CoinTypeService(CoinTypeRepository coinTypeRepository) {
        this.coinTypeRepository = coinTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<CoinType> getAll() {
        return coinTypeRepository.findAll();
    }

    @Transactional
    public CoinType add(CoinType coinType) {
        return coinTypeRepository.save(coinType);
    }

    @Transactional
    public CoinType update(String id, CoinType updated) {
        CoinType coinType = coinTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CoinType not found: " + id));
        coinType.setCode(updated.getCode());
        coinType.setSymbol(updated.getSymbol());
        coinType.setRateFloat(updated.getRateFloat());
        coinType.setDescription(updated.getDescription());
        coinType.setDescriptionZh(updated.getDescriptionZh());
        return coinTypeRepository.save(coinType);
    }

    @Transactional
    public void delete(String id) {
        if (!coinTypeRepository.existsById(id)) {
            throw new RuntimeException("CoinType not found: " + id);
        }
        coinTypeRepository.deleteById(id);
    }
}
