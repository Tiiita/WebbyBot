package net.propvp;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.propvp.util.ConfigWrapper;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created on April 10, 2023 | 16:07:08
 * (●'◡'●)
 */
public class WebbyBot {
    private JDA jda;
    private ConfigWrapper config;
    public void run() {

        this.config = new ConfigWrapper();
        config.load("config.yml");

        String token = config.getString("token");
        this.jda = JDABuilder.createDefault(token)
                .setEnabledIntents(Arrays.asList(GatewayIntent.values()))
                .setBulkDeleteSplittingEnabled(false)
                .setStatus(OnlineStatus.ONLINE)
                .build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerCommand() {



    }


    public JDA getJda() {
        return jda;
    }

    public ConfigWrapper getConfig() {
        return config;
    }
}
