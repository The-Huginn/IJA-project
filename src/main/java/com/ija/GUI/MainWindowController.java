/**
 * @file Controller.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for the main app
 */
package com.ija.GUI;

import java.io.File;
import java.io.IOException;

import com.ija.Application.App;
import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.jsonHandler.Saver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindowController {
    @FXML MenuBar myMenuBar;
    @FXML BorderPane view;
    @FXML public BorderPane diagramTable;
    @FXML public BorderPane editTable;
    @FXML public Label diagramName;

    private String currentPath = "";

    private void loadDiagram() {
        try {
            Node node = (Node)FXMLLoader.load(getClass().getResource("/com/ija/GUI/ClassDiagramTable.fxml"));
            diagramTable.setCenter(node);
            Node edit = (Node)FXMLLoader.load(getClass().getResource("/com/ija/GUI/EditTable.fxml"));
            editTable.setCenter(edit);
            diagramName.setText(App.getClassDiagram().getName());
        } catch (IOException e) {
            System.err.println(e);
            System.err.println("Unable to open resources...");
        }
    }

    private void closeDiagram() {
        diagramName.setText(null);
        diagramTable.setCenter(null);
        editTable.setCenter(null);
    }

    @FXML
    protected void newDiagram(ActionEvent event) {
        if (App.getClassDiagram() == null || App.isSaved()) {

            TextInputDialog td = new TextInputDialog("Enter name of the diagram");
            td.setHeaderText("Enter name");

            td.showAndWait();

            App.setClassDiagram(new ClassDiagram(td.getEditor().getText()));

            loadDiagram();

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
            App.setClassDiagram(diagram);

            loadDiagram();
        }
    }

    @FXML
    protected void close(ActionEvent event) {
        if (App.getClassDiagram() == null)
            return;

        // TODO probably clean "canvas"
        if (App.isSaved()) {
            App.setClassDiagram(null);
            diagramTable.setCenter(null);
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setContentText("Are you sure you do not want to save your work?");

            alert.showAndWait().ifPresent(present -> {
                if (present == ButtonType.OK) {
                    App.setClassDiagram(null);
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
            Saver.save(App.getClassDiagram(), currentPath);
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

            Saver.save(App.getClassDiagram(), selectedFile.toString());
            currentPath = selectedFile.toString();
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
}
