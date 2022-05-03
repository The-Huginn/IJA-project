package com.ija.GUI.seqDiagram;

import com.ija.GUI.GraphicInterface;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagramObject.Element;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class UMLInstance extends Label implements GraphicInterface {
    private Element element;
    private int instanceNumber;
    
    public UMLInstance(Element element, UMLElement parent, int number) {
        super(element.getName() + " : " + number);

        this.element = element;
        this.instanceNumber = number;
        setPrefWidth(200);
        setPadding(new Insets(10, 10, 10, 10));
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-border-color: grey; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: #99bbf2;");
    }

    public Integer getInstanceNumber() {
        return instanceNumber;
    }

    @Override
    public void select() {
        setStyle("-fx-border-color: yellow; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: #7796c9;");
    }

    @Override
    public void unselect() {
        setStyle("-fx-border-color: grey; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: #99bbf2;");
        
    }

    @Override
    public void updateContent() {}

    @Override
    public void addUndo() {}

    @Override
    public void undo() {}

    @Override
    public UMLElement getUMLParent() {
        return null;
    }

    @Override
    public void removeSelf(Pane fromPane) {}

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public ElementType getType() {
        // TODO Auto-generated method stub
        return ElementType.INSTANCE;
    }
    
}
