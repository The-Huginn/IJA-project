/**
 * @file ClassDiagramTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for SeqDiagram edit tab
 */
package com.ija.GUI.seqDiagram;

import java.util.List;
import java.util.stream.Collectors;

import com.ija.Application.App;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagramObject.UMLClass;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class SeqDiagramTableController {
    @FXML TextField instanceName;
    @FXML Spinner<Integer> instanceNumber;
    @FXML ComboBox<String> diagramComboBox;

    @FXML
    protected void addInstance(Event event) {
        UMLClass instance = App.getClassDiagram().getClasses().stream()
                                .filter(f -> f.getName().equals(instanceName.getText()))
                                .findAny()
                                .orElse(null);
        
        SeqDiagram diagram = (SeqDiagram)App.getCurrentDiagram();
        if (!diagram.addInstance(instance, instanceNumber.getValue())) {
            instanceName.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            instanceNumber.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            // TODO show in GUI
            instanceName.setStyle(null);
            instanceNumber.setStyle(null);
            App.addUndo();
        }
    }

    @FXML
    protected void updateDiagrams(Event event) {
        if (App.getClassDiagram() != null) {
            List<SeqDiagram> diagrams = App.getClassDiagram().getDiagrams();
            List<String> names = diagrams.stream()
                                        .map(f -> f.getName())
                                        .collect(Collectors.toList());
            diagramComboBox.setItems(FXCollections.observableArrayList(names));
        }
    }

    @FXML
    protected void switchDiagram(Event event) {
        if (App.getClassDiagram() != null) {
            for (SeqDiagram diagram : App.getClassDiagram().getDiagrams())
                if (diagram.getName().equals(diagramComboBox.getValue())) {
                    App.setCurrentDiagram(diagram);
                    break;
                }
        }
    }

    @FXML
    protected void clearStyle(Event event) {
        if (event.getTarget() instanceof TextField)
            ((TextField)event.getTarget()).setStyle(null);
        else if (event.getTarget() instanceof Spinner<?>)
            ((Spinner<?>)event.getTarget()).setStyle(null);
    }
}
