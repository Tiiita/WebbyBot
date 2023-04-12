package de.tiiita.webbybot.pollsystem;

import net.dv8tion.jda.api.entities.Member;

import java.io.Serializable;
import java.util.Map;

/**
 * Created on April 12, 2023 | 22:54:30
 * (●'◡'●)
 */
public class Poll implements Serializable {

    private final String guildId;
    private final String creatorId;

    public Poll(String guildId, String creatorId) {
        this.guildId = guildId;
        this.creatorId = creatorId;
        open();
    }

    private void open() {


    }


    public void close() {


    }
    public String getGuildId() {
        return guildId;
    }

    public String getCreatorId() {
        return creatorId;
    }
}
