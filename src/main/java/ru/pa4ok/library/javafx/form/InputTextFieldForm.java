package ru.pa4ok.library.javafx.form;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.pa4ok.library.javafx.FxUtils;

import java.util.function.Predicate;

public class InputTextFieldForm extends GridPane
{
    private Predicate<String> filter;

    @FXML
    private Label inputFieldName;

    @FXML
    private TextField inputField;

    public InputTextFieldForm(String fieldName, Predicate<String> filter)
    {
        FxUtils.loadFxmlAndController(this);

        this.inputFieldName.setText(fieldName);
        this.filter = filter;
    }

    public String getInputText() {
        return inputField.getText();
    }

    public InputTextFieldForm(String fieldName)
    {
        this(fieldName, null);
    }

    public boolean checkFilter()
    {
        return filter == null || filter.test(inputField.getText());
    }

    public Predicate<String> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<String> filter) {
        this.filter = filter;
    }

    public Label getInputFieldName() {
        return inputFieldName;
    }

    public TextField getInputField() {
        return inputField;
    }
}
