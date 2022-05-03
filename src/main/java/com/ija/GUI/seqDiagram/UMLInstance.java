package com.ija.GUI.seqDiagram;

import com.ija.Application.App;
import com.ija.GUI.GraphicInterface;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagramObject.Element;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class UMLInstance implements GraphicInterface {
    private Element element;
    private Label label;
    private Line line;
    private int instanceNumber;
    public static int WIDTH = 200;
    private static int HEIGHT = 50;
    private final sUMLDiagram parent;
    
    public UMLInstance(Element element, sUMLDiagram parent, Pane parentPane, int number, int x) {
        label = new Label(element.getName() + " : " + number);
        line = new Line();

        this.parent = parent;
        this.element = element;
        this.instanceNumber = number;
        
        line.setStartX(x);
        line.setStartY(HEIGHT);
        line.setEndX(x);
        line.setEndY(parentPane.getPrefHeight());
        line.toBack();
        
        parent.getHeader().getChildren().add(label);
        parentPane.getChildren().add(line);

        label.setPrefSize(WIDTH, HEIGHT);
        label.setPadding(new Insets(10, 10, 10, 10));
        label.setAlignment(Pos.TOP_CENTER);
        label.setStyle("-fx-border-color: grey; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: #99bbf2; -fx-opacity: 1;");
        label.toFront();

        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.setSelected(UMLInstance.this);
            }
        });
    }

    public Integer getInstanceNumber() {
        return instanceNumber;
    }

    @Override
    public void select() {
        label.setStyle("-fx-border-color: yellow; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: #7796c9;");
    }

    @Override
    public void unselect() {
        label.setStyle("-fx-border-color: grey; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: #99bbf2;");
        
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
    public void removeSelf(Pane fromPane) {
        parent.removeInstance(this);
    }

    public void removeFromPane() {
        parent.getHeader().getChildren().remove(label);
        App.getCurrentPane().getChildren().remove(line);
    }

    public void addToPane(int index) {
        parent.getHeader().getChildren().add(index, label);
        App.getCurrentPane().getChildren().add(line);
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public ElementType getType() {
        return ElementType.INSTANCE;
    }
    
}
