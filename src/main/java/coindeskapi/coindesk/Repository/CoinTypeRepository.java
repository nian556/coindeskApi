package coindeskapi.coindesk.Repository;

import coindeskapi.coindesk.entity.CoinType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinTypeRepository extends JpaRepository<CoinType, String> {
    CoinType findByCode(String code);
}
