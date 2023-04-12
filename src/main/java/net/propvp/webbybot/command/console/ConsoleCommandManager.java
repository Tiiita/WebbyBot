package net.propvp.webbybot.command.console;

import net.propvp.webbybot.WebbyBot;
import net.propvp.webbybot.command.console.commands.InfoCommand;
import net.propvp.webbybot.command.console.commands.ShutDownCommand;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created on April 11, 2023 | 01:17:03
 * (●'◡'●)
 */
public class ConsoleCommandManager {


    private final WebbyBot plugin;
    private final Set<ConsoleCommand> registeredConsoleCommands = new HashSet<>();

    public ConsoleCommandManager(WebbyBot plugin) {
        this.plugin = plugin;
        registerCommand(new ShutDownCommand());
        registerCommand(new InfoCommand(plugin.getJda(), plugin.getSecurityConfig()));
        listenToCommand();
    }

    public void registerCommand(ConsoleCommand command) {
        registeredConsoleCommands.add(command);
    }


    private void listenToCommand() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.trim().length() == 0) {
            listenToCommand();
            scanner.close();
            return;
        }

        for (ConsoleCommand command : registeredConsoleCommands) {
            if (!input.equalsIgnoreCase(command.getCommandName())) continue;
            if (command.getActionOnRun() == null) continue;
            command.getActionOnRun().run();
            listenToCommand(); //Start listening again
            scanner.close();
        }
        actionOnCommandNotFound(input);
        scanner.close();

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
