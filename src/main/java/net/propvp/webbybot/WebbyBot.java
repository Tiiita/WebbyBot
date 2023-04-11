package net.propvp.webbybot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.propvp.webbybot.command.console.ConsoleCommandManager;
import net.propvp.webbybot.util.Config;
import net.propvp.webbybot.util.Logger;

import java.util.Arrays;

/**
 * Created on April 10, 2023 | 16:07:08
 * (●'◡'●)
 */
public class WebbyBot {

    private JDA jda;
    private Config config;
    private ConsoleCommandManager consoleCommandManager;

    public void run() {
        Logger infoLogger = Logger.INFO;
        Logger.log(infoLogger, "Created Bot Instance!");
        Logger.log(infoLogger, "Starting...");

        //Token
        Config tokenConfig = new Config("token.yml");
        String token = tokenConfig.getString("token");


        //Main
        this.config = new Config("config.yml");
        connectToDiscord(token);
        registerCommand();
        //End
        Logger.log(infoLogger, "Start Complete, Done :)");
        Logger.log(infoLogger, "Type /shutdown to stop the bot application");
        this.consoleCommandManager = new ConsoleCommandManager();
    }



    private void registerCommand() {


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

    public Config getConfig() {
        return config;
    }

}
