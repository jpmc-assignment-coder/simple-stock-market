package com.gbce.equity.trading.analytics;

import com.gbce.equity.products.index.IndexType;

import java.math.BigDecimal;

/**
 * IndexTradeAnalytics Interface
 */
public interface IndexTradeAnalyticsService {
    BigDecimal calcIndexPrice(IndexType indexType);
}
