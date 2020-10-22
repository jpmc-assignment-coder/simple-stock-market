package com.gbce.equity.utils;

import java.math.RoundingMode;

public interface Constants {

    /**
     * Constant for Decimal places for calc outputs
     */
    int DECIMAL_PRECISION = 5;

    /**
     * Constant for Rounding Mode for calc outputs
     */
    RoundingMode DECIMAL_ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * 15 minutes constant for Volume Weighted Stock Price
     */
    int VOLUME_WEIGHTED_STOCK_PRICE_TIME_MILLIS = 15 * 60 * 1000;
}
