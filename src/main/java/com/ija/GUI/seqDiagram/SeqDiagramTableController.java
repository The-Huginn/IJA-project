/**
 * @file ClassDiagramTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for SeqDiagram edit tab
 */
package com.ija.GUI.seqDiagram;

import java.util.List;
import java.util.stream.Collectors;

import com.ija.Application.App;
import com.ija.GUI.MainWindowController;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagramObject.UMLClass;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;

public class SeqDiagramTableController {
    @FXML Spinner<Integer> instanceNumber;
    @FXML ComboBox<String> diagramComboBox;
    @FXML ComboBox<String> classesComboBox;

    @FXML
    protected void addInstance(Event event) {
        if (classesComboBox.getValue() == null) {
            classesComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return;
        }

        UMLClass instance = App.getClassDiagram().getClasses().stream()
                                .filter(f -> f.getName().equals(classesComboBox.getValue()))
                                .findAny()
                                .orElse(null);
        
        SeqDiagram diagram = (SeqDiagram)App.getCurrentDiagram().getElement();
        if (!diagram.addInstance(instance, instanceNumber.getValue())) {
            classesComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            instanceNumber.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            classesComboBox.setStyle(null);
            instanceNumber.setStyle(null);
            ((sUMLDiagram)App.getCurrentDiagram()).addNewInstance(instance, instanceNumber.getValue());
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
        if (diagramComboBox.getValue() != null) {
            ((MainWindowController)App.getLoader().getController()).switchSeqDiagram(diagramComboBox.getValue());
        }
    }

    @FXML
    protected void clearStyle(Event event) {
        if (event.getTarget() instanceof Spinner<?>)
            ((Spinner<?>)event.getTarget()).setStyle(null);
    }

    @FXML
    protected void updateClasses(Event event) {
        List<String> classes = App.getClassDiagram().getClasses().stream()
                                    .map(f -> f.getName())
                                    .collect(Collectors.toList());
            classesComboBox.setItems(FXCollections.observableArrayList(classes));
    }
}
