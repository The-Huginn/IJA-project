package com.ija.GUI;

import java.lang.reflect.InvocationTargetException;

import com.ija.Application.App;
import com.ija.backend.diagramObject.Type;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditDiagramTableController {
    @FXML TextField newNameField;
    @FXML ComboBox<String> typeComboBox;

    public EditDiagramTableController() {

        if (App.containsMethod("setType")) {
            Type current;
            typeComboBox.setItems(FXCollections.observableArrayList(Type.getAllTypes()));
            try {
                current = (Type)App.getElement().getClass().getMethod("getType").invoke(App.getElement());
                typeComboBox.setValue(current.getName());
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }

        if (App.containsMethod("setVisibility")) {

        }
    }
    
    @FXML
    protected void setNewName(Event event) {
        if (!App.getElement().setName(newNameField.getText()))
            newNameField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        else {
            newNameField.setStyle(null);
            ((MainWindowController)App.getLoader().getController()).diagramName.setText(App.getElement().getName());
            App.addUndo();
        }
    }

    @FXML
    protected void setNewType(Event event) {
        if (!App.containsMethod("setType"))
            return;
            
        try {
            App.getElement().getClass().getMethod("setType").invoke(Type.getType(typeComboBox.getValue()));
            App.addUndo();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }
}
