package model;

import interfaces.IFunction;
import interfaces.ISystem;

import java.util.ArrayList;

public class SecondSystem implements ISystem {
    @Override
    public double solveF1(double x, double y) {
        return x + y -4;
    }

    @Override
    public double solveF2(double x, double y) {
        return Math.pow(x,2) - 4*y + 0.85;
    }

    @Override
    public double solveDF1X(double x, double y) {
        return 1;
    }

    @Override
    public double solveDF2X(double x, double y) {
        return 2*x;
    }

    @Override
    public double solveDF1Y(double x, double y) {
        return 1;
    }

    @Override
    public double solveDF2Y(double x, double y) {
        return -4;
    }

    @Override
    public double[][] inverseJ(double x, double y) {
        double detJ = solveJ(x, y);
        if (detJ != 0) {
            return new double[][]{{solveDF2Y(x, y) / detJ, -solveDF1Y(x, y) / detJ},
                    {-solveDF2X(x,y)/detJ, solveDF1X(x,y)/detJ}};
        }
        return null;
    }

    @Override
    public double solveJ(double x, double y) {
        return solveDF1X(x, y) * solveDF2Y(x, y) - solveDF2X(x, y) * solveDF1Y(x, y);
    }

    @Override
    public String getSystem() {
        return "|x^2+y^2=3\n" +
               "   |sin(x-0.6)-y=1.6";
    }

    @Override
    public ArrayList<IFunction> getFunctions() {
        ArrayList<IFunction> functions = new ArrayList<>();
        functions.add(x -> 4-x);
        functions.add(x -> (Math.pow(x,2) + 0.85) / 4);
        return functions;
    }
}
