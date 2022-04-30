package com.ija.GUI.classDiagram;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ija.Application.App;
import com.ija.GUI.MainWindowController;
import com.ija.backend.diagramObject.Attribute;
import com.ija.backend.diagramObject.Type;
import com.ija.backend.diagramObject.Attribute.Visibility;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditTableController {
    @FXML TextField newNameField;
    @FXML ComboBox<String> typeComboBox;
    @FXML ComboBox<String> visiComboBox;
    @FXML TextField newParamsField;

    public EditTableController() {

        if (App.containsMethod("setType")) {
            typeComboBox.setItems(FXCollections.observableArrayList(Type.getAllTypes()));
            try {
                Type current = (Type)App.getElement().getClass().getMethod("getType").invoke(App.getElement());
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
    protected void setNewType(Event event) {
        if (!App.containsMethod("setType"))
            return;
            
        try {
            App.getElement().getClass().getMethod("setType").invoke(App.getElement(), Type.getType(typeComboBox.getValue()));
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
            App.getElement().getClass().getMethod("setVisibility").invoke(App.getElement(), Visibility.valueOf(visiComboBox.getValue()));
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
        try {
            if (! (boolean)App.getElement().getClass().getMethod("setParameters").invoke(App.getElement(), (Object[])newParams))
                newParamsField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            else {
                // TODO update params in GUI
                newParamsField.setStyle(null);
                App.addUndo();
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }
}
