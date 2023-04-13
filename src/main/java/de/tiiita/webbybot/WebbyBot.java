package de.tiiita.webbybot;

import de.tiiita.webbybot.command.TimeCommand;
import de.tiiita.webbybot.command.console.ConsoleCommandManager;
import de.tiiita.webbybot.database.DatabaseManager;
import de.tiiita.webbybot.database.MySQL;
import de.tiiita.webbybot.listener.GuildJoinListener;
import de.tiiita.webbybot.pollsystem.PollCommand;
import de.tiiita.webbybot.pollsystem.PollManager;
import de.tiiita.webbybot.util.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import de.tiiita.webbybot.util.Logger;
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
    private PollManager pollManager;
    private MySQL mySQL;
    private DatabaseManager databaseManager;
    private ConsoleCommandManager consoleCommandManager;

    public void run() {
        Logger infoLogger = Logger.INFO;
        Logger.log(infoLogger, "Starting...");

        //Setup configs
        this.config = new Config( "config.yml");
        this.securityConfig = new Config("security.yml");
        String token = securityConfig.getString("token");


        //Setup Database
        String host = securityConfig.getString("mysql.host");
        String port = securityConfig.getString("mysql.port");
        String database = securityConfig.getString("mysql.database");
        String username = securityConfig.getString("mysql.user");
        String password = securityConfig.getString("mysql.password");

        this.mySQL = new MySQL(host, port, database, username, password);
        this.databaseManager = new DatabaseManager(jda, mySQL);

        databaseManager.registerEveryNonRegisteredGuild().whenComplete((unused, throwable) -> {
            Logger.log(infoLogger, "Start Complete, Done :)");
            Logger.log(infoLogger, "Type 'shutdown' to stop the bot application");
        });

        this.pollManager = new PollManager();
        setupDiscord(token);


        this.consoleCommandManager = new ConsoleCommandManager(this);
    }

    private void setupDiscord(String token) {
        connectToDiscord(token);
        registerCommands();
        registerListener();
        jda.updateCommands().submit();
    }


    private void registerCommands() {
        List<Guild> guilds = jda.getGuilds();
        guilds.forEach(currentGuild -> {
            String guildId = currentGuild.getId();

            //Register every command here!
            registerCommand(guildId, "poll", "Create polls easily", new PollCommand(pollManager));
            registerCommand(guildId, "time", "Show the UTC Time", new TimeCommand());
        });
    }

    private void registerListener() {
        jda.addEventListener(new GuildJoinListener(databaseManager));
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
