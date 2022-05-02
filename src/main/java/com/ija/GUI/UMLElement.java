/**
 * @file UMLElement.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains basic selectable element for GUI, which holds one corresponding backend Element.
 */
package com.ija.GUI;

import com.ija.backend.diagramObject.Element;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class UMLElement extends VBox implements GraphicInterface {
    private Element entity;
    private final ElementType type;
    private final UMLElement parent;

    public UMLElement(Element element, UMLElement parent, ElementType type) {
        super();

        entity = element;
        this.type = type;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof UMLElement))
            return false;

        UMLElement objThis = (UMLElement)obj;

        return objThis.entity == this.entity &&
                objThis.type == this.type &&
                objThis.parent == this.parent;
    }
    
    public UMLElement getUMLParent() {
        return parent;
    }

    @Override
    public void removeSelf(Pane fromPane) {
        fromPane.getChildren().remove(this);
    }

    @Override
    public Element getElement() {
        return entity;
    }

    @Override
    public ElementType getType() {
        return type;
    }

    @Override
    public void undo() {
        entity.undo();
    }
}
