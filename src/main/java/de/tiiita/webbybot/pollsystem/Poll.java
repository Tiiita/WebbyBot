package de.tiiita.webbybot.pollsystem;

import net.dv8tion.jda.api.entities.Member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on April 12, 2023 | 22:54:30
 * (●'◡'●)
 */
public class Poll implements Serializable {

    private final String guildId;
    private final String creatorId;
    private final List<String> answers;

    public Poll(String guildId, String creatorId, List<String> answers) {
        this.guildId = guildId;
        this.answers = answers;
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

    //Please do not use getAnswers().add or something. Just get the answers with it.
    public List<String> getAnswers() {
        return answers;
    }
}
