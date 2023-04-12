package net.propvp.webbybot.command.console.commands;

import net.propvp.webbybot.command.console.ConsoleCommand;

/**
 * Created on April 11, 2023 | 01:26:40
 * (●'◡'●)
 */
public class ShutDownCommand extends ConsoleCommand {
    public ShutDownCommand() {
        super("shutdown");
        setActionOnRun(this::shutdown);
    }

    private void shutdown() {
        System.out.println("Shutting down...");
        System.exit(0);
    }
}
