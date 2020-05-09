package pl.jkkk.cps.logic.model.transform;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WalshHadamardTransformTest {

    @Test
    public void transform() {
        double[] x = {1.0, 2.0, 3.0, 4.0};
        double[] X = {10.0, -2.0, -4.0, 0.0};
        double[] result = new WalshHadamardTransform().transform(x);
        for (int i = 0; i < x.length; i++) {
            Assertions.assertEquals(X[i], result[i], 0.0000001);
        }
    }

    @Test
    public void pasteMatrixIntoMatrix() {
        double[] dst = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] src = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[] result = {0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 0, 0, 4, 5, 6, 0, 0, 7, 8, 9, 0, 0, 0, 0, 0};
        new WalshHadamardTransform().pasteMatrixIntoMatrix(src, 3, dst, 5, 1, 2, 1);
        Assertions.assertArrayEquals(dst, result, 0.0000001);
    }
}
