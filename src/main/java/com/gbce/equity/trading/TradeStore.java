package com.gbce.equity.trading;

import com.gbce.equity.trading.stock.Indicator;
import com.gbce.equity.trading.stock.Trade;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Trade Store/ Repository Interface
 */
public interface TradeStore {
    Collection<Trade> getAllTrades();

    Collection<Trade> getAllTradesForStock(String symbol);

    /**
     * Method to quickly fetch latest record of stock
     */
    Trade getLatestTradeForStock(String symbol);

    /**
     * Method to quickly fetch records of stock of last X time
     */
    Collection<Trade> getTradesAfterMillisForStock(String symbol, long millis);

    Trade recordStockTrade(long timeStampInMillis, String symbol, int quantity, Indicator indicator, BigDecimal price);

    Trade recordStockTrade(Trade trade);

    String toTradesString(String symbol);

    String toAllTradesString();
}
