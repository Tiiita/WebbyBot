package net.propvp.webbybot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.propvp.webbybot.command.TimeCommand;
import net.propvp.webbybot.command.console.ConsoleCommandManager;
import net.propvp.webbybot.database.DatabaseManager;
import net.propvp.webbybot.database.MySQL;
import net.propvp.webbybot.util.Config;
import net.propvp.webbybot.util.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Created on April 10, 2023 | 16:07:08
 * (●'◡'●)
 */
public class WebbyBot {

    private JDA jda;
    private Config config;
    private Config securityConfig;
    private MySQL mySQL;
    private DatabaseManager databaseManager;
    private ConsoleCommandManager consoleCommandManager;

    public void run() {
        Logger infoLogger = Logger.INFO;
        Logger.log(infoLogger, "Starting...");

        //Setup configs
        this.config = new Config("config.yml");
        this.securityConfig = new Config("security.yml");
        String token = securityConfig.getString("token");

        //Setup Database
        String host = getConfig().getString("mysql.host");
        String port = getConfig().getString("mysql.port");
        String database = getConfig().getString("mysql.database");
        String username = getConfig().getString("mysql.user");
        String password = getConfig().getString("mysql.password");

        this.mySQL = new MySQL(host, port, database, username, password);
        this.databaseManager = new DatabaseManager(mySQL);

        setupDiscord(token);
        Logger.log(infoLogger, "Start Complete, Done :)");
        Logger.log(infoLogger, "Type /shutdown to stop the bot application");
        this.consoleCommandManager = new ConsoleCommandManager();
    }


    private void setupDiscord(String token) {
        connectToDiscord(token);
        registerCommands();
    }


    private void registerCommands() {
        List<Guild> guilds = jda.getGuilds();
        guilds.forEach(currentGuild -> {
            //Register every command here!
            registerCommand(currentGuild.getId(), "time", "Show the UTC Time", new TimeCommand());
        });
    }


    //Register discord bot commands here:
    private CommandCreateAction registerCommand(@NotNull String guildId, @NotNull String name, @NotNull String description, @NotNull Object command) {
        Guild guildById = jda.getGuildById(guildId);
        if (guildById == null) throw new IllegalArgumentException("Could not find any guild with id: " + guildId);
        CommandCreateAction createdCommand = guildById.upsertCommand(name, description);
        createdCommand.submit();
        jda.addEventListener(command);
        return createdCommand;
    }

    private void connectToDiscord(String token) {
        this.jda = JDABuilder.createDefault(token)
                .setEnabledIntents(Arrays.asList(GatewayIntent.values()))
                .setBulkDeleteSplittingEnabled(false)
                .setStatus(OnlineStatus.ONLINE)
                .build();

        try {
            this.jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public JDA getJda() {
        return jda;
    }

    public ConsoleCommandManager getConsoleCommandManager() {
        return consoleCommandManager;
    }

    public Config getSecurityConfig() {
        return securityConfig;
    }

    public Config getConfig() {
        return config;
    }

}
