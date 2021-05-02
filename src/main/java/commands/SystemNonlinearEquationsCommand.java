package commands;

import interfaces.ICommand;
import interfaces.ISystem;
import model.FirstSystem;
import model.SecondSystem;
import util.MathUtil;
import util.MenuUtil;

import java.util.ArrayList;

public class SystemNonlinearEquationsCommand implements ICommand {
    @Override
    public void execute() {
        ArrayList<ICommand> commands = new ArrayList<>();
        ArrayList<ISystem> systems = new ArrayList<>();

        systems.add(new FirstSystem());
        systems.add(new SecondSystem());

        for (ISystem system : systems){
            commands.add(new ICommand() {
                @Override
                public String getMessage() {
                    return system.getSystem();
                }
                @Override
                public void execute() {
                    MathUtil.solveSystem(system);
                }
            });
        }
        commands.add(new Main());
        MenuUtil menu = new MenuUtil(commands);
        menu.execute();
    }

    @Override
    public String getMessage() {
        return "Решение систем нелинейных уравнений";
    }
}
