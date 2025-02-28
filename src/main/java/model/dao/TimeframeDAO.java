package model.dao;
import java.sql.*;
import model.entities.Timeframe;
import model.entities.Ticker;


public class TimeframeDAO implements BaseDAO {
    private final Connection connection;

    public TimeframeDAO(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS timeframes (
                id INT PRIMARY KEY AUTO_INCREMENT,
                timeframe VARCHAR(20) NOT NULL,
                ticker_id INT NOT NULL,
                FOREIGN KEY (ticker_id) REFERENCES tickers(id)
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Пример сохранения Timeframe
    // (ticker, timeframeName) -> INSERT -> возвращаем Timeframe c id
    public Timeframe saveTimeframe(Ticker ticker, String timeframeName) throws SQLException {
        String insertSql = "INSERT INTO timeframes (timeframe, ticker_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, timeframeName);
            ps.setLong(2, ticker.getId());  // предполагаем, что ticker уже сохранён

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    Timeframe tf = new Timeframe(timeframeName);
                    tf.setId(generatedId);
                    tf.setTimeframe(timeframeName);
                    tf.setTicker(ticker);
                    return tf;
                }
            }
        }
        return null;
    }



}
