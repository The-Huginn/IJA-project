/**
 * @file App.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains main function
 */
package com.ija.Application;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;

import com.ija.GUI.GraphicInterface;
import com.ija.GUI.MainWindowController;
import com.ija.GUI.UMLElement;
import com.ija.GUI.GraphicInterface.ElementType;
import com.ija.GUI.classDiagram.UMLAttribute;
import com.ija.GUI.classDiagram.UMLEntity;
import com.ija.GUI.classDiagram.cUMLDiagram;
import com.ija.GUI.classDiagram.cUMLRelation;
import com.ija.GUI.seqDiagram.UMLInstance;
import com.ija.GUI.seqDiagram.sUMLDiagram;
import com.ija.GUI.seqDiagram.sUMLRelation;
import com.ija.backend.undoInterface;
import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagramObject.Element;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

public class App extends Application {
    private static ClassDiagram classDiagram= null;
    private static UMLElement currentDiagram = null;
    private static GraphicInterface selectedElement = null;
    private static Deque<undoInterface> undoStack = new ArrayDeque<>();
    private static boolean isSaved = true;
    private static FXMLLoader loader;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                if (!isSaved()) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);

                    alert.setContentText("Are you sure you do not want to save your work?");

                    alert.showAndWait().ifPresent(present -> {
                        if (present != ButtonType.OK) {
                            event.consume();
                        }
                    });
                }
            }
        });
    }

    /**
     * @brief This function should be called upon opening/creating new file
     * @param newDiagram
     */
    public static void newClassDiagram(UMLElement newDiagram) {
        classDiagram = (ClassDiagram)newDiagram.getElement();
        currentDiagram = newDiagram;
        selectedElement = newDiagram;
    }

    /**
     * @return Class diagram of current file
     */
    public static ClassDiagram getClassDiagram() {
        return classDiagram;
    }

    /**
     * @return Currently opened diagram
     */
    public static UMLElement getCurrentDiagram() {
        return currentDiagram;
    }

    /**
     * @brief Switches to new diagram and makes it selected
     * @param newCurrent
     */
    public static void setCurrentDiagram(UMLElement newCurrent) {
        currentDiagram = newCurrent;
        setSelected(newCurrent);
    }

    /**
     * @return Currently selected Element
     */
    public static Element getElement() {
        return selectedElement.getElement();
    }

    /**
     * @param newSelectedElement
     */
    public static void setSelected(GraphicInterface newSelectedElement) {
        if (selectedElement != null)
            selectedElement.unselect();

        selectedElement = newSelectedElement;
        selectedElement.select();

        updateEditPane();
    }

    /**
     * @brief to allow for call of addOthersUndo
     * @return
     */
    public static GraphicInterface getSelected() {
        return selectedElement;
    }

    /**
     * @brief Checks, whether selected Element contains method
     * @param methodName name of the method
     * @return true if contains
     */
    public static boolean containsMethod(String methodName) {
        for (Method method : selectedElement.getElement().getClass().getMethods())
            if (method.getName().equals(methodName))
                return true;

        return false;
    }

    /**
     * @brief Undo last action from diagram
     */
    public static void undo() {

        if (undoStack.isEmpty()) {
            isSaved = true;
            return;
        }

        isSaved = false;
        undoStack.pop().undo();
    }

    /**
     * @brief Adds new action to undo stack from selected Element. Calls @see addUndo from @see UMLElement for notify about change.
     */
    public static void addUndo() {
        addClearUndo();
        selectedElement.addUndo();
    }

    /**
     * @brief Adds new action to undo stack from selected Element. Does not call any other function
     */
    public static void addClearUndo() {
        isSaved = false;
        undoStack.addFirst(selectedElement);
    }

    public static boolean isSaved() {
        return isSaved;
    }

    public static void save() {
        isSaved = true;
    }

    public static FXMLLoader getLoader() {
        return loader;
    }

    public static Pane getCurrentPane() {
        return ((MainWindowController)getLoader().getController()).getCurrentPane();
    }

    public static Pair<Double, Double> getTopLeft() {
        return ((MainWindowController)getLoader().getController()).getTopLeft();
    }

    public static void clearUndo() {
        undoStack.clear();
    }

    public static void closeDiagram() {
        classDiagram= null;
        currentDiagram = null;
        selectedElement = null;
        undoStack = new ArrayDeque<>();
        isSaved = true;
    }

    private static void updateEditPane() {
        BorderPane diagramTable = ((MainWindowController)getLoader().getController()).diagramTable;
        BorderPane editTable = ((MainWindowController)getLoader().getController()).editTable;
        try {
            if (getSelected() instanceof cUMLDiagram) {
                Node node = (Node)FXMLLoader.load(App.class.getResource("/ClassDiagramTable.fxml"));
                diagramTable.setCenter(node);
                Node edit = (Node)FXMLLoader.load(App.class.getResource("/EditTable.fxml"));
                editTable.setCenter(edit);
            } else if (getSelected() instanceof UMLEntity) {
                Node node = (Node)FXMLLoader.load(App.class.getResource("/ClassInterfaceTable.fxml"));
                diagramTable.setCenter(node);
                Node edit = (Node)FXMLLoader.load(App.class.getResource("/EditTable.fxml"));
                editTable.setCenter(edit);
            } else if (getSelected() instanceof UMLAttribute && getSelected().getType() == ElementType.VARIABLE) {
                Node node = (Node)FXMLLoader.load(App.class.getResource("/EmptyTable.fxml"));
                diagramTable.setCenter(node);
                Node edit = (Node)FXMLLoader.load(App.class.getResource("/EditAttributeTable.fxml"));
                editTable.setCenter(edit);
            } else if (getSelected() instanceof UMLAttribute && getSelected().getType() == ElementType.METHOD) {
                Node node = (Node)FXMLLoader.load(App.class.getResource("/EmptyTable.fxml"));
                diagramTable.setCenter(node);
                Node edit = (Node)FXMLLoader.load(App.class.getResource("/EditMethodTable.fxml"));
                editTable.setCenter(edit);
            } else if (getSelected() instanceof cUMLRelation) {
                Node node = (Node)FXMLLoader.load(App.class.getResource("/EmptyTable.fxml"));
                diagramTable.setCenter(node);
                Node edit = (Node)FXMLLoader.load(App.class.getResource("/EditRelationTable.fxml"));
                editTable.setCenter(edit);
            } else if (getSelected() instanceof sUMLRelation) {
                Node node = (Node)FXMLLoader.load(App.class.getResource("/EmptyTable.fxml"));
                diagramTable.setCenter(node);
                Node edit = (Node)FXMLLoader.load(App.class.getResource("/sEditRelationTable.fxml"));
                editTable.setCenter(edit);
            } else if (getSelected() instanceof UMLInstance) {
                Node node = (Node)FXMLLoader.load(App.class.getResource("/InstanceTable.fxml"));
                diagramTable.setCenter(node);
                Node edit = (Node)FXMLLoader.load(App.class.getResource("/EmptyTable.fxml"));
                editTable.setCenter(edit);
            } else if (getSelected() instanceof sUMLDiagram) {
                Node node = (Node)FXMLLoader.load(App.class.getResource("/SeqDiagramTable.fxml"));
                diagramTable.setCenter(node);
                Node edit = (Node)FXMLLoader.load(App.class.getResource("/sEditTable.fxml"));
                editTable.setCenter(edit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}