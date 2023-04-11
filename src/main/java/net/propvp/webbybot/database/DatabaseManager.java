package net.propvp.webbybot.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * @author tiiita_
 * Created on Januar 06, 2023 | 15:45:53
 * (●'◡'●)
 */
public class DatabaseManager {

    private final MySQL mySQL;
    private final String table = "stats";
    public DatabaseManager(MySQL mySQL) {

        this.mySQL = mySQL;
    }


    public CompletableFuture<Boolean> isGuildRegistered(String guildId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(
                    "select uuid from " + this.table + " where uuid = ?;")) {
                statement.setString(1, guildId);
                ResultSet result = statement.executeQuery();
                return result.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> registerGuild(String guildId) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO "+  this.table + "(guildId) VALUES(?);")) {
                statement.setString(1, guildId);
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Connection getConnection() {
        try {
            return mySQL.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
