package util;

import java.util.Scanner;

public class ReadUtil {
    public static boolean isNumeric(String str) {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static double readDouble(Scanner scanner){
        while (true) {
            try {
                double d = Double.parseDouble(scanner.nextLine());
                return d;
            } catch (NumberFormatException nfe) {
                System.out.println("Неправильный ввод");
            }
        }
    }
}
