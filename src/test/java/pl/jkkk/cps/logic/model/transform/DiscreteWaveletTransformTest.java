package pl.jkkk.cps.logic.model.transform;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DiscreteWaveletTransformTest {

    @Test
    public void transform() {
        double[] x = {1, 2, 3, 4};
        System.out.println(Arrays.toString(new DiscreteWaveletTransform().transform(x)));
    }
}
