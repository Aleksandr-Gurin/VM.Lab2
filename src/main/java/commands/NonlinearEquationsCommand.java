package commands;

import interfaces.ICommand;
import interfaces.IFunction;
import util.MathUtil;
import util.MenuUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NonlinearEquationsCommand implements ICommand {
    @Override
    public void execute() {
        ArrayList<ICommand> commands = new ArrayList<>();
        HashMap<String, IFunction> funcHashMap = new HashMap<>();
        funcHashMap.put("x^2-2x-5", x -> Math.pow(x, 2) - (2 * x) - 5);
        funcHashMap.put("5x^2-4x+3", x -> 5 * Math.pow(x, 2) - (4 * x) + 3);
        funcHashMap.put("e^x-5", x -> Math.pow(Math.E, x) - 5);
        funcHashMap.put("x^3-x+4", x -> Math.pow(x, 3) -  x + 4);
        funcHashMap.put("3x^7-x^5+7x-1", x -> 3 * Math.pow(x, 7) - Math.pow(x, 5)
                                              + (7 * x) - 1);

        for(Map.Entry<String, IFunction> entry : funcHashMap.entrySet()) {
            commands.add(new ICommand() {
                @Override
                public String getMessage() {
                    return entry.getKey();
                }
                @Override
                public void execute() {
                    MathUtil.solveFunction(entry.getValue());
                }
            });
        }
//        commands.add(new InputFunction());
        commands.add(new Main());
        MenuUtil menu = new MenuUtil(commands);
        menu.execute();
    }

    @Override
    public String getMessage() {
        return "Методы решения нелинейных уравнений";
    }
}
