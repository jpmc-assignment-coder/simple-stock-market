package com.gbce.equity.trading.analytics;

import com.gbce.equity.trading.TradeStore;
import com.gbce.equity.trading.stock.Trade;
import com.gbce.equity.utils.Constants;
import com.gbce.equity.utils.MathUtils;

import java.math.BigDecimal;
import java.util.Collection;

public class StockTradeAnalyticsServiceImpl implements StockTradeAnalyticsService {

    private TradeStore tradeStore;

    public StockTradeAnalyticsServiceImpl(TradeStore tradeStore) {
        this.tradeStore = tradeStore;
    }

    public TradeStore getTradeStore() {
        return tradeStore;
    }

    /**
     * Get Last 15 min's trade of stock
     * and then calculate volumeWeightedStockPrice
     */
    @Override
    public BigDecimal calcVolumeWeightedStockPrice(String symbol) {

        long fromTime = System.currentTimeMillis() - Constants.VOLUME_WEIGHTED_STOCK_PRICE_TIME_MILLIS;

        Collection<Trade> stockPrices = getTradeStore().getTradesAfterMillisForStock(symbol, fromTime);

        return MathUtils.volumeWeightedStockPrice(stockPrices)
                .setScale(Constants.DECIMAL_PRECISION, Constants.DECIMAL_ROUNDING_MODE);
    }

}
