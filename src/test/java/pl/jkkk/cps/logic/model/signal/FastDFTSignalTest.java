package pl.jkkk.cps.logic.model.signal;

import org.apache.commons.math3.complex.Complex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FastDFTSignalTest {
    
    private double[] x = {1, 2, 3, 4};

    private FastDFTSignal signal = new FastDFTSignal(
        new DiscreteSignal(0.0, x.length, 1.0){
            @Override
            public double value(int n) {
                return x[n];
            }
        });

    @Test
    public void calculateInSituFFT() {
        double[] x = {1, 2, 3, 4};
        double[] yr = {10, -2, -2, -2};
        double[] yi = {0.0, 2, 0.0, -2};
        Complex[] dft = signal.inSituFFT(x);
        for (int i = 0; i < dft.length; i++) {
            Assertions.assertEquals(yr[i], dft[i].getReal(), 0.00000001);
            Assertions.assertEquals(yi[i], dft[i].getImaginary(), 0.00000001);
        }
    }

    @Test
    public void calculateRecursiveFFT() {
        double[] x = {1, 2, 3, 4};
        double[] yr = {10, -2, -2, -2};
        double[] yi = {0.0, 2, 0.0, -2};
        for (int i = 0; i < x.length; i++) {
            Assertions.assertEquals(yr[i], signal.recursiveFFT(x, i).getReal(), 0.00000001);
            Assertions.assertEquals(yi[i], signal.recursiveFFT(x, i).getImaginary(), 0.00000001);
        }
    }

    @Test
    public void vectorW() {
        Complex[] vectorW = signal.calculateVectorOfWParams(8);
        double W2arg = 2 * Math.PI / 2;
        Complex W2 = new Complex(Math.cos(W2arg), Math.sin(W2arg));
        double W4arg = 2 * Math.PI / 4;
        Complex W4 = new Complex(Math.cos(W4arg), Math.sin(W4arg));

        //N = 2, k = 1
        Assertions.assertEquals(W2.pow(new Complex(1)).getReal(), 
                signal.retrieveWFromVector(2, 1, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W2.pow(new Complex(1)).getImaginary(), 
                signal.retrieveWFromVector(2, 1, vectorW).getImaginary(), 0.00000001);

        //N = 2, k = 2
        Assertions.assertEquals(W2.pow(new Complex(2)).getReal(), 
                signal.retrieveWFromVector(2, 2, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W2.pow(new Complex(2)).getImaginary(), 
                signal.retrieveWFromVector(2, 2, vectorW).getImaginary(), 0.00000001);

        //N = 2, k = -1
        Assertions.assertEquals(W2.pow(new Complex(-1)).getReal(), 
                signal.retrieveWFromVector(2, -1, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W2.pow(new Complex(-1)).getImaginary(), 
                signal.retrieveWFromVector(2, -1, vectorW).getImaginary(), 0.00000001);

        //N = 4, k = -1
        Assertions.assertEquals(W4.pow(new Complex(-1)).getReal(), 
                signal.retrieveWFromVector(4, -1, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W4.pow(new Complex(-1)).getImaginary(), 
                signal.retrieveWFromVector(4, -1, vectorW).getImaginary(), 0.00000001);

        //N = 4, k = 3
        Assertions.assertEquals(W4.pow(new Complex(3)).getReal(), 
                signal.retrieveWFromVector(4, 3, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W4.pow(new Complex(3)).getImaginary(), 
                signal.retrieveWFromVector(4, 3, vectorW).getImaginary(), 0.00000001);

        //N = 4, k = -2
        Assertions.assertEquals(W4.pow(new Complex(-2)).getReal(), 
                signal.retrieveWFromVector(4, -2, vectorW).getReal(), 0.00000001);
        Assertions.assertEquals(W4.pow(new Complex(-2)).getImaginary(), 
                signal.retrieveWFromVector(4, -2, vectorW).getImaginary(), 0.00000001);
    }

    @Test
    public void mixSamples() {
        double[] x = {0, 1, 2, 3, 4, 5, 6, 7};
        double[] y = {0, 4, 2, 6, 1, 5, 3, 7};
        Assertions.assertArrayEquals(y, signal.mixSamples(x));
    }

    @Test
    public void reverseBits() {
        Assertions.assertEquals(0, signal.reverseBits(0, 3));
        Assertions.assertEquals(4, signal.reverseBits(1, 3));
        Assertions.assertEquals(2, signal.reverseBits(2, 3));
        Assertions.assertEquals(6, signal.reverseBits(3, 3));
        Assertions.assertEquals(1, signal.reverseBits(4, 3));
        Assertions.assertEquals(5, signal.reverseBits(5, 3));
        Assertions.assertEquals(3, signal.reverseBits(6, 3));
        Assertions.assertEquals(7, signal.reverseBits(7, 3));
    }
}
