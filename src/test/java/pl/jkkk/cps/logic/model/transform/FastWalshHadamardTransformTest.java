package pl.jkkk.cps.logic.model.transform;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FastWalshHadamardTransformTest {
    @Test
    public void transform() {
        double[] x = {1.0, 2.0, 3.0, 4.0};
        double[] X = {10.0, -2.0, -4.0, 0.0};
        double[] result = new FastWalshHadamardTransform().transform(x);
        for (int i = 0; i < x.length; i++) {
            Assertions.assertEquals(X[i], result[i], 0.0000001);
        }
    }
}
