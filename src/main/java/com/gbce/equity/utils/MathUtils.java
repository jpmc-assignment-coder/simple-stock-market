package com.gbce.equity.utils;

import com.gbce.equity.trading.stock.Trade;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Math Utility classes
 */
public class MathUtils {

    private MathUtils() {
    }

    public static boolean isGreaterThanZero(final BigDecimal value) {
        return value.doubleValue() > 0;
    }

    /**
     * Calculate Geometric Mean
     *
     * product = p1 * p2 * ... pn
     * and then
     * pow ( product , 1/n)
     */
    public static BigDecimal geometricMean(List<BigDecimal> stockPrices) {
        BigDecimal product = BigDecimal.valueOf(1.0d);
        for (int index = 0; index < stockPrices.size(); ++index) {
            product = product.multiply(stockPrices.get(index));
        }

        Double geometricMean = Math.pow(product.doubleValue(), 1.0 / (double) stockPrices.size());
        return BigDecimal.valueOf(geometricMean);
    }

    /**
     * Calculate Volume Weighted Stock Price based on trades in past 15 minutes
     *
     * total trade price = p1*q1 + p1*q2 ... + pn*qn
     * total quantity = q1+q2..+qn
     * Volume Weighted Stock Price = total trade price/total quantity
     */
    public static BigDecimal volumeWeightedStockPrice(Collection<Trade> filteredTrades) {
        BigDecimal stockPrice = BigDecimal.valueOf(0);
        // Volume weighted Stock Price calculation
        int totalShares = 0;
        BigDecimal totalTradePrice = BigDecimal.valueOf(0.0);
        for (Trade trade : filteredTrades) {
            // Trade Price x Quantity
            totalTradePrice = totalTradePrice.add(trade.getPrice().multiply(BigDecimal.valueOf(trade.getQuantity())));
            // Total Number of shares
            totalShares += trade.getQuantity();
        }
        // stock price calculation
        if (totalShares > 0) {
            stockPrice = totalTradePrice.divide(BigDecimal.valueOf(totalShares), Constants.DECIMAL_PRECISION,
                    Constants.DECIMAL_ROUNDING_MODE);
        }
        return stockPrice;
    }
}
