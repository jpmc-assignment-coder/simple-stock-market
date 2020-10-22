package com.gbce.equity.trading;

import com.gbce.equity.trading.stock.Indicator;
import com.gbce.equity.trading.stock.StockTrade;
import com.gbce.equity.trading.stock.Trade;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In memory implementation of trade store/repository
 * Stores all trades and for fast lookup also caches stock based trade cache
 * always returns unmodifiableCollection
 * supports toString of trades
 */
public class TradeStoreInMemImpl implements TradeStore {

    private final List<Trade> allTrades = Collections.synchronizedList(new ArrayList<>());
    private final Map<String, List<Trade>> symTrades = new ConcurrentHashMap<>();

    @Override
    public Collection<Trade> getAllTrades() {
        return Collections.unmodifiableCollection(allTrades);
    }

    @Override
    public Collection<Trade> getAllTradesForStock(String symbol) {
        return Collections.unmodifiableCollection(symTrades.getOrDefault(symbol,
                Collections.synchronizedList(new ArrayList<Trade>())));
    }

    @Override
    public Trade getLatestTradeForStock(String symbol) {
        List<Trade> trades = symTrades.getOrDefault(symbol, Collections.synchronizedList(new ArrayList<>()));
        if (trades.isEmpty()) {
            return null;
        }
        return trades.get(trades.size() - 1);
    }

    @Override
    public Collection<Trade> getTradesAfterMillisForStock(String symbol, long millis) {
        List<Trade> trades = symTrades.getOrDefault(symbol, Collections.synchronizedList(new ArrayList<>()));
        if (trades.isEmpty()) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableCollection(trades
                .stream()
                .filter(trade -> trade.getTimeStampInMillis() > millis)
                .collect(Collectors.toList()));
    }

    @Override
    public Trade recordStockTrade(long timeStampInMillis, String symbol, int quantity, Indicator indicator,
                                  BigDecimal price) {
        Trade trade = new StockTrade(timeStampInMillis, symbol, quantity, indicator, price);
        return recordStockTrade(trade);
    }

    @Override
    public Trade recordStockTrade(Trade trade) {
        List<Trade> prevSymTrades = symTrades.getOrDefault(trade.getSymbol(),
                Collections.synchronizedList(new ArrayList<>()));
        if (prevSymTrades.isEmpty()) {
            symTrades.put(trade.getSymbol(), prevSymTrades);
        }
        prevSymTrades.add(trade);

        allTrades.add(trade);
        return trade;
    }

    @Override
    public String toTradesString(String symbol) {
        return symTrades.getOrDefault(symbol, Collections.synchronizedList(new ArrayList<>())).stream()
                .map(Trade::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toAllTradesString() {
        return allTrades.stream()
                .map(Trade::toString)
                .collect(Collectors.joining("\n"));
    }

}
