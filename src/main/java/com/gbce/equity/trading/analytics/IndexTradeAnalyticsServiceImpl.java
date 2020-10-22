package com.gbce.equity.trading.analytics;

import com.gbce.equity.products.ProductStore;
import com.gbce.equity.products.index.IndexType;
import com.gbce.equity.trading.TradeStore;
import com.gbce.equity.utils.Constants;
import com.gbce.equity.utils.MathUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class IndexTradeAnalyticsServiceImpl implements IndexTradeAnalyticsService {

    private ProductStore productStore;
    private TradeStore tradeStore;

    public IndexTradeAnalyticsServiceImpl(ProductStore productStore, TradeStore tradeStore) {
        this.productStore = productStore;
        this.tradeStore = tradeStore;
    }

    public ProductStore getProductStore() {
        return productStore;
    }

    public TradeStore getTradeStore() {
        return tradeStore;
    }

    /**
     * Get Latest record for each stock and create price list
     * and then calculate geometric mean
     */
    @Override
    public BigDecimal calcIndexPrice(IndexType indexType) {

        List<BigDecimal> stockPrices = getProductStore().getIndex(indexType)
                .getStockSymbols()
                .stream()
                .map(symbol -> getTradeStore().getLatestTradeForStock(symbol).getPrice())
                .collect(Collectors.toList());

        return MathUtils.geometricMean(stockPrices)
                .setScale(Constants.DECIMAL_PRECISION, Constants.DECIMAL_ROUNDING_MODE);
    }
}
