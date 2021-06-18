package ru.pa4ok.library.javafx.form;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.pa4ok.library.javafx.FxUtils;
import ru.pa4ok.library.util.DataFilter;

public class InputTextFieldForm extends GridPane
{
    @FXML
    private Label inputFieldName;

    @FXML
    private TextField inputField;

    private DataFilter<String> filter;

    public InputTextFieldForm(String fieldName, DataFilter<String> filter)
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
        return filter == null ? true : filter.filter(inputField.getText());
    }

    public DataFilter<String> getFilter() {
        return filter;
    }

    public void setFilter(DataFilter<String> filter) {
        this.filter = filter;
    }

    public Label getInputFieldName() {
        return inputFieldName;
    }

    public TextField getInputField() {
        return inputField;
    }
}
