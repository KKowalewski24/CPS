package pl.jkkk.cps.logic.model.transform;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiscreteFourierTransformTest {
    
    @Test
    public void transform() {
        Complex[] x = {
            new Complex(1.0),
            new Complex(2.0),
            new Complex(3.0),
            new Complex(4.0)
        };
        Complex[] X = {
            new Complex(2.5, 0.0),
            new Complex(-0.5, 0.5),
            new Complex(-0.5, 0.0),
            new Complex(-0.5, -0.5)
        };
        Complex[] result = new DiscreteFourierTransform().transform(x);
        for (int i = 0; i < x.length ;i++) {
            Assertions.assertEquals(X[i].getReal(), result[i].getReal(), 0.0000001);
            Assertions.assertEquals(X[i].getImaginary(), result[i].getImaginary(), 0.0000001);
        }
    }
}
