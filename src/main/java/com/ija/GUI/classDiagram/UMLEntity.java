package com.ija.GUI.classDiagram;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import com.ija.Application.App;
import com.ija.GUI.UMLElement;
import com.ija.backend.diagramObject.Attribute;
import com.ija.backend.diagramObject.Method;
import com.ija.backend.diagramObject.UMLObject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class UMLEntity extends UMLElement {
    private Label name;
    private ListView<Node> variables;
    private ListView<Node> methods;
    
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<Delta> undo_moves = new ArrayDeque<>();
    private Deque<Pair<Integer, UMLAttribute>> undo_removes = new ArrayDeque<>();
    
    private enum UndoType {
        move,
        changeName,
        addVariable,
        addMethod,
        removeVariable,
        removeMethod,
        others
    }

    public UMLEntity(UMLObject entity, Pane parentPane, cUMLDiagram parent, int y, int x) {
        super(entity, parent, ElementType.CLASS);

        name = new Label();
        name.setPadding(new Insets(10, 10, 10, 10));
        name.setPrefHeight(30);

        variables = new ListView<>();
        variables.setPadding(new Insets(0, 10, 10, 10));
        variables.setPrefWidth(300);
        variables.setPrefHeight(120);
        variables.setStyle(".list-cell:filled:selected {-fx-background-color: -fx-background ;-fx-background-insets: 0 ;}");
        for (Attribute var : entity.getVariables()) {
            variables.getItems().add(new UMLAttribute(var, this, ElementType.VARIABLE));
        }
        variables.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                if (variables.getSelectionModel().getSelectedItem() != null)
                    App.setSelected((UMLAttribute)variables.getSelectionModel().getSelectedItem());                
            }
        });
        
        methods = new ListView<>();
        methods.setPadding(new Insets(0, 10, 10, 10));
        methods.setPrefWidth(300);
        methods.setPrefHeight(120);
        methods.setStyle(".list-cell:filled:selected {-fx-background-color: -fx-background ;-fx-background-insets: 0 ;}");
        for (Method var : entity.getMethods()) {
            methods.getItems().add(new UMLAttribute(var, this, ElementType.METHOD));
        }
        methods.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event) {
                if (methods.getSelectionModel().getSelectedItem() != null)
                    App.setSelected((UMLAttribute)methods.getSelectionModel().getSelectedItem());                
            }
        });

        updateContent();
        
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-border-color: grey; -fx-border-insets: 10; -fx-border-width: 2; -fx-border-style: dashed;");
        
        getChildren().addAll(Arrays.asList(name, new Separator(), variables, new Separator(), methods));
        setLayoutY(y);
        setLayoutX(x);

        parentPane.getChildren().add(this);

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
                App.setSelected(UMLEntity.this);
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // If we just selected we do not want to undo this move
                if (undo_moves.getFirst().x == getLayoutX() && undo_moves.getFirst().y == getLayoutY()) {
                    undo_moves.pop();
                } else {
                    undo_stack.addFirst(UndoType.move);
                    App.addClearUndo();
                }
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
    public void updateContent() {
        name.setText(getElement().getName());        
    }

    /**
     * @brief Adds new Variable to the GUI if allowed
     * @param name
     * @return
     */
    public boolean addVariable(String name) {
        UMLObject elem = (UMLObject)getElement();
        if (!elem.addVariable(new Attribute(name, elem)))
            return false;

        UMLAttribute newAttribute = new UMLAttribute(elem.getVariables().get(elem.getVariables().size() - 1), this, ElementType.VARIABLE);

        variables.getItems().add(newAttribute);
            
        undo_stack.addFirst(UndoType.addVariable);
        App.addClearUndo();

        App.setSelected(newAttribute);

        return true;
    }

    /**
     * @brief Adds new Method to the GUI if allowed
     * @param name
     * @return
     */
    public boolean addMethod(String name) {
        UMLObject elem = (UMLObject)getElement();
        if (!elem.addMethod(new Method(name, elem)))
            return false;

        UMLAttribute newAttribute = new UMLAttribute(elem.getMethods().get(elem.getMethods().size() - 1), this, ElementType.METHOD);
        methods.getItems().add(newAttribute);
        
        undo_stack.addFirst(UndoType.addMethod);
        App.addClearUndo();

        App.setSelected(newAttribute);

        return true;
    }

    /**
     * @brief Removes Method by instance
     * @param instance
     */
    public void removeVariable(UMLAttribute instance) {
        UMLObject elem = (UMLObject)getElement();
        
        undo_stack.addFirst(UndoType.removeVariable);
        for (int index = 0; index < variables.getItems().size(); index++) {
            if ((UMLAttribute)variables.getItems().get(index) == instance) {
                elem.removeVariable(index);
                undo_removes.addFirst(new Pair<Integer,UMLAttribute>(index, (UMLAttribute)variables.getItems().get(index)));
                variables.getItems().remove(index);
                break;
            }
        }
        App.setSelected(this);
        App.addClearUndo();
    }

    /**
     * @brief Removes Variable by instance
     * @param instance
     */
    public void removeMethod(UMLAttribute instance) {
        UMLObject elem = (UMLObject)getElement();

        undo_stack.addFirst(UndoType.removeMethod);
        for (int index = 0; index < methods.getItems().size(); index++) {
            if ((UMLAttribute)methods.getItems().get(index) == instance) {
                undo_removes.addFirst(new Pair<Integer,UMLAttribute>(index, (UMLAttribute)methods.getItems().get(index)));
                elem.removeMethod(index);
                methods.getItems().remove(index);
                break;
            }
        }
        App.setSelected(this);
        App.addClearUndo();
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

        if (type != UndoType.move) {
            super.undo();
        }

        if (type == UndoType.move) {
            Delta delta = undo_moves.pop();
            setLayoutX(delta.x);
            setLayoutY(delta.y);
        } else if (type == UndoType.removeMethod) {
            Pair<Integer, UMLAttribute> top = undo_removes.pop();
            methods.getItems().add(top.getKey(), top.getValue());
        } else if (type == UndoType.removeVariable) {
            Pair<Integer, UMLAttribute> top = undo_removes.pop();
            variables.getItems().add(top.getKey(), top.getValue());
        } else if (type == UndoType.addVariable) {
            variables.getItems().remove(variables.getItems().size() - 1);
        } else if (type == UndoType.addMethod) {
            methods.getItems().remove(methods.getItems().size() - 1);
        } else if (type == UndoType.others) {
            updateContent();
        }
    }
}

class Delta {
    public double x, y;
    public Delta() {}
    public Delta(double x, double y) {this.x = x; this.y = y;}
}