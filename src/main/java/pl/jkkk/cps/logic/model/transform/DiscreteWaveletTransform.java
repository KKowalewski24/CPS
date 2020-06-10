package pl.jkkk.cps.logic.model.transform;

public class DiscreteWaveletTransform extends RealTransform {

    private static final double[] DB4 = {
            (1.0 + Math.sqrt(3.0)) / (4.0 * Math.sqrt(2)),
            (3.0 + Math.sqrt(3.0)) / (4.0 * Math.sqrt(2)),
            (3.0 - Math.sqrt(3.0)) / (4.0 * Math.sqrt(2)),
            (1.0 - Math.sqrt(3.0)) / (4.0 * Math.sqrt(2))
    };

    private static final double[] DB6 = {
            0.47046721, 1.14111692, 0.650365, -0.19093442, -0.12083221, 0.0498175
    };

    private static final double[] DB8 = {
            0.32580343, 1.01094572, 0.8922014, -0.03957503, -0.26450717, 0.0436163, 0.0465036,
            -0.01498699
    };

    @Override
    public double[] transform(final double[] x) {
        return waveletTransform(x, DB4);
    }

    public double[] waveletTransform(final double[] x, final double[] h) {
        //System.out.println(Arrays.toString(h));
        int N = x.length;
        double[] X = new double[N];
        for (int i = 0; i < N; i++) {
            double sum = 0.0;
            int begin = (i / 2) * 2;
            //for (int a = 0; a < begin; a++) System.out.print("\t\t\t");
            for (int j = begin; j < begin + h.length; j++) {
                double factor;
                if (i % 2 == 0) {
                    factor = h[(j - begin)];
                } else {
                    factor = h[(h.length - (j - begin) - 1)];
                    if ((j - begin) % 2 == 1) {
                        factor *= -1;
                    }
                }
                sum += (factor * x[j % N]);
                //System.out.print(((int)(factor * 1000)) / 1000.0 + " " + x[j%N] + "\t" );
            }
            X[i] = sum;
            //System.out.println();
        }
        /* mix samples */
        double[] mixedX = new double[N];
        int iterator = 0;
        for (int i = 0; i < N; i += 2) {
            mixedX[iterator++] = X[i];
        }
        for (int i = 1; i < N; i += 2) {
            mixedX[iterator++] = X[i];
        }
        return mixedX;
    }
}
