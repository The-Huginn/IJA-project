package com.ija.GUI.classDiagram;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import com.ija.Application.App;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagramObject.UMLObject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class UMLEntity extends UMLElement {
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<Delta> undo_moves = new ArrayDeque<>();

    private Label name;
    private ListView<String> variables;
    private ListView<String> methods;

    private enum UndoType {
        move,
        changeName,
        others
    }

    private void updateContent() {
        name.setText(getElement().getName());
        // TODO repaint variables and methods
    }

    public UMLEntity(UMLObject entity, Pane parent, int y, int x) {
        super(entity, ElementType.CLASS);

        name = new Label();
        name.setPadding(new Insets(10, 10, 10, 10));
        name.setPrefHeight(30);

        variables = new ListView<String>();
        variables.setPadding(new Insets(0, 10, 10, 10));
        variables.setPrefHeight(30);
        variables.setPrefWidth(150);
        
        methods = new ListView<String>();
        methods.setPadding(new Insets(0, 10, 10, 10));
        methods.setPrefHeight(30);
        methods.setPrefWidth(150);

        updateContent();
        
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-border-color: grey; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed;");
        
        getChildren().addAll(Arrays.asList(name, new Separator(), variables, new Separator(), methods));
        setLayoutY(y);
        setLayoutX(x);

        parent.getChildren().add(this);

        // Inspired by https://stackoverflow.com/a/10689478
        final Delta dragDelta = new Delta();
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = getLayoutX() - mouseEvent.getSceneX();
                dragDelta.y = getLayoutY() - mouseEvent.getSceneY();
                setCursor(Cursor.MOVE);
                undo_moves.addFirst(new Delta(getLayoutX(), getLayoutY()));
                undo_stack.addFirst(UndoType.move);
                App.setSelected(UMLEntity.this);
                App.addClearUndo();
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCursor(Cursor.HAND);
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCursor(Cursor.HAND);
            }
        });
    }

    @Override
    public void select() {
        setStyle("-fx-border-color: yellow; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed;");
    }

    @Override
    public void unselect() {
        setStyle("-fx-border-color: grey; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed;");
    }

    @Override
    public void addUndo() {
        updateContent();
        undo_stack.addFirst(UndoType.others);
    }

    @Override
    public void undo() {
        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.move) {
            Delta delta = undo_moves.pop();
            setLayoutX(delta.x);
            setLayoutY(delta.y);
        } else if (type == UndoType.others) {
            super.undo();
            updateContent();
        }
    }
}

class Delta {
    public double x, y;
    public Delta() {}
    public Delta(double x, double y) {this.x = x; this.y = y;}
}