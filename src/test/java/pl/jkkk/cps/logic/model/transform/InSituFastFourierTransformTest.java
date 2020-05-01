package pl.jkkk.cps.logic.model.transform;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InSituFastFourierTransformTest {

    @Test
    public void transform() {
        Complex[] x = {
            new Complex(1.0),
            new Complex(2.0),
            new Complex(3.0),
            new Complex(4.0)
        };
        Complex[] X = {
            new Complex(10.0, 0.0),
            new Complex(-2.0, 2.0),
            new Complex(-2.0, 0.0),
            new Complex(-2.0, -2.0)
        };
        Complex[] result = new InSituFastFourierTransform().transform(x);
        for (int i = 0; i < x.length ;i++) {
            Assertions.assertEquals(X[i].getReal(), result[i].getReal(), 0.0000001);
            Assertions.assertEquals(X[i].getImaginary(), result[i].getImaginary(), 0.0000001);
        }
    }

    @Test
    public void vectorW() {
        InSituFastFourierTransform transform = new InSituFastFourierTransform();

        Complex[] vectorW = transform.calculateVectorOfWParams(8);
        double W2arg = 2 * Math.PI / 2;
        Complex W2 = new Complex(Math.cos(W2arg), Math.sin(W2arg));
        double W4arg = 2 * Math.PI / 4;
        Complex W4 = new Complex(Math.cos(W4arg), Math.sin(W4arg));

        //N = 2, k = 1
        Assertions.assertEquals(W2.pow(new Complex(1)).getReal(), 
                transform.retrieveWFromVector(2, 1, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W2.pow(new Complex(1)).getImaginary(), 
                transform.retrieveWFromVector(2, 1, vectorW).getImaginary(), 0.00000001);

        //N = 2, k = 2
        Assertions.assertEquals(W2.pow(new Complex(2)).getReal(), 
                transform.retrieveWFromVector(2, 2, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W2.pow(new Complex(2)).getImaginary(), 
                transform.retrieveWFromVector(2, 2, vectorW).getImaginary(), 0.00000001);

        //N = 2, k = -1
        Assertions.assertEquals(W2.pow(new Complex(-1)).getReal(), 
                transform.retrieveWFromVector(2, -1, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W2.pow(new Complex(-1)).getImaginary(), 
                transform.retrieveWFromVector(2, -1, vectorW).getImaginary(), 0.00000001);

        //N = 4, k = -1
        Assertions.assertEquals(W4.pow(new Complex(-1)).getReal(), 
                transform.retrieveWFromVector(4, -1, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W4.pow(new Complex(-1)).getImaginary(), 
                transform.retrieveWFromVector(4, -1, vectorW).getImaginary(), 0.00000001);

        //N = 4, k = 3
        Assertions.assertEquals(W4.pow(new Complex(3)).getReal(), 
                transform.retrieveWFromVector(4, 3, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W4.pow(new Complex(3)).getImaginary(), 
                transform.retrieveWFromVector(4, 3, vectorW).getImaginary(), 0.00000001);

        //N = 4, k = -2
        Assertions.assertEquals(W4.pow(new Complex(-2)).getReal(), 
                transform.retrieveWFromVector(4, -2, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W4.pow(new Complex(-2)).getImaginary(), 
                transform.retrieveWFromVector(4, -2, vectorW).getImaginary(), 0.00000001);
    }

    @Test
    public void mixSamples() {
        InSituFastFourierTransform transform = new InSituFastFourierTransform();
        Complex[] x = {
            new Complex(0), new Complex(1), new Complex(2), new Complex(3),
            new Complex(4), new Complex(5), new Complex(6), new Complex(7)
        };
        Complex[] y = {
            new Complex(0), new Complex(4), new Complex(2), new Complex(6),
            new Complex(1), new Complex(5), new Complex(3), new Complex(7)
        };
        Complex[] z = transform.mixSamples(x);
        for (int i = 0; i < z.length; i++) {
            Assertions.assertEquals(y[i].getReal(), z[i].getReal(), 0.0000001);
            Assertions.assertEquals(y[i].getImaginary(), z[i].getImaginary(), 0.0000001);
        }
    }

    @Test
    public void reverseBits() {
        InSituFastFourierTransform transform = new InSituFastFourierTransform();
        Assertions.assertEquals(0, transform.reverseBits(0, 3));
        Assertions.assertEquals(4, transform.reverseBits(1, 3));
        Assertions.assertEquals(2, transform.reverseBits(2, 3));
        Assertions.assertEquals(6, transform.reverseBits(3, 3));
        Assertions.assertEquals(1, transform.reverseBits(4, 3));
        Assertions.assertEquals(5, transform.reverseBits(5, 3));
        Assertions.assertEquals(3, transform.reverseBits(6, 3));
        Assertions.assertEquals(7, transform.reverseBits(7, 3));
    }
}
