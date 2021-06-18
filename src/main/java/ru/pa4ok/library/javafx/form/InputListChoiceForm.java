package ru.pa4ok.library.javafx.form;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ru.pa4ok.library.javafx.FxUtils;

import java.util.List;

public class InputListChoiceForm<T> extends GridPane
{
    @FXML
    private Label inputFieldName;

    @FXML
    private ComboBox<T> choiceField;

    private List<T> choices;

    public InputListChoiceForm(String inputFieldName, List<T> choices)
    {
        this.choices = choices;

        FxUtils.loadFxmlAndController(this);

        this.inputFieldName.setText(inputFieldName);
        choiceField.getItems().addAll(this.choices);
        choiceField.getSelectionModel().select(0);
    }

    public T getChoice() {
        return choiceField.getValue();
    }

    public ComboBox<T> getChoiceField() {
        return choiceField;
    }

    public List<T> getChoices() {
        return choices;
    }

    public void setChoices(List<T> choices) {
        this.choices = choices;
    }
}
