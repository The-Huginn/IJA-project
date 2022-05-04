/**
 * @file diagramHandler.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains class for handling Panes with diagrams ready to use and switch between.
 */
package com.ija.GUI;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.ija.Application.App;
import com.ija.GUI.GraphicInterface.ElementType;
import com.ija.GUI.classDiagram.UMLEntity;
import com.ija.GUI.classDiagram.cUMLDiagram;
import com.ija.GUI.classDiagram.cUMLRelation;
import com.ija.GUI.seqDiagram.sUMLDiagram;
import com.ija.GUI.seqDiagram.sUMLRelation;
import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagram.ClassRelation;
import com.ija.backend.diagram.Relation;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagram.SeqRelation;
import com.ija.backend.diagramObject.UMLClass;
import com.ija.backend.diagramObject.UMLInterface;
import com.ija.backend.diagramObject.UMLObject;

import org.json.JSONArray;
import org.json.JSONObject;

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
        newPane.setPrefSize(10000, 10000);
        newPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getTarget() == newPane)
                    App.setSelected(App.getCurrentDiagram());
            }
        });

        return newPane;
    }

    public diagramHandler(ClassDiagram diagram, Label name) {
        classDiagram = new Pair<cUMLDiagram,Pane>(new cUMLDiagram(diagram, name), createPane());
    }
    
    public diagramHandler(ClassDiagram diagram, Label name, String path) {
        classDiagram = new Pair<cUMLDiagram,Pane>(new cUMLDiagram(diagram, name), createPane());

        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(content);

        for (int i = 0; i < diagram.getClasses().size(); i++) {
            UMLClass item = diagram.getClasses().get(i);

            JSONArray classes = json.getJSONArray("classes");
            if (classes.getJSONObject(i).has("coordinates")) {
                JSONObject coord = classes.getJSONObject(i).getJSONObject("coordinates");
                new UMLEntity(item, classDiagram.getValue(), classDiagram.getKey(), ElementType.CLASS, coord.getInt("y"), coord.getInt("x"));
            } else {
                new UMLEntity(item, classDiagram.getValue(), classDiagram.getKey(), ElementType.CLASS, 0, 0);
            }
        }

        for (int i = 0; i < diagram.getInterfaces().size(); i++) {
            UMLInterface item = diagram.getInterfaces().get(i);

            JSONArray interfaces = json.getJSONArray("interfaces");
            if (interfaces.getJSONObject(i).has("coordinates")) {
                JSONObject coord = interfaces.getJSONObject(i).getJSONObject("coordinates");
                new UMLEntity(item, classDiagram.getValue(), classDiagram.getKey(), ElementType.INTERFACE, coord.getInt("y"), coord.getInt("x"));
            } else {
                new UMLEntity(item, classDiagram.getValue(), classDiagram.getKey(), ElementType.INTERFACE, 0, 0);
            }
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

        /**
         * @note updates all relations to randomized positions
         */
        for (Node node : classDiagram.getKey().getChildren()) {
            if (!(node instanceof UMLEntity))
            continue;
            
            UMLEntity entity = (UMLEntity)node;
            entity.updateRelations();
        }

        for (int i = 0; i < diagram.getDiagrams().size(); i++) {
            SeqDiagram seqDiagram = diagram.getDiagrams().get(i);
            Pane newPane = createPane();
            Pair<sUMLDiagram, Pane> sPane = new Pair<sUMLDiagram,Pane>(new sUMLDiagram(seqDiagram, classDiagram.getKey(), name, newPane), newPane);
            seqDiagrams.add(sPane);
            for (Pair<UMLClass, Integer> pair : seqDiagram.getInstances()) {
                sPane.getKey().addInstance(pair.getKey(), pair.getValue());
            }

            for (int j = 0; j < seqDiagram.getRelations().size(); j++) {
                SeqRelation item = (SeqRelation)seqDiagram.getRelations().get(j);
    
                JSONObject seqJSON = json.getJSONArray("seqDiagrams").getJSONObject(i);
                sUMLRelation newRelation = null;

                if (seqJSON.has("coordinates")) {
                    if (seqJSON.getJSONArray("coordinates").length() > j) {
                        newRelation = new sUMLRelation(item, sPane.getValue(), sPane.getKey(), seqJSON.getJSONArray("coordinates").getInt(j));
                    } else {
                        newRelation = new sUMLRelation(item, sPane.getValue(), sPane.getKey(), 100);
                    }
                } else {
                    newRelation = new sUMLRelation(item, sPane.getValue(), sPane.getKey(), 100);
                }

                sPane.getKey().addRelation(newRelation);
            }
        }
    }

    public void addSequence(SeqDiagram diagram, Label name) {
        Pane newPane = createPane();
        seqDiagrams.add(new Pair<sUMLDiagram,Pane>(new sUMLDiagram(diagram, classDiagram.getKey(), name, newPane), newPane));
    }

    public void removeSeqDiagram(SeqDiagram diagram) {
        seqDiagrams.removeIf(pair -> pair.getKey().getElement().equals(diagram));
    }

    public Pane getClassPane() {
        return classDiagram.getValue();
    }

    public UMLElement getClassEntity() {
        return classDiagram.getKey();
    }

    /**
     * @brief Finds Seq diagram with name
     * @param name
     * @return
     */
    public Pane getSeqPane(String name) {
        
        for (Pair<sUMLDiagram, Pane> diagram : seqDiagrams) {
            if (diagram.getKey().getElement().getName().equals(name))
                return diagram.getValue();
        }

        return null;
    }

    public sUMLDiagram getSeqEntity(String name) {
        for (Pair<sUMLDiagram, Pane> diagram : seqDiagrams) {
            if (diagram.getKey().getElement().getName().equals(name))
                return diagram.getKey();
        }

        return null;
    }

    public boolean save(String path) {
        JSONObject json = App.getClassDiagram().getJSON();

        try {
            /**
             * We rely that all things are saved in the order they are saved in arrays, big no no I know...
             */

             // relations from Sequence Diagram
            for (int i = 0; i < App.getClassDiagram().getDiagrams().size(); i++) {
                JSONArray relJSON = new JSONArray();

                List<Relation> relations = App.getClassDiagram().getDiagrams().get(i).getRelations();
                for (int j = 0; j < relations.size(); j++) {
                    for (sUMLRelation rel : getSeqEntity(App.getClassDiagram().getDiagrams().get(i).getName()).getRelations()) {
                        if (rel.getElement() == relations.get(j)) {
                            relJSON.put((int)rel.getY());
                        }
                    }
                }

                json.getJSONArray("seqDiagrams")
                    .getJSONObject(i)
                    .put("coordinates", relJSON);

            }

            // classes from Class Diagram
            for (int i = 0; i < App.getClassDiagram().getClasses().size(); i++) {
                JSONObject classJSON = new JSONObject();

                for (Node node : getClassPane().getChildren()) {
                    if (!(node instanceof UMLEntity))
                        continue;

                    UMLEntity entity = (UMLEntity)node;

                    if (entity.getElement() == App.getClassDiagram().getClasses().get(i)) {
                        classJSON.put("y", (int) entity.getLayoutY());
                        classJSON.put("x", (int) entity.getLayoutX());
                    }
                }

                json.getJSONArray("classes")
                        .getJSONObject(i)
                        .put("coordinates", classJSON);

            }

            // interfaces from Class Diagram
            for (int i = 0; i < App.getClassDiagram().getInterfaces().size(); i++) {
                JSONObject interJSON = new JSONObject();

                for (Node node : getClassPane().getChildren()) {
                    if (!(node instanceof UMLEntity))
                        continue;

                    UMLEntity entity = (UMLEntity)node;

                    if (entity.getElement() == App.getClassDiagram().getInterfaces().get(i)) {
                        interJSON.put("y", (int) entity.getLayoutY());
                        interJSON.put("x", (int) entity.getLayoutX());
                    }
                }

                json.getJSONArray("interfaces")
                        .getJSONObject(i)
                        .put("coordinates", interJSON);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        String correct_path = (Paths.get(path).isAbsolute() ? path : "./" + path);
        
        try {
            File file = new File(correct_path);
            file.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        
        try (FileWriter file = new FileWriter(correct_path)) {
            file.write(json.toString(4));
            App.save();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
