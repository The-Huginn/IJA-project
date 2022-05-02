/**
 * @file EditTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file is used as controller for all Edit Component panes
 */
package com.ija.GUI.classDiagram;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.ija.Application.App;
import com.ija.backend.diagram.ClassRelation;
import com.ija.backend.diagram.ClassRelation.ClassRelEnum;
import com.ija.backend.diagramObject.Attribute;
import com.ija.backend.diagramObject.Method;
import com.ija.backend.diagramObject.Type;
import com.ija.backend.diagramObject.UMLObject;
import com.ija.backend.diagramObject.Attribute.Visibility;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class EditTableController implements Initializable {
    @FXML TextField newNameField;
    @FXML ComboBox<String> typeComboBox;
    @FXML ComboBox<String> visiComboBox;
    @FXML TextField newParamsField;
    @FXML ComboBox<String> startComboBox;
    @FXML ComboBox<String> endComboBox;
    @FXML ComboBox<String> relationComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (App.containsMethod("setType")) {
            typeComboBox.setItems(FXCollections.observableArrayList(Type.getAllTypes()));
            try {
                Type current = (Type)App.getElement().getClass().getMethod("getType").invoke(App.getElement());
                if (current != null)
                    typeComboBox.setValue(current.getName());
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }

        if (App.containsMethod("setVisibility")) {
            List<String> visibilities = Arrays.asList(Attribute.Visibility.values())
                                    .stream()
                                    .map(f -> f.toString())
                                    .collect(Collectors.toList());
            visiComboBox.setItems(FXCollections.observableArrayList(visibilities));
            try {
                Visibility current = (Attribute.Visibility)App.getElement().getClass().getMethod("getVisibility").invoke(App.getElement());
                visiComboBox.setValue(current.toString());
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }

        if (App.containsMethod("setParameters")) {
            try {

                @SuppressWarnings("unchecked")
                List<Type> types = (List<Type>)App.getElement().getClass().getMethod("getParameters").invoke(App.getElement());
                List<String> params = types.stream()
                                        .map(f -> f.getName())
                                        .collect(Collectors.toList());
                newParamsField.setText(String.join(",", params));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }

        if (App.containsMethod("setFirst")) {
            startComboBox.setItems(FXCollections.observableArrayList(ClassRelation.getCardinalities()));
            endComboBox.setItems(FXCollections.observableArrayList(ClassRelation.getCardinalities()));
            try {
                @SuppressWarnings("unchecked")
                Pair<UMLObject, Integer> rel = (Pair<UMLObject, Integer>)App.getElement().getClass().getMethod("getFirst").invoke(App.getElement());
                startComboBox.setValue(ClassRelation.getCardinality(rel.getValue()));

                @SuppressWarnings("unchecked")
                Pair<UMLObject, Integer> rel2 = (Pair<UMLObject, Integer>)App.getElement().getClass().getMethod("getSecond").invoke(App.getElement());
                endComboBox.setValue(ClassRelation.getCardinality(rel2.getValue()));

                List<String> relations = Arrays.asList(ClassRelation.ClassRelEnum.values())
                                        .stream()
                                        .map(f -> f.toString())
                                        .collect(Collectors.toList());
                relationComboBox.setItems(FXCollections.observableArrayList(relations));
                String current = ((ClassRelEnum)App.getElement().getClass().getMethod("getType").invoke(App.getElement())).toString();
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
            App.getSelected().updateContent();
            App.addUndo();
        }
    }

    @FXML
    protected void setNewType(Event event) {
        if (!App.containsMethod("setType"))
            return;
            
        try {
            App.getElement().getClass().getMethod("setType", Type.class).invoke(App.getElement(), Type.getType(typeComboBox.getValue()));
            App.getSelected().updateContent();
            App.addUndo();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void setNewVisibility(Event event) {
        if (!App.containsMethod("setVisibility"))
            return;

        try {
            App.getElement().getClass().getMethod("setVisibility", Attribute.Visibility.class).invoke(App.getElement(), Visibility.valueOf(visiComboBox.getValue()));
            App.getSelected().updateContent();
            App.addUndo();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void setNewParams(Event event) {
        if (!App.containsMethod("setParameters"))
            return;

        // Replace whitespaces and split by comma
        String[] newParams = newParamsField.getText().replaceAll("\\s+", "").split(",");
        Method method = (Method)App.getElement();
        try {
            if (! (boolean)method.setParameters(newParams))
                newParamsField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            else {
                // TODO update params in GUI
                newParamsField.setStyle(null);
                App.getSelected().updateContent();
                App.addUndo();
            }
        } catch (IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void updateStart(Event event) {
        if (!App.containsMethod("setFirst"))
            return;

        try {
            @SuppressWarnings("unchecked")
            Pair<UMLObject, Integer> rel = (Pair<UMLObject, Integer>) App.getElement().getClass().getMethod("getFirst").invoke(App.getElement());
            if (!((ClassRelation) App.getElement()).setFirst(rel.getKey(), ClassRelation.getCardinality(startComboBox.getValue())))
                startComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); // TODO test if this works
            else {
                // TODO maybe
                startComboBox.setStyle(null);
                App.getSelected().updateContent();
                App.addUndo();
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void updateEnd(Event event) {
        if (!App.containsMethod("setSecond"))
            return;

        try {
            @SuppressWarnings("unchecked")
            Pair<UMLObject, Integer> rel = (Pair<UMLObject, Integer>) App.getElement().getClass().getMethod("getSecond").invoke(App.getElement());
            if (!((ClassRelation) App.getElement()).setSecond(rel.getKey(), ClassRelation.getCardinality(endComboBox.getValue())))
                endComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); // TODO test if this works
            else {
                // TODO maybe
                endComboBox.setStyle(null);
                App.getSelected().updateContent();
                App.addUndo();
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void updateRelation(Event event) {
        if (!App.containsMethod("setType"))
            return;

        if (!((ClassRelation) App.getElement()).setType(ClassRelEnum.valueOf(relationComboBox.getValue())))
            relationComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); // TODO test if this works
        else {
            // TODO maybe
            relationComboBox.setStyle(null);
            App.getSelected().updateContent();
            App.addUndo();
        }
    }

    @FXML
    protected void deleteAttribute(Event event) {
        if (!(App.getSelected() instanceof UMLAttribute))
            return;

        ((UMLAttribute)App.getSelected()).deleteMe();
    }
}
