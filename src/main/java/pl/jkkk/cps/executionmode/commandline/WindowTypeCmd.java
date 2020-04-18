package pl.jkkk.cps.executionmode.commandline;

import pl.jkkk.cps.logic.model.window.BlackmanWindow;
import pl.jkkk.cps.logic.model.window.HammingWindow;
import pl.jkkk.cps.logic.model.window.HanningWindow;
import pl.jkkk.cps.logic.model.window.RectangularWindow;
import pl.jkkk.cps.logic.model.window.Window;

import java.util.Arrays;

public enum WindowTypeCmd {
    RECTANGULAR_WINDOW("win_rect"),
    HAMMING_WINDOW("win_ham"),
    HANNING_WINDOW("win_han"),
    BLACKMAN_WINDOW("win_bla");

    private final String name;

    WindowTypeCmd(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WindowTypeCmd fromString(final String text) {
        return Arrays.asList(WindowTypeCmd.values())
                .stream()
                .filter(operation -> operation.name.equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Window fromEnum(WindowTypeCmd windowTypeCmd, int M) {
        switch (windowTypeCmd) {
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
