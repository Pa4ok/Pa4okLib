package ru.pa4ok.library.javafx;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TableViewUtil
{
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    public static <T> void setDraggableRowFactory(TableView<T> table, Predicate<T> setRowIndexFunction, Consumer<List<T>> updateAllRowsFunction)
    {
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        boolean updateIndex = setRowIndexFunction != null;
        boolean updateAll = updateAllRowsFunction != null;

        table.setRowFactory(tv ->
        {
            TableRow<T> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != (Integer) db.getContent(SERIALIZED_MIME_TYPE)) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    T draggedElement = table.getItems().remove(draggedIndex);

                    int dropIndex ;
                    if (row.isEmpty()) {
                        dropIndex = table.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    table.getItems().add(dropIndex, draggedElement);
                    if(updateIndex) {
                        List<T> updateList = new ArrayList<>();
                        for(int i=dropIndex; i<table.getItems().size(); i++) {
                            T item = table.getItems().get(i);
                            if(setRowIndexFunction.test(item) && updateAll) {
                                updateList.add(item);
                            }
                        }
                        if(updateAll && !updateList.isEmpty()) {
                            updateAllRowsFunction.accept(updateList);
                        }
                    }

                    event.setDropCompleted(true);
                    table.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row ;
        });
    }

    public static <T> void setDraggableRowFactory(TableView<T> table)
    {
        setDraggableRowFactory(table, null, null);
    }

    public static <T> void setMultiRowDraggableRowFactory(TableView<T> table, Predicate<T> setRowIndexFunction, Consumer<List<T>> updateAllRowsFunction)
    {
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        boolean updateIndex = setRowIndexFunction != null;
        boolean updateAll = updateAllRowsFunction != null;

        List<T> selections = new ArrayList<>();

        table.setRowFactory(tv ->
        {
            TableRow<T> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();

                    selections.clear();
                    selections.addAll(table.getSelectionModel().getSelectedItems());

                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != (Integer) db.getContent(SERIALIZED_MIME_TYPE)) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();

                if (db.hasContent(SERIALIZED_MIME_TYPE)) {

                    int dropIndex;
                    T item = null;
                    if (row.isEmpty()) {
                        dropIndex = table.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                        item = table.getItems().get(dropIndex);
                    }

                    int delta=0;
                    if(item != null) {
                        while (selections.contains(item)) {
                            delta = 1;
                            --dropIndex;
                            if (dropIndex < 0) {
                                item = null;
                                dropIndex = 0;
                                break;
                            }
                            item = table.getItems().get(dropIndex);
                        }
                    }

                    table.getItems().removeAll(selections);

                    if(item != null) {
                        dropIndex=table.getItems().indexOf(item)+delta;
                    } else if(dropIndex!=0) {
                        dropIndex=table.getItems().size();
                    }

                    int startDropIndex = dropIndex;
                    table.getSelectionModel().clearSelection();
                    for(T t : selections) {
                        table.getItems().add(dropIndex, t);
                        table.getSelectionModel().select(dropIndex);
                        dropIndex++;
                    }

                    if(updateIndex && startDropIndex != dropIndex) {
                        List<T> updateList = new ArrayList<>();
                        for(int i=startDropIndex; i<table.getItems().size(); i++) {
                            item = table.getItems().get(i);
                            if(setRowIndexFunction.test(item) && updateAll) {
                                updateList.add(item);
                            }
                        }
                        if(updateAll && !updateList.isEmpty()) {
                            updateAllRowsFunction.accept(updateList);
                        }
                    }

                    event.setDropCompleted(true);
                    selections.clear();
                    event.consume();
                }
            });

            return row ;
        });
    }

    public static <T> void setMultiRowDraggableRowFactory(TableView<T> table)
    {
        setMultiRowDraggableRowFactory(table, null, null);
    }
}