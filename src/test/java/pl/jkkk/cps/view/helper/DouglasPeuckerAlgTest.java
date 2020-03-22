package pl.jkkk.cps.view.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.jkkk.cps.logic.model.Data;

public class DouglasPeuckerAlgTest {

    private DouglasPeuckerAlg alg = new DouglasPeuckerAlg();

    @Test
    public void distanceTest() {
        Data a = new Data(0, 0);
        Data b = new Data(5, 0);
        Data c = new Data(2, 3);
        Assertions.assertEquals(3, alg.distance(a, b, c));
    }
}
