/**
 * @file Controller.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains controller for the main app
 */
package GUI;

import java.io.File;
import java.io.IOException;

import Application.App;
import backend.diagram.ClassDiagram;
import backend.jsonHandler.Saver;
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
 
public class Controller {
    @FXML MenuBar myMenuBar;
    @FXML BorderPane view;
    @FXML BorderPane diagramTable;
    @FXML BorderPane editTable;
    @FXML Label diagramName;

    private String currentPath = "";

    @FXML
    protected void test() throws IOException {
        Node node = (Node)FXMLLoader.load(getClass().getResource("ClassDiagramTable.fxml"));
        diagramTable.setCenter(node);
    }

    @FXML
    protected void newDiagram(ActionEvent event) {
        if (App.getDiagram() == null || App.isSaved()) {
            
            TextInputDialog td = new TextInputDialog("Enter name of the diagram");
            td.setHeaderText("Enter name");

            td.showAndWait();

            App.setDiagram(new ClassDiagram(td.getEditor().getText()));

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
            currentPath = selectedFile.toString();
            App.setDiagram(diagram);
        }
    }

    @FXML
    protected void close(ActionEvent event) {
        if (App.getDiagram() == null)
            return;

        // TODO probably clean "canvas"
        if (App.isSaved()) {
            App.setDiagram(null);
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setContentText("Are you sure you do not want to save your work?");

            alert.showAndWait().ifPresent(present -> {
                if (present == ButtonType.OK)
                    App.setDiagram(null);
            });
        }
    }

    @FXML
    protected void save(ActionEvent event) {
        if (currentPath.equals("")) {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setContentText("Please use option File -> Save As...");

            alert.show();
        } else if (App.getDiagram() == null) {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setContentText("Missing diagram to save.");

            alert.show();
        } else {
            Saver.save(App.getDiagram(), currentPath);
        }
    }

    @FXML
    protected void saveFile(ActionEvent event) {

        if (App.getDiagram() == null) {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setContentText("Missing diagram to save.");

            alert.show();
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));

            File selectedFile = fileChooser.showSaveDialog((Stage)myMenuBar.getScene().getWindow());

            if (selectedFile == null)
                return;

            Saver.save(App.getDiagram(), selectedFile.toString());
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
