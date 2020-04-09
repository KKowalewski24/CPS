package pl.jkkk.cps;

import pl.jkkk.cps.executionmode.commandline.CommandLineMode;
import pl.jkkk.cps.executionmode.graphical.GraphicalMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    /**
     * Params for cmd mode
     * Generate - generate, filename to save, signal type, params for signal
     * Signal type abbreviation:
     * uni_noise, gauss_noise, sin, sin_one_half, sin_two_half, rect,
     * rect_symm, triang, unit_jump, unit_impulse, impulse_noise
     *
     *
     * Sampling - sampl, filename to read, filename to save, sampleRate
     *
     * Comparison - comp, first filename to read, second filename to read
     * Draw charts - draw, filenames to read...
     */

    /*------------------------ FIELDS REGION ------------------------*/
    private static List<String> mainArgs;

    /*------------------------ METHODS REGION ------------------------*/
    public static List<String> getMainArgs() {
        return Collections.unmodifiableList(mainArgs);
    }

    public static void main(String[] args) {
        mainArgs = Arrays.asList(args);

        if (args.length == 0) {
            new GraphicalMode().main();
        } else {
            try {
                new CommandLineMode().main();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

