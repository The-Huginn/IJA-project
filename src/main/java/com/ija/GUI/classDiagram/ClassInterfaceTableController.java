/**
 * @file ClassInterfaceTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for Class and Interface add tab
 */
package com.ija.GUI.classDiagram;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.ija.Application.App;
import com.ija.backend.diagram.ClassRelation;
import com.ija.backend.diagram.ClassRelation.ClassRelEnum;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;
import com.ija.backend.diagramObject.UMLObject;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ClassInterfaceTableController implements Initializable {
    @FXML TextField AttributeName;
    @FXML ToggleGroup newAttribute;
    @FXML TextField relationName;
    @FXML ComboBox<String> startComboBox;
    @FXML ComboBox<String> endComboBox;
    @FXML ComboBox<String> typeComboBox;
    @FXML ComboBox<String> relationComboBox;
    @FXML ToggleGroup newRelation;
    @FXML Button addRelation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startComboBox.setItems(FXCollections.observableArrayList(ClassRelation.getCardinalities()));
        endComboBox.setItems(FXCollections.observableArrayList(ClassRelation.getCardinalities()));
        List<String> values = Arrays.asList(ClassRelation.ClassRelEnum.values()).stream()
                                .map(f -> f.toString())
                                .collect(Collectors.toList());
        typeComboBox.setItems(FXCollections.observableArrayList(values));

        startComboBox.setValue(ClassRelation.getCardinalities().get(0));
        endComboBox.setValue(ClassRelation.getCardinalities().get(0));
        typeComboBox.setValue(values.get(0));
    }

    @FXML
    protected void addAttribute(Event event) {

        switch (newAttribute.getSelectedToggle().getUserData().toString()) {
            case "Variable":
                if (App.getElement() instanceof UMLObject) {
                    if (! ((UMLEntity)App.getSelected()).addVariable(AttributeName.getText()))
                        AttributeName.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    else {
                        AttributeName.setStyle(null);
                    }
                }
                break;

            case "Method":
            if (App.getElement() instanceof UMLObject) {
                if (! ((UMLEntity)App.getSelected()).addMethod(AttributeName.getText()))
                    AttributeName.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                else {
                    AttributeName.setStyle(null);
                }
            }
            break;
        }
    }

    @FXML
    protected void addRelation(Event event) {
        int start = ClassRelation.getCardinality(startComboBox.getValue());
        int end = ClassRelation.getCardinality(endComboBox.getValue());
        ClassRelEnum type = ClassRelEnum.valueOf(typeComboBox.getValue());
        UMLObject selected = null;
        if (newRelation.getSelectedToggle().getUserData().equals("Class")) {
            for (UMLClass var : App.getClassDiagram().getClasses()) {
                if (var.getName().equals(relationComboBox.getValue()))
                    selected = var;
            }
        } else {
            for (UMLInterface var : App.getClassDiagram().getInterfaces()) {
                if (var.getName().equals(relationComboBox.getValue()))
                    selected = var;
            }
        }

        ClassRelation relation = new ClassRelation(relationName.getText(),
                                                    App.getClassDiagram(),
                                                    (UMLObject)App.getSelected().getElement(),
                                                    start,
                                                    selected,
                                                    end,
                                                    type);

        if (!App.getClassDiagram().addRelation(relation)) {
            addRelation.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            double y = ((UMLEntity)App.getSelected()).getLayoutY() + ((UMLEntity)App.getSelected()).getHeight() / 2;
            double x = ((UMLEntity)App.getSelected()).getLayoutX() + ((UMLEntity)App.getSelected()).getWidth() / 2;
            
            cUMLRelation newRelation = new cUMLRelation(relation, App.getCurrentPane(), (cUMLDiagram)App.getCurrentDiagram(), y, x);
            ((cUMLDiagram)App.getCurrentDiagram()).addNewRelation(newRelation);

            for (Node node : App.getCurrentPane().getChildren()) {
                if (!(node instanceof UMLEntity))
                    continue;

                UMLEntity entity = (UMLEntity)node;
                if (selected == entity.getElement())
                    newRelation.drawEnd(entity.getLayoutY() + entity.getHeight() / 2, entity.getLayoutX() + entity.getWidth() / 2);
            }
        }
    }

    @FXML
    protected void updateRelation(Event event) {
        if (newRelation.getSelectedToggle().getUserData().equals("Class")) {
            List<String> classes = App.getClassDiagram().getClasses().stream()
                                    .map(f -> f.getName())
                                    .collect(Collectors.toList());
            relationComboBox.setItems(FXCollections.observableArrayList(classes));
        } else {
            List<String> interfaces = App.getClassDiagram().getInterfaces().stream()
                                    .map(f -> f.getName())
                                    .collect(Collectors.toList());
            relationComboBox.setItems(FXCollections.observableArrayList(interfaces));
        }
    }

    @FXML
    protected void clearStyle(Event event) {
        ((TextField)event.getTarget()).setStyle(null);
    }
}
