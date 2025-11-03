package coindeskapi.coindesk.entity;

import javax.persistence.*;

@Entity
@Table(name = "coin_type")
public class CoinType {

    @Id
    @Column(nullable = false, unique = true, length = 10)
    private String code;  // 幣別代碼，例如 USD、EUR

    @Column(length = 10)
    private String symbol;  // 幣別符號，例如 $

    @Column(name = "rate_float")
    private Double rateFloat;  // 匯率數值

    @Column(length = 100)
    private String description;  // 幣別英文名稱

    @Column(name = "description_zh", length = 100)
    private String descriptionZh;  // 幣別中文名稱

    public CoinType() {}

    public CoinType(String code, String symbol, Double rateFloat, String description, String descriptionZh) {
        this.code = code;
        this.symbol = symbol;
        this.rateFloat = rateFloat;
        this.description = description;
        this.descriptionZh = descriptionZh;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public Double getRateFloat() { return rateFloat; }
    public void setRateFloat(Double rateFloat) { this.rateFloat = rateFloat; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDescriptionZh() { return descriptionZh; }
    public void setDescriptionZh(String descriptionZh) { this.descriptionZh = descriptionZh; }
}
