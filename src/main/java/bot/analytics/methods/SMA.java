package bot.analytics.methods;

import bot.analytics.Analyzer;
import bot.bottype.discord.BotListener;
import bot.data.Data;
import bot.data.model.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SMA implements Analyzer {
     private final Data data;
     private final BotListener listener;
    private static final Logger logger = LoggerFactory.getLogger(SMA.class);

    public SMA(Data data, BotListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public void analyze(Ticker ticker) {
        // Получаем список значений закрытия из тикера
        List<Double> closePrices = ticker.close();

        // Рассчитаем текущее SMA
        double currentSMA = calculateSMA(closePrices);

        // Получаем последнее закрытие
        double lastClose = closePrices.get(closePrices.size() - 1);

        // Определяем сигнал
        if (lastClose > currentSMA) {
            if (ticker.SMAsignal() == false) {

                listener.broadcastMessage("Ticker: " + ticker.symbol() + " Timeframe: " + ticker.timeframe() + " SMA signal: BUY");

                logger.info("BUY");

                Ticker updatedTicker = new Ticker(
                        ticker.symbol(),
                        ticker.timeframe(),
                        ticker.close(),
                        ticker.lastTimestamp(),
                        true,
                        ticker.MACDsignal()
                );
                data.updateTicker(updatedTicker);



            } else { logger.info("BUY");}
        } else {
            if (ticker.SMAsignal() == true) {

                listener.broadcastMessage("Ticker: " + ticker.symbol() + " Timeframe: " + ticker.timeframe() + " SMA signal: SELL");

                logger.info("SELL");

                Ticker updatedTicker = new Ticker(
                        ticker.symbol(),
                        ticker.timeframe(),
                        ticker.close(),
                        ticker.lastTimestamp(),
                        false,
                        ticker.MACDsignal()
                );
                data.updateTicker(updatedTicker);
            } else {  logger.info("SELL");}
        }
    }

    // Метод для вычисления SMA
    private double calculateSMA(List<Double> prices) {
        double sum = 0;
        for (double price : prices) {
            sum += price;
        }
        return sum / prices.size(); // Возвращаем среднее значение
    }
}
