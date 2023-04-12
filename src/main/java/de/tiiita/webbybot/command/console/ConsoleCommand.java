package de.tiiita.webbybot.command.console;

/**
 * Created on April 11, 2023 | 01:18:57
 * (●'◡'●)
 */
public abstract class ConsoleCommand {
    private final String commandName;
    private Runnable actionOnRun;


    public String getDesignLine() {
        return "————————————————————————";
    }

    public ConsoleCommand(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public Runnable getActionOnRun() {
        return actionOnRun;
    }

    public void setActionOnRun(Runnable actionOnRun) {
        this.actionOnRun = actionOnRun;
    }
}
