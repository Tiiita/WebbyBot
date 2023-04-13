package de.tiiita.webbybot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

/**
 * Created on April 13, 2023 | 15:17:20
 * (●'◡'●)
 */
public class EmbedUtil {

    public static MessageEmbed getSimpleEmbed(String text) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(text);
        embedBuilder.setColor(Color.CYAN);
        return embedBuilder.build();
    }

    public static MessageEmbed getNoPermissionMessage() {
        return getSimpleEmbed("You do not have permission for that :(!");
    }
}
