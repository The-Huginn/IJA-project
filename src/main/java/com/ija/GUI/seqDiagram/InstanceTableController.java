/**
 * @file ClassInterfaceTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for Instance add tab
 */
package com.ija.GUI.seqDiagram;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.ija.Application.App;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagram.SeqRelation;
import com.ija.backend.diagram.SeqRelation.SeqRelEnum;
import com.ija.backend.diagramObject.UMLClass;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class InstanceTableController implements Initializable {
    @FXML TextField relationName;
    @FXML ComboBox<String> typeComboBox;
    @FXML ComboBox<String> relationComboBox;
    @FXML Spinner<Integer> instanceNumber;
    @FXML Button addRelation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> values = Arrays.asList(SeqRelEnum.values()).stream()
                                .map(f -> f.toString())
                                .collect(Collectors.toList());
        typeComboBox.setItems(FXCollections.observableArrayList(values));

        typeComboBox.setValue(values.get(0));
    }

    @FXML
    protected void addRelation(Event event) {
        SeqRelEnum type = SeqRelEnum.valueOf(typeComboBox.getValue());
        UMLClass selected = null;
        SeqDiagram diagram = (SeqDiagram) ((sUMLDiagram)App.getCurrentDiagram()).getElement();

        for (UMLClass var : App.getClassDiagram().getClasses()) {
            if (var.getName().equals(relationComboBox.getValue()))
                selected = var;
        }

        int start = ((UMLInstance)App.getSelected()).getInstanceNumber();
        int end = instanceNumber.getValue();

        SeqRelation relation = new SeqRelation(relationName.getText(),
                                                    diagram,
                                                    (UMLClass)App.getSelected().getElement(),
                                                    start,
                                                    selected,
                                                    end,
                                                    type);

        if (!((sUMLDiagram)App.getCurrentDiagram()).addNewRelation(relation)) {
            addRelation.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            addRelation.setStyle(null);
        }
    }

    @FXML
    protected void updateRelation(Event event) {
        List<String> classes = App.getClassDiagram().getClasses().stream()
                .map(f -> f.getName())
                .collect(Collectors.toList());
        relationComboBox.setItems(FXCollections.observableArrayList(classes));
    }

    @FXML
    protected void clearStyle(Event event) {
        ((TextField)event.getTarget()).setStyle(null);
    }
}
