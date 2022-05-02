/**
 * @file diagramHandler.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains class for handling Panes with diagrams ready to use and switch between.
 */
package com.ija.GUI;

import java.util.ArrayList;
import java.util.List;

import com.ija.Application.App;
import com.ija.GUI.GraphicInterface.ElementType;
import com.ija.GUI.classDiagram.UMLEntity;
import com.ija.GUI.classDiagram.cUMLDiagram;
import com.ija.GUI.classDiagram.cUMLRelation;
import com.ija.GUI.seqDiagram.sUMLDiagram;
import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagram.ClassRelation;
import com.ija.backend.diagram.Relation;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;
import com.ija.backend.diagramObject.UMLObject;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class diagramHandler {
    Pair<cUMLDiagram, Pane> classDiagram;
    List<Pair<sUMLDiagram, Pane>> seqDiagrams = new ArrayList<>();

    /**
     * @brief Creates Pane with preferred size and listener for selection
     * @return
     */
    private Pane createPane() {
        Pane newPane = new Pane();
        newPane.setPrefSize(1000, 1000);
        newPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getTarget() == newPane)
                    App.setSelected(App.getCurrentDiagram());
            }
        });

        return newPane;
    }

    /**
     * @brief Creates Pane and fills with diagram entities ready for use.
     * @param diagram
     * @return
     */
    private Pane createDiagram(SeqDiagram diagram) {
        Pane umlDiagram = createPane();

        // TODO create Sequence diagram

        return umlDiagram;
    }
    
    public diagramHandler(ClassDiagram diagram, Label name) {
        classDiagram = new Pair<cUMLDiagram,Pane>(new cUMLDiagram(diagram, name), createPane());

        for (UMLClass item : diagram.getClasses()) {
            // TODO get [y,x]
            new UMLEntity(item, classDiagram.getValue(), classDiagram.getKey(), ElementType.CLASS, 5000, 5000);
        }

        for (UMLInterface item : diagram.getInterfaces()) {
            // TODO get [y,x]
            new UMLEntity(item, classDiagram.getValue(), classDiagram.getKey(), ElementType.INTERFACE, 5300, 5300);
        }

        for (Relation relation : diagram.getRelations()) {
            ClassRelation item = (ClassRelation)relation;
            
            cUMLRelation newRelation = new cUMLRelation(item, classDiagram.getValue(), classDiagram.getKey(), 0, 0);
            classDiagram.getKey().addRelation(newRelation);
            
            for (Node node : classDiagram.getValue().getChildren()) {
                if (!(node instanceof UMLEntity))
                    continue;

                UMLEntity entity = (UMLEntity)node;

                if (((UMLObject)entity.getElement()) == item.getFirst().getKey()) {
                    newRelation.drawStart(entity.getLayoutY() + entity.getHeight() / 2, entity.getLayoutX() + entity.getWidth() / 2);
                }
                
                if (((UMLObject)entity.getElement()) == item.getSecond().getKey()) {
                    newRelation.drawEnd(entity.getLayoutY() + entity.getHeight() / 2, entity.getLayoutX() + entity.getWidth() / 2);
                }
            }
        }

        for (SeqDiagram seqDiagram : diagram.getDiagrams()) {
            seqDiagrams.add(new Pair<sUMLDiagram,Pane>(new sUMLDiagram(seqDiagram, classDiagram.getKey(), name), createDiagram(seqDiagram)));
        }
    }

    public Pane getClassPane() {
        return classDiagram.getValue();
    }

    public UMLElement getClassEntity() {
        return classDiagram.getKey();
    }

    public Pane getSeqPane(int index) {
        if (index < 0 || index >= seqDiagrams.size())
            return null;

        return seqDiagrams.get(index).getValue();
    }

    public UMLElement getSeqEntity(int index) {
        if (index < 0 || index >= seqDiagrams.size())
            return null;

        return seqDiagrams.get(index).getKey();
    }
}
