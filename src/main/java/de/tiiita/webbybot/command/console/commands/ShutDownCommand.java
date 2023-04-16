package de.tiiita.webbybot.command.console.commands;

import de.tiiita.webbybot.WebbyBot;
import de.tiiita.webbybot.command.console.ConsoleCommand;

/**
 * Created on April 11, 2023 | 01:26:40
 * (●'◡'●)
 */
public class ShutDownCommand extends ConsoleCommand {
    public ShutDownCommand() {
        super("shutdown");
        setActionOnRun(() -> System.exit(0));
    }
}
