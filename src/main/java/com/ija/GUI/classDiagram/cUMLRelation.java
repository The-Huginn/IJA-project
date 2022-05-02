package com.ija.GUI.classDiagram;

import com.ija.GUI.UMLElement;
import com.ija.backend.diagram.ClassRelation;

import javafx.scene.shape.Line;

public class cUMLRelation extends UMLElement {
    private Line line;
    private String[] colors = {" blue;", " cyan;", " orange;", " black;", " pink;"};

    public cUMLRelation(ClassRelation element, cUMLDiagram parent, int y, int x) {
        super(element, parent, ElementType.CLASS_RELATION);

        line = new Line();
        line.setStartX(x);
        line.setStartY(y);
        updateContent();
    }

    public void drawEnd(int y, int x) {
        line.setEndY(y);
        line.setEndX(x);
    }

    public void drawStart(int y, int x) {
        line.setStartY(y);
        line.setStartX(x);
    }

    @Override
    public void select() {
        line.setStyle("-fx-stroke: yellow");
    }

    @Override
    public void unselect() {
        updateContent();
    }

    @Override
    public void updateContent() {
        // TODO
        line.setStyle("-fx-stroke:" + colors[((ClassRelation)getElement()).getType().ordinal()]);
    }

    @Override
    public void addUndo() {}

    @Override
    public void undo() {
        super.undo();
        updateContent();
    }
    
    public void deleteMe() {
        
    }
}
