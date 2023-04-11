package net.propvp.webbybot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.propvp.webbybot.command.console.ConsoleCommandManager;
import net.propvp.webbybot.util.Config;

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
        System.out.println("Created Bot Instance!");
        System.out.println("Starting...");

        //Token
        Config tokenConfig = new Config("token.yml");
        String token = tokenConfig.getString("token");


        //Main
        this.config = new Config("config.yml");
        connectToDiscord(token);
        registerCommand();
        this.consoleCommandManager = new ConsoleCommandManager();
        //End
        System.out.println("Start Complete, Done :)");
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
