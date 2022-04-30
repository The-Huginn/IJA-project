package com.ija.GUI.seqDiagram;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ija.Application.App;
import com.ija.GUI.MainWindowController;
import com.ija.backend.diagram.SeqRelation;
import com.ija.backend.diagram.SeqRelation.SeqRelEnum;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditTableController {
    @FXML TextField newNameField;
    @FXML ComboBox<String> startComboBox;
    @FXML ComboBox<String> endComboBox;
    @FXML ComboBox<String> relationComboBox;

    public EditTableController() {

        if (App.containsMethod("setFirst")) {
            try {
                List<String> relations = Arrays.asList(SeqRelEnum.values())
                                        .stream()
                                        .map(f -> f.toString())
                                        .collect(Collectors.toList());
                relationComboBox.setItems(FXCollections.observableArrayList(relations));
                String current = ((SeqRelEnum)App.getElement().getClass().getMethod("getType").invoke(App.getElement())).toString();
                relationComboBox.setValue(current);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    protected void setNewName(Event event) {
        if (!App.getElement().setName(newNameField.getText()))
            newNameField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        else {
            newNameField.setStyle(null);
            if (App.getElement() == App.getCurrentDiagram())
                ((MainWindowController)App.getLoader().getController()).diagramName.setText(App.getElement().getName());
            App.addUndo();
        }
    }

    @FXML
    protected void updateRelation(Event event) {
        if (!App.containsMethod("setType"))
            return;

        if (!((SeqRelation) App.getElement()).setType(SeqRelEnum.valueOf(relationComboBox.getValue())))
            relationComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); // TODO test if this works
        else {
            // TODO maybe
            relationComboBox.setStyle(null);
            App.addUndo();
        }
    }
}
