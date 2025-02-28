package model.analytics;

import bot.data.model.Ticker;

public interface Analyzer {
    boolean analyze(AnalysisData data);
    DataType getRequiredDataType();
}
