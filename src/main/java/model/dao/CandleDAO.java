package model.dao;

import model.entities.Candle;
import model.entities.Timeframe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandleDAO implements BaseDAO {
    private final Connection connection;

    public CandleDAO(Connection connection) {
        this.connection = connection;
    }

    // Создание таблицы для тикера и таймфрейма (если её нет)
    public void createTableForTickerTimeframe(String ticker, String timeframe) throws SQLException {
        String tableName = ticker + "_" + timeframe;

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "timestamp BIGINT NOT NULL, "
                + "open_price DOUBLE NOT NULL, "
                + "high_price DOUBLE NOT NULL, "
                + "low_price DOUBLE NOT NULL, "
                + "close_price DOUBLE NOT NULL, "
                + "volume DOUBLE NOT NULL "
                + ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Сохранение свечи в БД (и получение id)
    public void saveCandle(Candle candle) throws SQLException {
        String tableName = candle.getTicker() + "_" + candle.getTimeframe();

        // Создаём таблицу, если её нет
        createTableForTickerTimeframe(candle.getTicker(), candle.getTimeframe());

        // SQL-запрос для вставки данных
        String sql = "INSERT INTO " + tableName + " (timestamp, open_price, high_price, low_price, close_price, volume) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, candle.getTimestamp());
            ps.setDouble(2, candle.getOpenPrice());
            ps.setDouble(3, candle.getHighPrice());
            ps.setDouble(4, candle.getLowPrice());
            ps.setDouble(5, candle.getClosePrice());
            ps.setDouble(6, candle.getVolume());
            ps.executeUpdate();

            // Получаем сгенерированный id
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    candle.setId(rs.getLong(1));
                }
            }
        }
    }


}
