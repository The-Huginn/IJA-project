/**
 * @file ClassInterfaceTableController.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for Class and Interface add tab
 */
package com.ija.GUI.classDiagram;

import com.ija.Application.App;
import com.ija.backend.diagramObject.Attribute;
import com.ija.backend.diagramObject.Method;
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
                    if (! ((UMLObject)App.getElement()).addVariable(new Attribute(AttributeName.getText(), (UMLObject)App.getElement())))
                        AttributeName.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    else {
                        // TODO add new Variable
                        AttributeName.setStyle(null);
                        App.addUndo();
                    }
                }
                break;

            case "Method":
            if (App.getElement() instanceof UMLObject) {
                if (! ((UMLObject)App.getElement()).addMethod(new Method(AttributeName.getText(), (UMLObject)App.getElement())))
                    AttributeName.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                else {
                    // TODO add new Variable
                    AttributeName.setStyle(null);
                    App.addUndo();
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
