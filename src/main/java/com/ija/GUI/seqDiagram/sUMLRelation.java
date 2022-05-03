package com.ija.GUI.seqDiagram;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import com.ija.Application.App;
import com.ija.GUI.GraphicInterface;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.SeqRelation;
import com.ija.backend.diagramObject.Element;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class sUMLRelation implements GraphicInterface {
    private final SeqRelation element;
    private final sUMLDiagram parent;

    private Line line;
    private Label name;
    private Label note;
    private static final String[] colors = {" blue;", " cyan;", " orange;", " black;", " pink;"};

    Deque<UndoType> undo_stack = new ArrayDeque<>();
    // TODO add moving of the line
    private enum UndoType {
        others
    }

    public sUMLRelation(SeqRelation element, Pane parentPane, sUMLDiagram parent, double y) {

        this.element = element;
        this.parent = parent;
        
        line = new Line();
        line.setStartY(y);
        line.setEndY(y);

        name = new Label();
        note = new Label();

        line.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY)
                    App.setSelected(sUMLRelation.this);
            }
        });

        name.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY)
                    App.setSelected(sUMLRelation.this);
            }
        });

        note.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY)
                    App.setSelected(sUMLRelation.this);
            }
        });

        addToPane(parentPane);

        updatePosition();
    }

    /**
     * @note calls @see updateContent
     */
    public void updatePosition() {
        for (Node node : parent.getHeader()) {
            if (!(node instanceof UMLInstance))
                continue;

            UMLInstance instance = (UMLInstance)node;

            // starts here
            if (element.getFirst().getKey() == instance.getElement() && element.getFirst().getValue() == instance.getInstanceNumber()) {
                int index = parent.getHeader().indexOf(node);
                int x = index * (sUMLDiagram.SPACING + UMLInstance.WIDTH) + UMLInstance.WIDTH / 2;
                line.setStartX(x);
            }
            
            if (element.getSecond().getKey() == instance.getElement() && element.getSecond().getValue() == instance.getInstanceNumber()) {
                int index = parent.getHeader().indexOf(node);
                int x = index * (sUMLDiagram.SPACING + UMLInstance.WIDTH) + UMLInstance.WIDTH / 2;
                line.setEndX(x);
            }
        }

        updateContent();
    }

    public void drawEnd(double y) {
        line.setEndY(y);
        name.setLayoutY((line.getEndY() + line.getStartY()) / 2 - 20);
        name.setLayoutX((line.getEndX() + line.getStartX()) / 2);
        note.setLayoutY((line.getEndY() + line.getStartY()) / 2);
        note.setLayoutX((line.getEndX() + line.getStartX()) / 2);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof sUMLRelation))
            return false;

        sUMLRelation otherThis = (sUMLRelation)other;

        return otherThis.line == this.line &&
                otherThis.getElement() == this.getElement() ;
    }

    public void removeFromPane(Pane fromPane) {
        fromPane.getChildren().removeAll(Arrays.asList(line, name, note));
    }

    public void addToPane(Pane toPane) {
        toPane.getChildren().addAll(Arrays.asList(line, name, note));
        
        name.toBack();
        line.toBack();
        note.toBack();
    }

    @Override
    public UMLElement getUMLParent() {
        return parent;
    }

    @Override
    public void select() {
        line.setStyle("-fx-stroke: yellow");
        name.setStyle("-fx-text-fill: yellow");
        note.setStyle("-fx-text-fill: yellow");
    }

    @Override
    public void unselect() {
        updateContent();
    }

    @Override
    public void updateContent() {
        name.setText(element.getName() + " - " + element.getMethod());
        note.setText(element.getNote());
        line.setStyle("-fx-stroke:" + colors[((SeqRelation)getElement()).getType().ordinal()]);
        name.setStyle("-fx-text-fill:" + colors[((SeqRelation)getElement()).getType().ordinal()]);
        note.setStyle("-fx-text-fill:" + colors[((SeqRelation)getElement()).getType().ordinal()]);
    }

    @Override
    public void removeSelf(Pane fromPane) {
        removeFromPane(fromPane);
        ((sUMLDiagram)getUMLParent()).removeRelation(this);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public void addUndo() {
        undo_stack.addFirst(UndoType.others);
    }

    @Override
    public void undo() {

        if (undo_stack.isEmpty())
            return;

        undo_stack.pop();

        element.undo();
        updateContent();
    }

    @Override
    public ElementType getType() {
        return ElementType.SEQ_RELATION;
    }
    
}
