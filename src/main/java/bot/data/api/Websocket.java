package bot.data.api;

public interface Websocket {
    void updateCandlestick(String symbol, String timeframe, CandlestickHandler handler);
}
