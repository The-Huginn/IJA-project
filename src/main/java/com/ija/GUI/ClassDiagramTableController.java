/**
 * @file ClassDiagramTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for ClassDiagram edit tab
 */
package com.ija.GUI;

import com.ija.Application.App;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagramObject.Type;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;

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

    @FXML
    protected void fillTypes(Event event) {

        String original = typeComboBox.getValue();

        if (App.getDiagram() != null) {
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
        Type.removeType(typeComboBox.getValue());
        fillTypes(event);
    }

    @FXML
    protected void addType(Event event) {
        if (!Type.addType(newType.getText()))
            newType.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        else {
            newType.setText(null);
        }
    }

    @FXML
    protected void addSequence(Event event) {
        if (!App.getDiagram().addDiagram(new SeqDiagram(newSequence.getText(), App.getDiagram())))
            newSequence.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        else {
            // TODO set Sequence diagram to window
        }
    }

    @FXML
    protected void addUMLObject(Event event) {

        switch (newUMLObject.getSelectedToggle().getUserData().toString()) {
            case "Class":
                if (!App.getDiagram().addClass(new UMLClass(UMLObjectName.getText(), App.getDiagram())))
                    UMLObjectName.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                else {
                    // TODO add new UMLObject
                }
                break;

            case "Interface":
                if (!App.getDiagram().addInterface(new UMLInterface(UMLObjectName.getText(), App.getDiagram())))
                    UMLObjectName.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                else {
                    // TODO add new UMLObject
                }
                break;
        }
    }

    @FXML
    protected void clearStyle(Event event) {
        ((TextField)event.getTarget()).setStyle(null);
    }
}
