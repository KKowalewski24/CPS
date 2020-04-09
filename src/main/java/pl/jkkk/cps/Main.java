package pl.jkkk.cps;

import pl.jkkk.cps.executionmode.commandline.CommandLineMode;
import pl.jkkk.cps.executionmode.graphical.GraphicalMode;

public class Main {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public static void main(String[] args) {
        if (args.length == 0) {
            new GraphicalMode().main(args);
        } else {
            try {
                new CommandLineMode().main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

