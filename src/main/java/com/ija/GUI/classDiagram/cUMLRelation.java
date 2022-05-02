package com.ija.GUI.classDiagram;

import com.ija.Application.App;
import com.ija.GUI.GraphicInterface;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.ClassRelation;
import com.ija.backend.diagramObject.Element;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class cUMLRelation implements GraphicInterface {
    private final ClassRelation element;
    private final cUMLDiagram parent;

    private Line line;
    private Label name;
    private static final String[] colors = {" blue;", " cyan;", " orange;", " black;", " pink;"};

    public cUMLRelation(ClassRelation element, Pane parentPane, cUMLDiagram parent, double y, double x) {

        this.element = element;
        this.parent = parent;
        
        line = new Line();
        line.setStartX(x);
        line.setStartY(y);

        name = new Label(element.getName());

        line.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY)
                    App.setSelected(cUMLRelation.this);
            }
        });

        name.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY)
                    App.setSelected(cUMLRelation.this);
            }
        });

        parentPane.getChildren().add(line);
        parentPane.getChildren().addAll(name);        
        name.toBack();
        line.toBack();

        updateContent();
    }

    public void drawEnd(double y, double x) {
        line.setEndY(y);
        line.setEndX(x);
        name.setLayoutY((line.getEndY() + line.getStartY()) / 2);
        name.setLayoutX((line.getEndX() + line.getStartX()) / 2);
    }

    public void drawStart(double y, double x) {
        line.setStartY(y);
        line.setStartX(x);
        name.setLayoutY((line.getEndY() + line.getStartY()) / 2);
        name.setLayoutX((line.getEndX() + line.getStartX()) / 2);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof cUMLRelation))
            return false;

        cUMLRelation otherThis = (cUMLRelation)other;

        return otherThis.line == this.line &&
                otherThis.getElement() == this.getElement();
    }

    @Override
    public void select() {
        line.setStyle("-fx-stroke: yellow");
        name.setStyle("-fx-text-fill: yellow");
    }

    @Override
    public void unselect() {
        updateContent();
    }

    @Override
    public void updateContent() {
        // TODO
        line.setStyle("-fx-stroke:" + colors[((ClassRelation)getElement()).getType().ordinal()]);
        name.setStyle("-fx-text-fill:" + colors[((ClassRelation)getElement()).getType().ordinal()]);
    }

    @Override
    public void addUndo() {}

    @Override
    public void undo() {
        element.undo();
        updateContent();
    }
    
    public void deleteMe() {
        
    }

    @Override
    public UMLElement getUMLParent() {
        return parent;
    }

    @Override
    public void removeSelf(Pane fromPane) {
        fromPane.getChildren().remove(line);
        fromPane.getChildren().remove(name);
        
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public ElementType getType() {
        return ElementType.CLASS_RELATION;
    }
}
