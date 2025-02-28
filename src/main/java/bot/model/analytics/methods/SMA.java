package bot.model.analytics.methods;

import bot.model.analytics.AnalysisData;
import bot.model.analytics.Analyzer;
import bot.model.analytics.methods.config.ConfigSMA;

import java.util.List;

public class SMA implements Analyzer {
    private final int period;

    public SMA(ConfigSMA config) {
        this.period = config.getPeriod();
    }

    @Override
    public boolean analyze(AnalysisData data) {
        List<Double> closePrices = data.getClosePrices();
        if (closePrices.size() < period) return false;

        double sum = 0;
        for (int i = closePrices.size() - period; i < closePrices.size(); i++) {
            sum += closePrices.get(i);
        }
        double sma = sum / period;

        return closePrices.get(closePrices.size() - 1) > sma;
    }

    @Override
    public DataType getRequiredDataType() {
        return DataType.CLOSE;
    }
}
