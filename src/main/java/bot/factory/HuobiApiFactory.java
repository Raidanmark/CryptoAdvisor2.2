package bot.factory;

import bot.data.TickerRepository;
import bot.data.api.huobi.HuobiApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.http.HttpClient;

public class HuobiApiFactory {
    public static HuobiApi create(TickerRepository tickerRepository) {

        return new HuobiApi(createHttpClient(), createObjectMapper());
    }

    private static CloseableHttpClient createHttpClient() {
        CloseableHttpClient client = HttpClients.createDefault();
        return client;
    }

    private static ObjectMapper createObjectMapper() {
        return  new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
