package net.propvp.webbybot.command.console.commands;

import net.dv8tion.jda.api.JDA;
import net.propvp.webbybot.command.console.ConsoleCommand;

/**
 * Created on April 12, 2023 | 17:46:22
 * (●'◡'●)
 */
public class GuildsCommand extends ConsoleCommand {

    private final JDA jda;
    public GuildsCommand(JDA jda) {
        super("guilds");
        this.jda = jda;
        setActionOnRun(this::sendGuilds);
    }

    private void sendGuilds() {
        System.out.println("> Guild List <");
        System.out.println(getDesignLine());
        this.jda.getGuilds().forEach(guild -> {
            System.out.println("- "+ guild.getName());
        });

        System.out.println(getDesignLine());
    }
}
