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
        listenToCommand();
    }

    public void registerCommand(Command command) {
        registeredConsoleCommands.add(command);
    }


    private void listenToCommand() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.trim().length() == 0) {
            listenToCommand();
            return;
        }

        for (Command command : registeredConsoleCommands) {
            if (!input.equalsIgnoreCase(command.getCommandName())) continue;
            if (command.getActionOnRun() == null) continue;
            command.getActionOnRun().run();
            listenToCommand(); //Start listening again
        }
        actionOnCommandNotFound(input);

    }


    private void actionOnCommandNotFound(String input) {
        if (registeredConsoleCommands.isEmpty()) {
            System.out.println("No commands registered...");
            listenToCommand();
            return;
        }
        System.out.println("Command '" + input + "' was not found!");
        System.out.println("List of Commands:");
        registeredConsoleCommands.forEach(command1 -> {
            System.out.println("- " + command1.getCommandName());
        });
        listenToCommand();
    }
}
