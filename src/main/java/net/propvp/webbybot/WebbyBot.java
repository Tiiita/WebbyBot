package net.propvp.webbybot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.propvp.webbybot.command.console.ConsoleCommandManager;
import net.propvp.webbybot.util.ConfigWrapper;

import java.util.Arrays;

/**
 * Created on April 10, 2023 | 16:07:08
 * (●'◡'●)
 */
public class WebbyBot {

    private JDA jda;
    private ConfigWrapper config;
    private ConsoleCommandManager consoleCommandManager;

    public void run() {
        System.out.println("Created Bot Instance!");
        System.out.println("Starting...");


        //Token


        //TODO: FIx CongigWrapper so you can create multiple configs! | 11.04.2023 16:00 <- Fix Date
        ConfigWrapper tokenConfig = new ConfigWrapper();
        tokenConfig.load("token.yml");

        //


        //Main
        this.config = new ConfigWrapper();
        config.load("config.yml");
        String token = config.getString("token");
        connectToDiscord(token);
        registerCommand();
        System.out.println("Start Complete, Done :)");
        //End


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

    public ConfigWrapper getConfig() {
        return config;
    }

}
