package model.dao;

import model.entities.MethodSet;
import model.entities.Signal;
import model.entities.Timeframe;

import java.sql.*;

public class SignalDAO implements BaseDAO {
    private final Connection connection;

    public SignalDAO(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS signals (
                id INT PRIMARY KEY AUTO_INCREMENT,
                    signal_value BOOLEAN NOT NULL,
                    method_set_id INT NOT NULL,
                    timeframe_id INT NOT NULL,
                    FOREIGN KEY (method_set_id) REFERENCES method_sets(id),
                    FOREIGN KEY (timeframe_id) REFERENCES timeframes(id)
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public Signal saveSignal(boolean signalValue, MethodSet methodSet, Timeframe timeframe) throws SQLException {
        String insertSql = "INSERT INTO signals (signal_value) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, signalValue);
            ps.setLong(2, methodSet.getId());
            ps.setLong(3, timeframe.getId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    Signal s = new Signal();
                    s.setId(generatedId);
                    s.setSignal(signalValue);
                    return s;
                }
            }
        }
        return null;
    }

    public Signal findById(long id) throws SQLException {
        String sql = "SELECT id, signal_value FROM signals WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Signal s = new Signal();
                    s.setId(rs.getLong("id"));
                    s.setSignal(rs.getBoolean("signal_value"));
                    return s;
                }
            }
        }
        return null;
    }
}
