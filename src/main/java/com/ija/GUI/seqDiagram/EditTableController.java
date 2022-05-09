/**
 * @file EditTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file is used as controller for all Edit Component panes
 */
package com.ija.GUI.seqDiagram;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.ija.Application.App;
import com.ija.backend.diagram.SeqRelation;
import com.ija.backend.diagram.SeqRelation.SeqRelEnum;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class EditTableController implements Initializable{
    @FXML TextField newNameField;
    @FXML TextField methodTextField;
    @FXML TextField noteTextField;
    @FXML ComboBox<String> startComboBox;
    @FXML ComboBox<String> endComboBox;
    @FXML ComboBox<String> relationComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {   
        
        if (App.containsMethod("setFirst")) {
            try {
                List<String> relations = Arrays.asList(SeqRelEnum.values())
                        .stream()
                        .map(f -> f.toString())
                        .collect(Collectors.toList());
                relationComboBox.setItems(FXCollections.observableArrayList(relations));
                String current = ((SeqRelation)App.getSelected().getElement()).getType().toString();
                relationComboBox.setValue(current);
            } catch (IllegalArgumentException | SecurityException e) {
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
            App.getSelected().updateContent();
            App.addUndo();
        }
    }

    @FXML
    protected void updateRelation(Event event) {
        if (!App.containsMethod("setType"))
            return;

        if (!((SeqRelation) App.getElement()).setType(SeqRelEnum.valueOf(relationComboBox.getValue())))
            relationComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        else {
            relationComboBox.setStyle(null);
            App.getSelected().updateContent();
            App.addUndo();
        }
    }

    @FXML
    protected void updateMethod(Event event) {
        if (!App.containsMethod("setMethod"))
            return;

        if (!((SeqRelation)App.getElement()).setMethod(methodTextField.getText())) {
            methodTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            methodTextField.setStyle(null);
            App.getSelected().updateContent();
            App.addUndo();
        }
    }

    @FXML
    protected void updateNote(Event event) {
        if (!App.containsMethod("setNote"))
            return;

        ((SeqRelation)App.getElement()).setNote(noteTextField.getText());
        App.getSelected().updateContent();
        App.addUndo();
    }

    @FXML
    protected void deleteAttribute(Event event) {
        if (App.getSelected() == App.getCurrentDiagram()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setContentText("Are you sure you want to delete this diagram [undo is not possible]?");

            alert.showAndWait().ifPresent(present -> {
                if (present == ButtonType.OK) {
                    App.getSelected().removeSelf(App.getCurrentPane());
                }
            });
        } else {
            App.getSelected().removeSelf(App.getCurrentPane());
        }
    }
}
