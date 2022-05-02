/**
 * @file ClassInterfaceTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for Class and Interface add tab
 */
package com.ija.GUI.classDiagram;

import com.ija.Application.App;
import com.ija.backend.diagramObject.UMLObject;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ClassInterfaceTableController {
    @FXML TextField AttributeName;
    @FXML ToggleGroup newAttribute;

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
    protected void clearStyle(Event event) {
        ((TextField)event.getTarget()).setStyle(null);
    }
}
