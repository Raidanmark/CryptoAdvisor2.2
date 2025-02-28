package bot.data;


import bot.data.model.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//TODO: It should be class responsible for all data moves
public class Data {
    private final TickerRepository tickerRepository;
    private final DataCollecting dataCollecting;
    private static final Logger logger = LoggerFactory.getLogger(Data.class);

    public Data(TickerRepository tickerRepository, DataCollecting dataCollecting) {
        this.tickerRepository = tickerRepository;
        this.dataCollecting = dataCollecting;
    }


    private void loadTickers() {
         dataCollecting.start();
    }

}

