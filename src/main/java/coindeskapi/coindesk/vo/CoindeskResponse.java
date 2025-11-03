package coindeskapi.coindesk.vo;

import java.util.List;

public class CoindeskResponse {
    private String updateTime;
    private List<CoinInfo> coinList;

    public static class CoinInfo {
        private String code;
        private String nameZh;
        private Double rate;

        // Getters & Setters
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getNameZh() { return nameZh; }
        public void setNameZh(String nameZh) { this.nameZh = nameZh; }
        public Double getRate() { return rate; }
        public void setRate(Double rate) { this.rate = rate; }
    }

    public String getUpdateTime() { return updateTime; }
    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }
    public List<CoinInfo> getCoinList() { return coinList; }
    public void setCoinList(List<CoinInfo> coinList) { this.coinList = coinList; }
}
