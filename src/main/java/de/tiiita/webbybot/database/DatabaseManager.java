package de.tiiita.webbybot.database;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

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
    private final JDA jda;

    public DatabaseManager(MySQL mySQL, JDA jda) {
        this.mySQL = mySQL;
        this.jda = jda;
    }

    private final String settingsTable = "settings";


    public CompletableFuture<Boolean> isGuildRegistered(String guildId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(
                    "select uuid from " + this.settingsTable + " where uuid = ?;")) {
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
                    "INSERT INTO " + this.settingsTable + "(guildId) VALUES(?);")) {
                statement.setString(1, guildId);
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> registerEveryNonRegisteredGuild() {
        return CompletableFuture.runAsync(() -> {
            for (Guild guild : jda.getGuilds()) {
                String guildId = guild.getId();
                isGuildRegistered(guildId).whenComplete((registered, throwable) -> {
                    registerGuild(guildId);
                });
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
