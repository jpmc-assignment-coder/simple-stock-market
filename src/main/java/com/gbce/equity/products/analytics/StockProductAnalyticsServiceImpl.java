package com.gbce.equity.products.analytics;

import com.gbce.equity.products.ProductStore;
import com.gbce.equity.products.stock.Stock;
import com.gbce.equity.utils.Constants;
import com.gbce.equity.utils.MathUtils;

import java.math.BigDecimal;

public class StockProductAnalyticsServiceImpl implements StockProductAnalyticsService {

    private ProductStore productStore;

    public StockProductAnalyticsServiceImpl(ProductStore productStore) {
        this.productStore = productStore;
    }

    public ProductStore getProductStore() {
        return productStore;
    }

    /**
     * Calculate Dividend Yield
     * DividendForDividendYield / Price
     */
    @Override
    public BigDecimal calcDividendYield(String symbol, BigDecimal price) {
        BigDecimal dividendYield = BigDecimal.valueOf(0);
        Stock stock = getProductStore().getStock(symbol);
        if (MathUtils.isGreaterThanZero(stock.getDividendForDividendYield()) && MathUtils.isGreaterThanZero(price)) {
            dividendYield = stock.getDividendForDividendYield().divide(price, Constants.DECIMAL_PRECISION,
                    Constants.DECIMAL_ROUNDING_MODE);
        }
        return dividendYield;
    }

    /**
     * Calculate PERatio
     * Price / Last Dividend
     */
    @Override
    public BigDecimal calcPERatio(String symbol, BigDecimal price) {
        BigDecimal peRatio = BigDecimal.valueOf(0);
        Stock stock = getProductStore().getStock(symbol);
        if (MathUtils.isGreaterThanZero(stock.getLastDividend()) && MathUtils.isGreaterThanZero(price)) {
            peRatio = price.divide(stock.getLastDividend(), Constants.DECIMAL_PRECISION,
                    Constants.DECIMAL_ROUNDING_MODE);
        }
        return peRatio;
    }
}
