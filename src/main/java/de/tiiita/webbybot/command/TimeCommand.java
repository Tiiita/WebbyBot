package de.tiiita.webbybot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import de.tiiita.webbybot.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Created on April 12, 2023 | 00:11:44
 * (●'◡'●)
 */
public class TimeCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("time")) return;
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.CYAN);
        embed.setDescription("It is " + TimeUtil.getTimeInPattern("HH:mm a"));
        event.replyEmbeds(embed.build()).setEphemeral(true).submit();
    }
}
