/**
 * @file ClassDiagramTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for ClassDiagram edit tab
 */
package com.ija.GUI.classDiagram;

import java.util.List;
import java.util.stream.Collectors;

import com.ija.Application.App;
import com.ija.GUI.MainWindowController;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagramObject.Type;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ClassDiagramTableController {
    @FXML ComboBox<String> typeComboBox;
    @FXML Button removeButton;
    @FXML TextField newType;
    @FXML TextField newSequence;
    @FXML TextField UMLObjectName;
    @FXML ToggleGroup newUMLObject;
    @FXML ComboBox<String> diagramComboBox;

    @FXML
    protected void fillTypes(Event event) {

        String original = typeComboBox.getValue();

        if (App.getClassDiagram() != null) {
            typeComboBox.setItems(FXCollections.observableArrayList(Type.getAllTypes()));

            if (Type.getAllTypes().contains(original))
                typeComboBox.setValue(original);
        } else {
            removeButton.setDisable(true);
            typeComboBox.getItems().clear();
        }
    }

    @FXML
    protected void selectType(Event event) {
        
        Type selected = Type.getType(typeComboBox.getValue() == null ? "" : typeComboBox.getValue());

        if (selected == null)
            return;

        if (!selected.isUserDefined())
            removeButton.setDisable(true);
        else
            removeButton.setDisable(false);
    }

    @FXML
    protected void removeType(Event event) {
        if (!Type.removeType(typeComboBox.getValue())) {
            removeButton.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            removeButton.setStyle(null);
            fillTypes(event);
        }
    }

    @FXML
    protected void addType(Event event) {
        if (!Type.addType(newType.getText()))
            newType.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        else {
            newType.setText(null);
            newType.setStyle(null);
        }
    }

    @FXML
    protected void addSequence(Event event) {
        if (newSequence.getText() == null) {
            newSequence.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }

        if (!App.getClassDiagram().addDiagram(new SeqDiagram(newSequence.getText(), App.getClassDiagram())))
            newSequence.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        else {
            newSequence.setStyle(null);
            ((MainWindowController)App.getLoader().getController()).addSequence(newSequence.getText());
        }
    }

    @FXML
    protected void addUMLObject(Event event) {

        switch (newUMLObject.getSelectedToggle().getUserData().toString()) {
            case "Class":
                if (!((cUMLDiagram)App.getCurrentDiagram()).addClass(UMLObjectName.getText()))
                    UMLObjectName.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                else {
                    UMLObjectName.setStyle(null);
                }
                break;

            case "Interface":
                if (!((cUMLDiagram)App.getCurrentDiagram()).addInterface(UMLObjectName.getText()))
                    UMLObjectName.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                else {
                    UMLObjectName.setStyle(null);
                }
                break;
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
        if (diagramComboBox.getValue()!= null) {
            ((MainWindowController) App.getLoader().getController()).switchSeqDiagram(diagramComboBox.getValue());
        }
    }

    @FXML
    protected void clearStyle(Event event) {
        ((TextField)event.getTarget()).setStyle(null);
    }
}
