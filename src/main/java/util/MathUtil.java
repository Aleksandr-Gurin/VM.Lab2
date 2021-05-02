package util;

import interfaces.IFunction;
import interfaces.ISystem;
import model.Iteration;
import model.Point;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeoutException;

public class MathUtil {
    public static void solveFunction(IFunction function) {
        Scanner scanner = new Scanner(System.in);

        double left, right, eps;
        System.out.println("Введите левую границу:");
        left = ReadUtil.readDouble(scanner);
        System.out.println("Введите правую границу:");
        right = ReadUtil.readDouble(scanner);

        if (left > right) {
            double t = left;
            left = right;
            right = t;
        }

        System.out.println("Введите точность:");
        while (true) {
            eps = ReadUtil.readDouble(scanner);
            if (eps > 0 && eps < 1)
                break;
            else
                System.out.println("Точность должна быть больше 0 и меньше 1. " +
                                   "Попробуйте еще раз:");
        }

        ArrayList<Point> points = new ArrayList<>();
        double divisionPoint, chordPoint;

        if (checkDivision(function, left, right)) {
            divisionPoint = solveDivision(function, left, right, eps);
            if (checkPoint(left, right, divisionPoint)) {
                System.out.println("Результат метода деления пополам: " + divisionPoint);
                points.add(new Point(divisionPoint, 0));
            } else {
                System.out.println("Результат метода деления пополам: решение не удовлетворяет заданному интервалу");
            }
        } else {
            System.out.println("Уравнение не имеет решений методом деления пополам.");
        }
        chordPoint = solveChord(function, left, right, eps);
        if (checkPoint(left, right, chordPoint)) {
            System.out.println("Результат метода хорд: " + chordPoint);
            points.add(new Point(chordPoint, 0));
        } else {
            System.out.println("Результат метода хорд: решение не удовлетворяет заданному интервалу");
        }

        new GraphicPanel(function, points, left, right);
    }

    public static void solveSystem(ISystem system) {
        Scanner scanner = new Scanner(System.in);

        double x, y, eps;
        System.out.println("Введите приближение x:");
        x = ReadUtil.readDouble(scanner);
        System.out.println("Введите приближение y:");
        y = ReadUtil.readDouble(scanner);

        System.out.println("Введите точность:");
        while (true) {
            eps = ReadUtil.readDouble(scanner);
            if (eps > 0 && eps < 1)
                break;
            else
                System.out.println("Точность должна быть больше 0 и меньше 1. " +
                                   "Попробуйте еще раз:");
        }

        try {
            Stack<Iteration> iterations = solveNewton(system, x, y, eps);
            Iteration lastIteration = iterations.pop();
            iterations.forEach(Iteration::print);
            ArrayList<Point> points = new ArrayList<>();
            points.add(new Point(lastIteration.getX(), lastIteration.getY()));
            new GraphicPanel(system.getFunctions(), points, -10, 10);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static Stack<Iteration> solveNewton(ISystem system, double previousX, double previousY, double eps) throws TimeoutException {
        Stack<Iteration> iterations = new Stack<>();
        double x, y;
        int iteration = 0;
        while (true) {
            if (iteration < 1000) {
                double[][] inverseJ = system.inverseJ(previousX, previousY);
                if (inverseJ != null) {
                    double h1 = inverseJ[0][0] * system.solveF1(previousX, previousY)
                                + inverseJ[0][1] * system.solveF2(previousX, previousY);
                    double h2 = inverseJ[1][0] * system.solveF1(previousX, previousY)
                                + inverseJ[1][1] * system.solveF2(previousX, previousY);
                    x = previousX - h1;
                    y = previousY - h2;
                    double epsX = Math.abs(x - previousX);
                    double epsY = Math.abs(y - previousY);
                    iterations.push(new Iteration(previousX, previousY, epsX, epsY, iteration++));
                    if (Math.max(epsX, epsY) <= eps) {
                        iterations.push(new Iteration(x, y, 0, 0, iteration));
                        return iterations;
                    }
                    previousX = x;
                    previousY = y;
                } else
                    throw new IllegalStateException("Определитель Якообиана равен нулю");
            }else
                throw new TimeoutException("Количество итераций больше 1000");
        }
    }

    private static double solveChord(IFunction function, double left, double right, double eps) {
        while (Math.abs(right - left) > eps) {
            left = right - (right - left) * function.solve(right) / (function.solve(
                    right) - function.solve(left));
            right = left - (left - right) * function.solve(left) / (function.solve(left) - function.solve(right));
        }
        return right;
    }

    private static boolean checkPoint(double left, double right, double point) {
        return point >= left && point <= right;
    }

    private static double solveDivision(IFunction function, double left, double right, double eps) {
        double dx = 0, xi = 0;
        if (function.solve(left) == 0) {
            return left;
        }
        if (function.solve(right) == 0) {
            return right;
        }
        while (right - left > eps) {
            dx = (right - left) / 2.0;
            xi = left + dx;
            if (sign(function.solve(left)) == sign(function.solve(xi))) left = xi;
            else right = xi;
        }
        return xi;
    }

    private static boolean checkDivision(IFunction function, double left, double right) {
        for (double i = left; i < right; i += 0.5) {
            for (double j = left; j < right; j += 0.5) {
                if (function.solve(i) * function.solve(j) <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int sign(double x) {
        if (x > 0) return 1;
        else if (x < 0) return -1;
        return 0;
    }
}
