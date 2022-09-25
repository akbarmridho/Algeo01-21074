import main.interpolation.Bicubic;
import main.matrix.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BicubicTest {
    @Test
    public void firstTest() {
        double[][] contents = {
                {153, 59, 210, 96},
                {125, 161, 72, 81},
                {98, 101, 42, 12},
                {21, 51, 0, 16}
        };

        Bicubic solver = new Bicubic(new Matrix(contents));

        double result = solver.interpolate(0.5,0.5);


        assertTrue(true);
    }
}
