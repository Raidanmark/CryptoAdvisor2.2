package bot.data.api;

import bot.data.CandlestickHandler;

public interface Websocket {
    void updateCandlestick(String symbol, String timeframe, CandlestickHandler handler);
}
