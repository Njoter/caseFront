package no.njoter.casefront.gui;

import javafx.scene.control.TextField;

public class NonEditableTextField extends TextField {

    public NonEditableTextField() {
        this.setEditable(false);
        this.setMouseTransparent(true);
        this.setFocusTraversable(false);
    }
}
