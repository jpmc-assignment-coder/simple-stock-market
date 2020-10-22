package com.gbce.equity.trading.analytics;

import java.math.BigDecimal;

/**
 * StockTradeAnalytics Interface
 */
public interface StockTradeAnalyticsService {
    BigDecimal calcVolumeWeightedStockPrice(String symbol);
}
