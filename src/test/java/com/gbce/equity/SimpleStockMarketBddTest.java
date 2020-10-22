package com.gbce.equity;

import com.gbce.equity.products.ProductStore;
import com.gbce.equity.products.ProductStoreInMemImpl;
import com.gbce.equity.products.analytics.StockProductAnalyticsService;
import com.gbce.equity.products.analytics.StockProductAnalyticsServiceImpl;
import com.gbce.equity.products.index.IndexType;
import com.gbce.equity.products.stock.CommonStock;
import com.gbce.equity.products.stock.PreferredStock;
import com.gbce.equity.products.stock.Stock;
import com.gbce.equity.trading.TradeStore;
import com.gbce.equity.trading.TradeStoreInMemImpl;
import com.gbce.equity.trading.analytics.IndexTradeAnalyticsService;
import com.gbce.equity.trading.analytics.IndexTradeAnalyticsServiceImpl;
import com.gbce.equity.trading.analytics.StockTradeAnalyticsService;
import com.gbce.equity.trading.analytics.StockTradeAnalyticsServiceImpl;
import com.gbce.equity.trading.stock.StockTrade;
import com.gbce.equity.trading.stock.Trade;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/*
 * Scenarios and Steps Impl for feature files.
 * matches_below_result method does the calculation and asserts
 *
 */
public class SimpleStockMarketBddTest {

    private static final Logger logger = Logger.getLogger(StandaloneSimpleStockMarket.class.getName());
    private List<CommonStock> commonStockData = new ArrayList<>();
    private List<PreferredStock> preferredStockData = new ArrayList<>();
    private List<StockTrade> tradeData = new ArrayList<>();
    private CalcType calcType;
    private Map<String, BigDecimal> stockPriceInput = new HashMap<>();

    @Before
    public void initialization() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tH:%1$tM:%1$tS %4$s %5$s%6$s%n");

        commonStockData.clear();
        preferredStockData.clear();
        tradeData.clear();
        stockPriceInput.clear();
    }

    @Given("^Common stock example data$")
    public void common_stock_example_data(DataTable table) throws Exception {
        commonStockData = table.asList(CommonStock.class);
    }

    @Given("^Preferred stock example data$")
    public void preferred_stock_example_data(DataTable table) throws Exception {
        preferredStockData = table.asList(PreferredStock.class);
    }

    @Given("^Example trade data$")
    public void example_trade_data(DataTable table) throws Exception {
        tradeData = table.asList(StockTrade.class);
        for (StockTrade trade : tradeData) {
            trade.setTimeStampInMillis(System.currentTimeMillis() + trade.getTimeStampInMillis());
        }
    }

    @When("^Calculating \"([^\"]*)\" for stocks$")
    public void calculating_for_stocks(String calcType, DataTable table) throws Exception {
        this.calcType = CalcType.valueOf(calcType.replaceAll("\\s", ""));
        for (String symbol : table.asList(String.class)) {
            stockPriceInput.put(symbol, BigDecimal.ZERO);
        }
    }

    @When("^Calculating \"([^\"]*)\" for price$")
    public void calculating_for_price(String calcType, DataTable table) throws Exception {
        this.calcType = CalcType.valueOf(calcType.replaceAll("\\s", ""));
        stockPriceInput = table.asMap(String.class, BigDecimal.class);
    }

    @Then("^Matches below result$")
    public void matches_below_result(DataTable table) throws Exception {


        logger.log(Level.INFO, "*** Running for Calculation type {0}  *** \n", calcType);

        Map<String, BigDecimal> outputDataset = table.asMap(String.class, BigDecimal.class);
        ProductStore productStore = new ProductStoreInMemImpl();
        TradeStore tradeStore = new TradeStoreInMemImpl();

        StockProductAnalyticsService stockProductAnalyticsService = new StockProductAnalyticsServiceImpl(productStore);

        StockTradeAnalyticsService stockTradeAnalyticsService = new StockTradeAnalyticsServiceImpl(tradeStore);
        IndexTradeAnalyticsService indexTradeAnalyticsService = new IndexTradeAnalyticsServiceImpl(productStore,
                tradeStore);

        productStore.createAllShareIndex();
        for (Stock stock : commonStockData) {
            productStore.addStock(stock);
        }
        for (Stock stock : preferredStockData) {
            productStore.addStock(stock);
        }
        for (Trade trade : tradeData) {
            tradeStore.recordStockTrade(trade);
        }

        logger.log(Level.INFO, "Stocks = \n{0}\n",
                productStore.toProductsString());

        logger.log(Level.INFO, "All Share Index - All Symbols = {0}",
                productStore.getIndex(IndexType.ALL_SHARE));

        logger.log(Level.INFO, "Trades = \n{0}\n",
                tradeStore.toAllTradesString());

        logger.log(Level.INFO, "Expected = {0}", outputDataset);


        for (Map.Entry<String, BigDecimal> entry : stockPriceInput.entrySet()) {
            BigDecimal actual = null;

            switch (calcType) {
                case DividendYield:
                    actual = stockProductAnalyticsService.calcDividendYield(entry.getKey(), entry.getValue());
                    break;
                case PERatio:
                    actual = stockProductAnalyticsService.calcPERatio(entry.getKey(), entry.getValue());
                    break;
                case VolumeWeightedStockPrice:
                    actual = stockTradeAnalyticsService.calcVolumeWeightedStockPrice(entry.getKey());
                    break;
                case IndexPrice:
                    actual = indexTradeAnalyticsService.calcIndexPrice(IndexType.ALL_SHARE);
                    break;
            }
            assertEquals(outputDataset.get(entry.getKey()).stripTrailingZeros(), actual.stripTrailingZeros());
        }
    }

    enum CalcType {DividendYield, PERatio, VolumeWeightedStockPrice, IndexPrice}
}


