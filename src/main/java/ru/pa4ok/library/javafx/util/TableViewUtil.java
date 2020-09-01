package ru.pa4ok.library.javafx.util;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.TextAlignment;

import java.util.concurrent.atomic.AtomicLong;

public class TableViewUtil
{
    public static void autoResizeTableColumns(TableView<?> table)
    {
        table.setColumnResizePolicy((param) -> true );
        Platform.runLater(() -> autoResizeTableColumnsAction(table, 0));
    }

    public static void autoResizeTableColumns(TableView<?> table, int shift)
    {
        table.setColumnResizePolicy((param) -> true );
        Platform.runLater(() -> autoResizeTableColumnsAction(table, shift));
    }

    private static void autoResizeTableColumnsAction(TableView<?> table, int shift)
    {
        AtomicLong width = new AtomicLong();
        table.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = table.getWidth() - shift;

        if (tableWidth > width.get()) {
            table.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/table.getColumns().size()));
            });
        }
    }

    public static <T,V> TableColumn<T,V> createMultiLineHeaderColumn(String title, int prefWidth, int prefHeight)
    {
        TableColumn<T,V> column = new TableColumn<>();

        Label columnTitle = new Label(title);
        columnTitle.setPrefWidth(prefWidth);

        columnTitle.setWrapText(true);
        columnTitle.setTextAlignment(TextAlignment.CENTER);
        columnTitle.setPrefHeight(prefHeight);
        column.setGraphic(columnTitle);
        return column;
    }

    public static void preventColumnReordering(TableView<?> tableView) {
        tableView.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
            {
                TableHeaderRow header = (TableHeaderRow) tableView.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
    }
}
