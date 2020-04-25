package pl.jkkk.cps.view.model;

import javafx.scene.Node;
import javafx.scene.control.Tab;

public class InnerDualCustomTab extends Tab {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public InnerDualCustomTab(String text, Node content, boolean isClosable) {
        super(text, content);
        super.setClosable(isClosable);
    }
}
    