package model.entities;

import java.time.Instant;


public class Candle {
    private Long id; // ID свечи (может быть null, если свеча ещё не сохранена)
    private String ticker;
    private String timeframe;
    private double openPrice;
    private double highPrice;
    private double lowPrice;
    private double closePrice;
    private double volume;
    private long timestamp;

    // Конструктор для новых свечей (без id)
    public Candle(String ticker, String timeframe, double openPrice, double highPrice, double lowPrice, double closePrice, double volume) {
        this.id = null; // Новая свеча (id ещё не задан)
        this.ticker = ticker;
        this.timeframe = timeframe;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
        this.timestamp = Instant.now().getEpochSecond();
    }

    // Конструктор для свечей, загруженных из БД (с id)
    public Candle(Long id, String ticker, String timeframe, double openPrice, double highPrice, double lowPrice, double closePrice, double volume, long timestamp) {
        this.id = id;
        this.ticker = ticker;
        this.timeframe = timeframe;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public String getTicker() { return ticker; }
    public String getTimeframe() { return timeframe; }
    public double getOpenPrice() { return openPrice; }
    public double getHighPrice() { return highPrice; }
    public double getLowPrice() { return lowPrice; }
    public double getClosePrice() { return closePrice; }
    public double getVolume() { return volume; }
    public long getTimestamp() { return timestamp; }

    public void setId(Long id) { this.id = id; }
}