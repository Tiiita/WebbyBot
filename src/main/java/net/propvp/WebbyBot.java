package net.propvp;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Arrays;

/**
 * Created on April 10, 2023 | 16:07:08
 * (●'◡'●)
 */
public class WebbyBot {
    private JDA jda;
    public void run() {
        this.jda = JDABuilder.createDefault("MTA5NDk4NjEyMTQ4MzY2NTQ1OA.GeSeUY.3tGukVTyFy2EW5_mKr2w9hHJ056u8P4cEFuoDo")
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
}
