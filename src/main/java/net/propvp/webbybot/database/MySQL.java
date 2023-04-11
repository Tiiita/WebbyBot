package net.propvp.webbybot.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.propvp.webbybot.util.Logger;;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * @author tiiita_
 * Created on Januar 10, 2023 | 20:40:44
 * (●'◡'●)
 */
public class MySQL {

    private final String HOST;
    private final String DATABASE;
    private final String USER;
    private final String PASSWORD;
    private final String PORT;
    private Connection connection;
    private final HikariConfig config = new HikariConfig();
    private HikariDataSource dataSource;

    public MySQL(String HOST, String PORT, String DATABASE, String USER, String PASSWORD) {
        this.HOST = HOST;
        this.DATABASE = DATABASE;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.PORT = PORT;

        connect();
        try {
            initDb();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect() {
        try {
            config.setJdbcUrl("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoConnect=true&serverTimezone=UTC");
            config.setUsername(USER);
            config.setPassword(PASSWORD);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource = new HikariDataSource(config);
            dataSource.getConnection();
            Logger.log(Logger.INFO, "Successfully connected to mysql!");
        } catch (SQLException e) {
            Logger.log(Logger.ERROR, "The mysql connection has been closed!");
            Logger.log(Logger.ERROR, "Error: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
                Logger.log(Logger.INFO, "The mysql connection has been closed!");
            }
        } catch (SQLException e) {
            Logger.log(Logger.ERROR, "Cannot close mysql connection!");
            Logger.log(Logger.ERROR, "Error: " + e.getMessage());
        }
    }

    private void initDb() throws SQLException, IOException {
        // first lets read our setup file.
        // This file contains statements to create our inital tables.
        // it is located in the resources.
        String setup;
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("dbsetup.sql")) {
            // Java 9+ way
            // Legacy way
            setup = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            Logger.log(Logger.ERROR, "Could not read db setup file.");
            Logger.log(Logger.ERROR, "Exception: " + e.getMessage());
            throw e;
        }
        // Mariadb can only handle a single query per statement. We need to split at ;.
        String[] queries = setup.split(";");
        // execute each query to the database.
        for (String query : queries) {
            // If you use the legacy way you have to check for empty queries here.
            if (query.isEmpty()) continue;
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.execute();
            }
        }
        Logger.log(Logger.INFO, "Database setup complete.");
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
