package de.tiiita.webbybot.pollsystem;

import de.tiiita.webbybot.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.ls.LSException;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Created on April 13, 2023 | 15:09:53
 * (●'◡'●)
 */
public class PollCommand extends ListenerAdapter {
    private final PollManager pollManager;

    public PollCommand(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("poll")) return;
        //Check permission
        Member member = event.getMember();
        if (member == null) {
            event.replyEmbeds(EmbedUtil.getSimpleEmbed("Something went wrong, try again later")).setEphemeral(true).submit();
            return;
        }
        if (!pollManager.hasCreatePollPermission(member)) {
            event.replyEmbeds(EmbedUtil.getNoPermissionMessage()).setEphemeral(true).submit();
            return;
        }

        Guild guild = event.getGuild();
        if (guild != null) {


            pollManager.createPoll(guild.getId(), member, getPollAnswers(event));
            event.replyEmbeds(EmbedUtil.getSimpleEmbed("You have successfully created a poll!")).setEphemeral(true).submit();
        }
    }

    private List<String> getPollAnswers(SlashCommandInteractionEvent event) {
        List<OptionMapping> options = event.getOptions();
        List<String> answers = new ArrayList<>();

        options.forEach(optionMapping -> {
            answers.add(optionMapping.getAsString());
        });
        return answers;
    }
}
