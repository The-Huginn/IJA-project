package com.ija.GUI.classDiagram;

import java.util.ArrayDeque;
import java.util.Deque;

import com.ija.Application.App;
import com.ija.GUI.GraphicInterface;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.ClassRelation;
import com.ija.backend.diagram.Relation;
import com.ija.backend.diagram.ClassRelation.ClassRelEnum;
import com.ija.backend.diagramObject.Element;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class cUMLRelation implements GraphicInterface {
    private final ClassRelation element;
    private final cUMLDiagram parent;

    private Line line;
    private Circle circle;
    private Label name;
    private Label start;
    private Label end;

    private static final String[] colors = {" blue;", " cyan;", " orange;", " black;", " pink;"};
    private int RADIUS = 5;

    Deque<UndoType> undo_stack = new ArrayDeque<>();
    
    private enum UndoType {
        others
    }

    public cUMLRelation(ClassRelation element, Pane parentPane, cUMLDiagram parent, double y, double x) {

        this.element = element;
        this.parent = parent;
        
        line = new Line();
        line.setStartX(x);
        line.setStartY(y);

        circle = new Circle(line.getEndY(), line.getEndX(), RADIUS);

        name = new Label();
        start = new Label();
        end = new Label();

        setListener(line);
        setListener(circle);
        setListener(name);
        setListener(start);
        setListener(end);

        addToPane(parentPane);

        updateContent();
    }

    private void setListener(Node node) {
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY)
                    App.setSelected(cUMLRelation.this);
            }
        });
    }

    public void drawEnd(double y, double x) {
        line.setEndY(y);
        line.setEndX(x);
        circle.setLayoutY(y);
        circle.setLayoutX(x);
        name.setLayoutY((line.getEndY() + line.getStartY()) / 2);
        name.setLayoutX((line.getEndX() + line.getStartX()) / 2);
        end.setLayoutY(y + RADIUS / 2);
        end.setLayoutX(x);
    }

    public void drawStart(double y, double x) {
        line.setStartY(y);
        line.setStartX(x);
        name.setLayoutY((line.getEndY() + line.getStartY()) / 2);
        name.setLayoutX((line.getEndX() + line.getStartX()) / 2);
        start.setLayoutX(x + RADIUS / 2);
        start.setLayoutY(y);
    }

    /**
     * @brief Should be called only to get coordinates of start and end
     * @return
     */
    public Line getLine() {
        return line;
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
        line.setStyle("-fx-stroke: #c3de49;");
        circle.setStyle("-fx-stroke: #c3de49; -fx-fill: #c3de49;");
        name.setStyle("-fx-text-fill: #c3de49;");
        start.setStyle("-fx-text-fill: #c3de49;");
        end.setStyle("-fx-text-fill: #c3de49;");
    }

    @Override
    public void unselect() {
        updateContent();
    }

    @Override
    public void updateContent() {
        line.setStyle("-fx-stroke:" + colors[((ClassRelation)getElement()).getType().ordinal()]);
        circle.setStyle("-fx-stroke:" + colors[((ClassRelation)getElement()).getType().ordinal()] + " -fx-fill: " + colors[((ClassRelation)getElement()).getType().ordinal()]);
        name.setStyle("-fx-text-fill:" + colors[((ClassRelation)getElement()).getType().ordinal()]);
        start.setStyle("-fx-text-fill:" + colors[((ClassRelation)getElement()).getType().ordinal()]);
        end.setStyle("-fx-text-fill:" + colors[((ClassRelation)getElement()).getType().ordinal()]);
        name.setText(element.getName());
        if (((ClassRelation)getElement()).getType() == ClassRelEnum.GENERALIZATION) {
            start.setText("");
            end.setText("");
        } else {
            start.setText(ClassRelation.getCardinality(element.getFirst().getValue()));
            end.setText(ClassRelation.getCardinality(element.getSecond().getValue()));
        }
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
    
    public void addToPane(Pane toPane) {
        toPane.getChildren().addAll(line, circle, name, start, end);
        line.toBack();
        name.toBack();
        circle.toFront();
        start.toFront();
        end.toFront();
    }

    @Override
    public UMLElement getUMLParent() {
        return parent;
    }

    @Override
    public void removeSelf(Pane fromPane) {
        removeFromPane(fromPane);
        ((cUMLDiagram)getUMLParent()).removeRelation(this);
    }

    public void removeFromPane(Pane fromPane) {
        fromPane.getChildren().removeAll(line, circle, name, start, end);
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public ElementType getType() {
        return ElementType.CLASS_RELATION;
    }

    @Override
    public void checkCorrect() {
        if (((Relation)getElement()).checkCorrect())
            return;

        line.setStyle("-fx-stroke: red;");
        circle.setStyle("-fx-stroke: red; -fx-fill: red;");
        name.setStyle("-fx-text-fill: red;");
        start.setStyle("-fx-text-fill: red;");
        end.setStyle("-fx-text-fill: red;");
    }
}
