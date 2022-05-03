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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditTableController implements Initializable{
    @FXML TextField newNameField;
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
    protected void deleteAttribute(Event event) {
        App.getSelected().removeSelf(App.getCurrentPane());
    }
}
