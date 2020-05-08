package pl.jkkk.cps.logic.model.transform;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiscreteCosineTransformTest {

    @Test
    public void transform() {
        double[] x = { 1.0, 2.0, 3.0, 4.0 };
        double[] X = { 5.0, -2.2304425, 0, -0.15851267 };
        double[] result = new DiscreteCosineTransform().transform(x);
        for (int i = 0; i < x.length; i++) {
            Assertions.assertEquals(X[i], result[i], 0.0000001);
        }
    }

}
