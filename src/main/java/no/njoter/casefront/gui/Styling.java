package no.njoter.casefront.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Styling {

    public static Border setRegularBorder(boolean top, boolean right, boolean bottom, boolean left) {

        // Settings
        Color color = Color.BLACK;
        CornerRadii radii = CornerRadii.EMPTY;
        BorderWidths bWidths = BorderWidths.DEFAULT;
        Insets insets = Insets.EMPTY;

        // Set which sides are drawn
        boolean[] sides = {top, right, bottom, left};
        BorderStrokeStyle[] bsStyle = new BorderStrokeStyle[4];
        for (int i = 0; i < bsStyle.length; i++) {
            if (sides[i]) {
                bsStyle[i] = BorderStrokeStyle.SOLID;
            } else {
                bsStyle[i] = BorderStrokeStyle.NONE;
            }
        }

        // Set the border
        BorderStroke bs = null;
        bs = new BorderStroke(
                color,
                color,
                color,
                color,
                bsStyle[0],
                bsStyle[1],
                bsStyle[2],
                bsStyle[3],
                radii,
                bWidths,
                insets
        );
        return new Border(bs);
    }
}
