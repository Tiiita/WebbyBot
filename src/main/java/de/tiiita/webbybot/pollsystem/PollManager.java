package de.tiiita.webbybot.pollsystem;

import de.tiiita.webbybot.util.Base64;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on April 12, 2023 | 22:54:18
 * (●'◡'●)
 */
public class PollManager {

    private final List<Poll> currentPolls = new ArrayList<>();

    public void createPoll(String guildId, Member creator) {
        Poll poll = new Poll(guildId, creator.getId());
        currentPolls.add(poll);
    }



    public void closePoll(Poll poll) {
        poll.close();
        currentPolls.remove(poll);
    }

    public String encodePoll(Poll poll) {
        return Base64.encode(poll);
    }


    public Poll decodePoll(String hash) {
        return Base64.decode(hash, Poll.class);
    }
}
