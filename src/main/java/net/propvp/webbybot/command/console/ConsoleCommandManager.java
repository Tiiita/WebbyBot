package net.propvp.webbybot.command.console;

import net.propvp.webbybot.command.console.commands.ShutDownCommand;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created on April 11, 2023 | 01:17:03
 * (●'◡'●)
 */
public class ConsoleCommandManager {

    private final Set<Command> registeredConsoleCommands = new HashSet<>();

    public ConsoleCommandManager() {
        registerCommand(new ShutDownCommand());
        startListening();
    }

    public void registerCommand(Command command) {
        registeredConsoleCommands.add(command);
    }


    private void startListening() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        for (Command command : registeredConsoleCommands) {
            if (!input.equalsIgnoreCase(command.getCommandName())) continue;
            if (command.getActionOnRun() == null) continue;
            command.getActionOnRun().run();
        }
    }
}
