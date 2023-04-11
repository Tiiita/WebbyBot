package net.propvp.webbybot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.propvp.webbybot.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Created on April 12, 2023 | 00:11:44
 * (●'◡'●)
 */
public class TimeCommand extends ListenerAdapter {
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.CYAN);
        embed.setDescription("It is " + TimeUtil.getTimeInPattern("HH:mm a"));
        event.replyEmbeds(embed.build()).setEphemeral(true).submit();
    }
}
