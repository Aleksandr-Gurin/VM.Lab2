package interfaces;

import java.util.ArrayList;

public interface ISystem {
    double solveF1(double x, double y);
    double solveF2(double x, double y);
    double solveDF1X(double x, double y);
    double solveDF2X(double x, double y);
    double solveDF1Y(double x, double y);
    double solveDF2Y(double x, double y);
    double[][] inverseJ(double x, double y);
    double solveJ(double x, double y);
    String getSystem();
    ArrayList<IFunction> getFunctions();
}
