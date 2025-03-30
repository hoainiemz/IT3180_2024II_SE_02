package org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VerticleTextAndTextArea extends VBox {
    private Text text;
    private TextArea textArea;

    public Text getText() {
        return text;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public VerticleTextAndTextArea(String text, String value, String prompt, String id, boolean editable) {
        super();
        if (id != null) {
            setId(id);
        }
        this.text = new Text(text);
        getChildren().add(this.text);
        this.textArea = new TextArea(value);
        this.textArea.setPromptText(prompt);
        this.textArea.setEditable(editable);
        getChildren().add(this.textArea);
        setAlignment(Pos.TOP_LEFT);
//        this.textArea.setPrefWidth(250);
        if (!editable) {
            this.textArea.getStyleClass().add("not-editable-text-field");
        } else {
            this.textArea.getStyleClass().add("editable-text-field");
        }
        getChildren().add(new Separator(Orientation.HORIZONTAL));
        setSpacing(0);
    }
}
