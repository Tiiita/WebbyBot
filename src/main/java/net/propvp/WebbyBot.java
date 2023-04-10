package net.propvp;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.propvp.command.console.ConsoleCommandManager;
import net.propvp.util.ConfigWrapper;

import javax.sound.midi.Soundbank;
import javax.swing.table.JTableHeader;
import java.util.Arrays;
import java.util.Scanner;

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
        this.jda =  JDABuilder.createDefault(token)
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
