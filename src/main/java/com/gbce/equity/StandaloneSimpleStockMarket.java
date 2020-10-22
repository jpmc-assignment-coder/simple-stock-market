package com.gbce.equity;

import com.gbce.equity.products.ProductStore;
import com.gbce.equity.products.ProductStoreInMemImpl;
import com.gbce.equity.products.analytics.StockProductAnalyticsService;
import com.gbce.equity.products.analytics.StockProductAnalyticsServiceImpl;
import com.gbce.equity.products.index.IndexType;
import com.gbce.equity.trading.TradeStore;
import com.gbce.equity.trading.TradeStoreInMemImpl;
import com.gbce.equity.trading.analytics.IndexTradeAnalyticsService;
import com.gbce.equity.trading.analytics.IndexTradeAnalyticsServiceImpl;
import com.gbce.equity.trading.analytics.StockTradeAnalyticsService;
import com.gbce.equity.trading.analytics.StockTradeAnalyticsServiceImpl;
import com.gbce.equity.trading.stock.Indicator;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Standalone Simple Stock Market Impl with test data
 * Logic :
 *
 * Create ProductStore
 * Create TradeStore
 *
 * Create Stocks
 * Create Index
 *
 * Create StockProductAnalyticsService
 * Create StockTradeAnalyticsService
 * Create IndexTradeAnalyticsService
 *
 * Use services to  perform calculations
 */
public class StandaloneSimpleStockMarket {

    private static final Logger logger = Logger.getLogger(StandaloneSimpleStockMarket.class.getName());

    public static void productAnalyticsExample() {

        logger.log(Level.INFO, "*** ProductAnalyticsExample : calcDividendYield(stock) and calcPERatio(stock) ***\n");

        ProductStore productStore = new ProductStoreInMemImpl();
        StockProductAnalyticsService stockProductAnalyticsService = new StockProductAnalyticsServiceImpl(productStore);

        productStore.createCommonStock("TEA", new BigDecimal("0"), new BigDecimal("100"));
        productStore.createCommonStock("POP", new BigDecimal("8"), new BigDecimal("100"));
        productStore.createCommonStock("ALE", new BigDecimal("23"), new BigDecimal("60"));
        productStore.createCommonStock("JOE", new BigDecimal("13"), new BigDecimal("250"));

        productStore.createPreferredStock("GIN", new BigDecimal("8"), new BigDecimal("100"), new BigDecimal("2"));

        productStore.createAllShareIndex();

        logger.log(Level.INFO, "Stocks = \n{0}\n", productStore.toProductsString());

        logger.log(Level.INFO, "All Share Index - All Symbols = {0}",
                productStore.getIndex(IndexType.ALL_SHARE));

        logger.log(Level.INFO, "calcDividendYield = {0}",
                stockProductAnalyticsService.calcDividendYield("POP", new BigDecimal("46")));

        logger.log(Level.INFO, "calcPERatio = {0}",
                stockProductAnalyticsService.calcPERatio("POP", new BigDecimal("46")));

        logger.log(Level.INFO, "-\n");
    }

    public static void productTradeAnalyticsExample() {

        logger.log(Level.INFO, "*** ProductTradeAnalyticsExample : calcVolumeWeightedStockPrice(stock) and " +
                "calcIndexPrice(all " +
                "stocks) *** \n");

        ProductStore productStore = new ProductStoreInMemImpl();
        TradeStore tradeStore = new TradeStoreInMemImpl();

        StockTradeAnalyticsService stockTradeAnalyticsService = new StockTradeAnalyticsServiceImpl(tradeStore);
        IndexTradeAnalyticsService indexTradeAnalyticsService = new IndexTradeAnalyticsServiceImpl(productStore,
                tradeStore);

        productStore.createAllShareIndex();
        productStore.createCommonStock("POP", new BigDecimal("8"), new BigDecimal("100"));

        tradeStore.recordStockTrade(System.currentTimeMillis() - 30000, "POP", 600, Indicator.SELL, new BigDecimal(50));
        tradeStore.recordStockTrade(System.currentTimeMillis() - 20000, "POP", 300, Indicator.BUY, new BigDecimal(30));

        logger.log(Level.INFO, "Stocks = \n{0}\n",
                productStore.toProductsString());

        logger.log(Level.INFO, "All Share Index - All Symbols = {0}",
                productStore.getIndex(IndexType.ALL_SHARE));

        logger.log(Level.INFO, "Trades = \n{0}\n",
                tradeStore.toAllTradesString());

        logger.log(Level.INFO, "calcIndexPrice = {0}",
                indexTradeAnalyticsService.calcIndexPrice(IndexType.ALL_SHARE));

        logger.log(Level.INFO, "calcVolumeWeightedStockPrice = {0}",
                stockTradeAnalyticsService.calcVolumeWeightedStockPrice("POP"));

    }

    public static void main(String[] args) {

        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tH:%1$tM:%1$tS %4$s %5$s%6$s%n");

        productAnalyticsExample();
        productTradeAnalyticsExample();
    }
}