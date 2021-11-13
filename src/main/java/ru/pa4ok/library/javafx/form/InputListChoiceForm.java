package ru.pa4ok.library.javafx.form;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import ru.pa4ok.library.javafx.FxUtils;

import java.util.List;

@Getter
public class InputListChoiceForm<T> extends GridPane
{
    protected List<T> choices;

    @FXML
    protected Label inputFieldName;

    @FXML
    protected ComboBox<T> choiceField;

    @FXML
    protected ColumnConstraints leftColumnConstraints;

    @FXML
    protected ColumnConstraints rightColumnConstraints;


    public InputListChoiceForm(String inputFieldName, List<T> choices)
    {
        this.choices = choices;

        FxUtils.loadFxmlAndController(this, "util/");

        this.inputFieldName.setText(inputFieldName);
        choiceField.getItems().addAll(this.choices);
        choiceField.getSelectionModel().select(0);
    }

    public T getChoice() {
        return choiceField.getValue();
    }

    public SelectionModel<T> getSelectionModel() {
        return choiceField.getSelectionModel();
    }
}
