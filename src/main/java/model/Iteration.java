package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Iteration {
    private double x, y, epsX, epsY;
    private int iteration;

    public void print() {
        System.out.println("Iteration №" + iteration + "\n"
                           + "x = " + x + "\n"
                           + "y = " + y + "\n"
                           + "|Xn+1 - Xn| = " + epsX
                           + "|Yn+1 - Yn| = " + epsY);
    }
}
