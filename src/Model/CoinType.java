package Model;

import java.util.Comparator;

public enum CoinType {
    UNU(1, 1, "RON"),
    CINCI(5, 5, "RON"),
    ZECE(10, 10, "RON"),
    CINCIZECI(50, 50, "RON");

    private int code;
    private int value;
    private String currency;

    CoinType(int code, int value, String currency) {
        this.code = code;
        this.value = value;
        this.currency = currency;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
