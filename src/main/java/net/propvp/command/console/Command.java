package net.propvp.command.console;

/**
 * Created on April 11, 2023 | 01:18:57
 * (●'◡'●)
 */
public abstract class Command {
    private final String commandName;
    private Runnable actionOnRun;


    public Command(String commandName) {
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
