package ru.pa4ok.library.javafx.form;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import ru.pa4ok.library.javafx.FxUtils;

import java.util.function.Predicate;

@Getter
public class InputTextAreaForm extends GridPane
{
    @Setter
    private Predicate<String> filter;

    @FXML
    protected Label inputFieldName;

    @FXML
    protected TextArea inputField;

    @FXML
    protected ColumnConstraints leftColumnConstraints;

    @FXML
    protected ColumnConstraints rightColumnConstraints;

    public InputTextAreaForm(String fieldName, Predicate<String> filter)
    {
        FxUtils.loadFxmlAndController(this, "util/");

        this.inputFieldName.setText(fieldName);
        this.filter = filter;
    }

    public InputTextAreaForm(String fieldName)
    {
        this(fieldName, null);
    }

    public String getInputText() {
        return inputField.getText();
    }

    public boolean checkFilter()
    {
        return filter == null || filter.test(inputField.getText());
    }
}
