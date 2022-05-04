package com.ija.GUI;

import com.ija.backend.undoInterface;
import com.ija.backend.diagramObject.Element;

import javafx.scene.layout.Pane;

public interface GraphicInterface extends undoInterface {

    public enum ElementType {
        VARIABLE,
        METHOD,
        CLASS,
        INTERFACE,
        CLASS_DIAGRAM,
        SEQ_DIAGRAM,
        CLASS_RELATION,
        SEQ_RELATION,
        INSTANCE
    }

    /**
     * @return parent of this object
     */
    public UMLElement getUMLParent();

    /**
     * @brief Class gets notified it got selected
     */
    public void select();

    /**
     * @brief Class gets notified it got unselected
     */
    public void unselect();

    /**
     * @brief can get notified to update content
     */
    public void updateContent();

    /**
     * @brief to remove itself from the Pane
     */
    public void removeSelf(Pane fromPane);

    /**
     * @return Corresponding Element
     */
    public Element getElement();

    /**
     * @brief Call this function if method was called upon Element outside of UMLElement
     */
    public void addUndo();

    /**
     * @return
     */
    public ElementType getType();

    /**
     * @brief Checks if current Element or subElements are correct
     */
    public void checkCorrect();
}
