package pl.jkkk.cps.logic.model.enumtype;

import pl.jkkk.cps.logic.model.window.BlackmanWindow;
import pl.jkkk.cps.logic.model.window.HammingWindow;
import pl.jkkk.cps.logic.model.window.HanningWindow;
import pl.jkkk.cps.logic.model.window.RectangularWindow;
import pl.jkkk.cps.logic.model.window.Window;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WindowType {

    /*------------------------ FIELDS REGION ------------------------*/
    RECTANGULAR_WINDOW("Okno Prostokątne"),
    HAMMING_WINDOW("Okno Hamminga"),
    HANNING_WINDOW("Okno Hanninga"),
    BLACKMAN_WINDOW("Okno Blackmana");

    private final String name;

    /*------------------------ METHODS REGION ------------------------*/
    WindowType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WindowType fromString(final String text) {
        return Arrays.asList(WindowType.values())
                .stream()
                .filter((it) -> it.getName().equals(text))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<String> getNamesList() {
        return Arrays.asList(WindowType.values())
                .stream()
                .map((it) -> it.getName())
                .collect(Collectors.toList());
    }

    public static Window fromEnum(WindowType windowType, int M) {
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
