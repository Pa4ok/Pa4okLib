package ru.pa4ok.library.javafx.form;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import ru.pa4ok.library.javafx.FxUtils;

import java.util.function.Predicate;

@Getter
public class InputTextFieldForm extends GridPane
{
    @Setter
    protected Predicate<String> filter;

    @FXML
    protected Label inputFieldName;

    @FXML
    protected TextField inputField;

    @FXML
    protected ColumnConstraints leftColumnConstraints;

    @FXML
    protected ColumnConstraints rightColumnConstraints;

    public InputTextFieldForm(String fieldName, Predicate<String> filter)
    {
        FxUtils.loadFxmlAndController(this, "util/");

        this.inputFieldName.setText(fieldName);
        this.filter = filter;
    }

    public InputTextFieldForm(String fieldName)
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
