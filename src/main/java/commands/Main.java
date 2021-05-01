package commands;

import interfaces.ICommand;
import util.MenuUtil;

import java.util.ArrayList;

public class Main implements ICommand {
    @Override
    public void execute() {
        ArrayList<ICommand> commands = new ArrayList<>();
        commands.add(new NonlinearEquationsCommand());
        commands.add(new SystemNonlinearEquationsCommand());

        MenuUtil menu = new MenuUtil(commands);
        menu.execute();
    }

    @Override
    public String getMessage() {
        return "Назад";
    }
}
