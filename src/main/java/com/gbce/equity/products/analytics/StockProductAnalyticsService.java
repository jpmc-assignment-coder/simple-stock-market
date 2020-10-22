package com.gbce.equity.products.analytics;

import java.math.BigDecimal;

/**
 * StockProductAnalytics Interface
 */
public interface StockProductAnalyticsService {
    BigDecimal calcDividendYield(String symbol, BigDecimal price);

    BigDecimal calcPERatio(String symbol, BigDecimal price);
}
