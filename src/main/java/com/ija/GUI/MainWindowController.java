/**
 * @file Controller.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for the main app
 */
package com.ija.GUI;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.TreeSet;

import com.ija.Application.App;
import com.ija.GUI.classDiagram.cUMLDiagram;
import com.ija.GUI.seqDiagram.sUMLDiagram;
import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagram.SeqDiagram;
import com.ija.backend.diagramObject.Type;
import com.ija.backend.jsonHandler.Saver;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainWindowController implements Initializable {
    @FXML MenuBar myMenuBar;
    @FXML BorderPane view;
    @FXML public BorderPane diagramTable;
    @FXML public BorderPane editTable;
    @FXML public Label diagramName;
    @FXML public BorderPane pane;
    @FXML ScrollPane scrollPane;
    private diagramHandler handler;

    private String currentPath = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scrollPane.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY)
                scrollPane.setPannable(true);
          });
        scrollPane.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.SECONDARY)
                scrollPane.setPannable(false);
          });
    }

    public Pane getCurrentPane() {
        return (Pane)pane.getCenter();
    }

    public Pair<Double, Double> getTopLeft() {
        double y = scrollPane.getVvalue() * getCurrentPane().getHeight() - scrollPane.getHeight() / 2;
        double x = scrollPane.getHvalue() * getCurrentPane().getWidth() - scrollPane.getWidth() / 2;
        return new Pair<Double,Double>(y, x);
    }

    private void setPane(UMLElement entity, Pane newPane) {
        App.clearUndo();
        App.setCurrentDiagram(entity);
        pane.setCenter(newPane);
        entity.updateContent();

        if (entity instanceof cUMLDiagram) {
            scrollPane.setHvalue(0.5);
            scrollPane.setVvalue(0.5);
        } else {
            scrollPane.setHvalue(0.0);
            scrollPane.setVvalue(0.0);
        }
    }

    /**
     * @param diagram
     * @param path    if set to null then we do not load from the memory and create
     *                epmty Class Diagram
     */
    private void loadDiagram(ClassDiagram diagram, String path) {
        if (path == null) {
            // Default Types
            TreeSet<String> set = new TreeSet<String>();
            set.addAll(Arrays.asList("int", "boolean", "float", "string", "long", "byte", "short", "char", "void"));
            Type.initTypes(set.toArray(new String[set.size()]), diagram);
            handler = new diagramHandler(diagram, diagramName);
        } else {
            handler = new diagramHandler(diagram, diagramName, path);
        }

        App.newClassDiagram(handler.getClassEntity());
        setPane(handler.getClassEntity(), handler.getClassPane());
    }

    private void closeDiagram() {
        App.closeDiagram();
        diagramName.setText(null);
        diagramTable.setCenter(null);
        editTable.setCenter(null);
        pane.setCenter(null);
        handler = null;
    }

    public void addSequence(String name) {
        SeqDiagram diagram = App.getClassDiagram().getDiagrams().stream()
                                                                .filter(f -> f.getName().equals(name))
                                                                .findAny()
                                                                .orElse(null);
        handler.addSequence(diagram, diagramName);
        switchSeqDiagram(name);
    }

    public void removeClassDiagram() {
        handler = null;
        closeDiagram();
    }

    public void removeSeqDiagram(SeqDiagram diagram) {
        handler.removeSeqDiagram(diagram);
        setPane(handler.getClassEntity(), handler.getClassPane());
    }

    @FXML
    protected void newDiagram(ActionEvent event) {
        if (App.getClassDiagram() == null || App.isSaved()) {

            TextInputDialog td = new TextInputDialog("Enter name of the diagram");
            td.setHeaderText("Enter name");

            td.showAndWait();

            loadDiagram(new ClassDiagram(td.getEditor().getText()), null);

        } else {
            Alert alert = new Alert(AlertType.WARNING);

            alert.setContentText("Please save your work.");

            alert.show();
        }
    }

    @FXML
    protected void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));

        Stage stage = (Stage)myMenuBar.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null)
            return;

        ClassDiagram diagram = Saver.load(selectedFile.toString());

        if (diagram == null) {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setContentText("The configuration file is wrong or corrupted.");

            alert.show();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);

            alert.setContentText("The diagram was successfully loaded.");

            alert.show();

            currentPath = selectedFile.toString();

            loadDiagram(diagram, selectedFile.toString());
        }
    }

    @FXML
    protected void close(ActionEvent event) {
        if (App.getClassDiagram() == null)
            return;

        if (App.isSaved()) {
            closeDiagram();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setContentText("Are you sure you do not want to save your work?");

            alert.showAndWait().ifPresent(present -> {
                if (present == ButtonType.OK) {
                    closeDiagram();
                }
            });
        }
    }

    @FXML
    protected void save(ActionEvent event) {
        if (currentPath.equals("")) {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setContentText("Please use option File -> Save As...");

            alert.show();
        } else if (App.getClassDiagram() == null) {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setContentText("Missing diagram to save.");

            alert.show();
        } else {
            if (!handler.save(currentPath)) {
                Alert alert = new Alert(AlertType.ERROR);

                alert.setContentText("Error saving current diagram.");

                alert.show();
            }
        }
    }

    @FXML
    protected void saveFile(ActionEvent event) {

        if (App.getClassDiagram() == null) {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setContentText("Missing diagram to save.");

            alert.show();
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));

            File selectedFile = fileChooser.showSaveDialog((Stage)myMenuBar.getScene().getWindow());

            if (selectedFile == null)
                return;

            if (!handler.save(selectedFile.toString())) {
                Alert alert = new Alert(AlertType.ERROR);

                alert.setContentText("Error saving current diagram.");

                alert.show();

            } else {
                currentPath = selectedFile.toString();
            }
        }
    }

    @FXML
    protected void quit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    protected void undo(ActionEvent event) {
        App.undo();
    }

    @FXML
    protected void openClassDiagram(Event event) {
        if (App.getClassDiagram() == null)
            return;

        setPane(handler.getClassEntity(), handler.getClassPane());
    }

    @FXML
    protected void checkCorrect(Event event) {
        if (!handler.checkCorrect()) {
            Alert alert = new Alert(AlertType.INFORMATION);

            alert.setContentText("The diagram has inconsistencies. All are marked with red color.");

            alert.show();
        }
    }

    @FXML
    protected void updateAll(Event event) {
        if (App.getCurrentDiagram() instanceof cUMLDiagram) {
            ((cUMLDiagram)App.getCurrentDiagram()).updateAll();
        } else if (App.getCurrentDiagram() instanceof sUMLDiagram) {
            ((sUMLDiagram)App.getCurrentDiagram()).updateAll();
        }
    }

    public void switchSeqDiagram(String name) {
        setPane(handler.getSeqEntity(name), handler.getSeqPane(name));
    }
}
