package de.tiiita.webbybot.listener;

import de.tiiita.webbybot.database.DatabaseManager;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * Created on April 12, 2023 | 23:52:03
 * (●'◡'●)
 */
public class GuildJoinListener extends ListenerAdapter {

    private final DatabaseManager databaseManager;

    public GuildJoinListener(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        String guildId = event.getGuild().getId();
        if (databaseManager != null) {
            databaseManager.isGuildRegistered(guildId).whenComplete((registered, throwable) -> {
                if (!registered) {
                    databaseManager.registerGuild(guildId);
                }
            });
        }
    }
}
