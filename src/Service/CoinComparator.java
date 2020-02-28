package Service;

import Model.CoinType;

import java.util.Comparator;

public class CoinComparator implements Comparator<CoinType> {
    @Override
    public int compare(CoinType coin1, CoinType coin2) {
        return coin2.getValue().compareTo(coin1.getValue());
    }
}
