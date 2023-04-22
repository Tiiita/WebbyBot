package de.tiiita.webbybot.command;

import de.tiiita.webbybot.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created on April 22, 2023 | 20:18:47
 * (●'◡'●)
 */
public class ClearSpamCommand extends ListenerAdapter {


    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("clear-spam")) return;
        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.replyEmbeds(EmbedUtil.getNoPermissionMessage()).submit();
            return;
        }

       final String messageTypeToDelete = event.getOption("message").getAsString();
        int deletedMessages = 0;

        List<Message> messageHistory = event.getChannel().getHistory().getRetrievedHistory();
        for (Message message : messageHistory) {
            if (message.getContentRaw().equalsIgnoreCase(messageTypeToDelete)) {
                message.delete().submit();
                deletedMessages++;
            }
        }

        event.replyEmbeds(EmbedUtil.getSimpleEmbed("Removed **" + deletedMessages + "** messages!")).setEphemeral(true).submit();
    }
}
