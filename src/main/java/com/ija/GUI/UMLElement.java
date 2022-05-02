/**
 * @file UMLElement.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains basic selectable element for GUI, which holds one corresponding backend Element.
 */
package com.ija.GUI;

import com.ija.backend.undoInterface;
import com.ija.backend.diagramObject.Element;

import javafx.scene.layout.VBox;

public abstract class UMLElement extends VBox implements undoInterface {
    private Element entity;
    private final ElementType type;
    private final UMLElement parent;

    public enum ElementType {
        VARIABLE,
        METHOD,
        CLASS,
        INTERFACE,
        CLASS_DIAGRAM,
        SEQ_DIAGRAM,
        CLASS_RELATION,
        SEQ_RELATION
    }

    public UMLElement(Element element, UMLElement parent, ElementType type) {
        super();

        entity = element;
        this.type = type;
        this.parent = parent;
    }

    public UMLElement getUMLParent() {
        return parent;
    }

    /**
     * @brief Class gets notified it got selected
     */
    public abstract void select();

    /**
     * @brief Class gets notified it got unselected
     */
    public abstract void unselect();

    /**
     * @brief can get notified to update content
     */
    public abstract void updateContent();

    /**
     * @return
     */
    public Element getElement() {
        return entity;
    }

    /**
     * @return
     */
    public ElementType getType() {
        return type;
    }

    /**
     * @brief Call this function if method was called upon Element outside of UMLElement
     */
    public abstract void addUndo();

    /**
     * @brief Calls undo upon it's @see entity
     */
    public void undo() {
        entity.undo();
    }
}
