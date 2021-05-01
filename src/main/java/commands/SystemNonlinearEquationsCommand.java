package commands;

import interfaces.ICommand;
import interfaces.ISystem;
import util.MenuUtil;

import java.util.ArrayList;

public class SystemNonlinearEquationsCommand implements ICommand {
    @Override
    public void execute() {
        ArrayList<ICommand> commands = new ArrayList<>();
        ArrayList<ISystem> sysFuncs = new ArrayList<>();

        sysFuncs.add(new FirstSysFunc());
        sysFuncs.add(new SecondSysFunc());

        for (ISystem func : sysFuncs){
            commands.add(new ICommand() {
                @Override
                public String getMessage() {
                    return func.getMessage();
                }
                @Override
                public void execute() {
                    MathModule.execute(func);
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
