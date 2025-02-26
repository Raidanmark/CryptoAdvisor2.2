package bot.analytics;

import bot.data.model.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TickerAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(TickerAnalyzer.class);
    private final List<Analyzer> analyzers;

        public TickerAnalyzer(List<Analyzer> analyzers) {
            this.analyzers = analyzers;
        }

        public void analyzeTicker(Ticker ticker) {
            for (Analyzer analyzer : analyzers) {
                try {
                    analyzer.analyze(ticker);
                } catch (Exception e) {
                    logger.error("Error analyzing ticker: " + ticker.symbol(), e);
                }
            }
        }

        private void logError(String message, Throwable e) {
            logger.error("[TickerAnalyzer] " + message, e);
        }
    }
