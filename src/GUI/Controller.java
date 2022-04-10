package GUI;

import java.io.File;

import Application.App;
import backend.diagram.ClassDiagram;
import backend.jsonHandler.Saver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public class Controller {
    @FXML MenuBar myMenuBar;
    @FXML AnchorPane view;
    @FXML AnchorPane diagramTable;
    @FXML AnchorPane editTable;

    private String currentPath = "";

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
        }
    }

    @FXML
    protected void close(ActionEvent event) {

    }

    @FXML
    protected void save(ActionEvent event) {
        if (currentPath.equals("")) {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setContentText("Please use option File -> Save as...");

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
