package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Timeframe {
    private String timeframe;
    private List<Candle> candles = new ArrayList<>();
    private Ticker ticker;
    private Long id;

    public Timeframe(String timeframe) {
        this.timeframe = timeframe;
    }



    public String getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(String timeframe) {
        this.timeframe = timeframe;
    }

    public List<Candle> getCandles() {
        return candles;
    }

    public void setCandles(List<Candle> candles) {
        this.candles = candles;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
