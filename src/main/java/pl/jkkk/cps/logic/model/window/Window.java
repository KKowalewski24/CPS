package pl.jkkk.cps.logic.model.window;

import pl.jkkk.cps.logic.model.enumtype.WindowType;

public interface Window {

    double w(int n);

    static Window fromEnum(WindowType windowType, int M) {
        switch (windowType) {
            case RECTANGULAR_WINDOW: {
                return new RectangularWindow();
            }
            case HAMMING_WINDOW: {
                return new HammingWindow(M);
            }
            case HANNING_WINDOW: {
                return new HanningWindow(M);
            }
            case BLACKMAN_WINDOW: {
                return new BlackmanWindow(M);
            }
        }

        return null;
    }
}
