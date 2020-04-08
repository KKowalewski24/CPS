package pl.jkkk.cps.textinput;

import pl.jkkk.cps.logic.model.signal.Signal;
import pl.jkkk.cps.logic.model.signal.UnitJumpSignal;

public class MainText {
    public void main(String[] args) {
        Operation operation = Operation.fromString(args[0]);
        switch (operation) {
        case GENERATE:
            Signal signal = null;
            SignalType signalType = SignalType.fromString(args[1]);
            switch (signalType) {
            case UNIT_JUMP:
                signal = new UnitJumpSignal(Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]), Double.parseDouble(args[5]));
            }
        }
    }
}
