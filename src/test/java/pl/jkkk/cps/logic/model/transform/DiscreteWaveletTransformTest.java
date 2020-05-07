package pl.jkkk.cps.logic.model.transform;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DiscreteWaveletTransformTest {

    @Test
    public void transform() {
        double[] x = {1, 2, 3, 4};
        System.out.println(Arrays.toString(new DiscreteWaveletTransform().transform(x)));
    }
}
