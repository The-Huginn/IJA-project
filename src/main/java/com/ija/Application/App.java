/**
 * @file App.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains main function
 */
package com.ija.Application;
import java.util.ArrayDeque;
import java.util.Deque;

import com.ija.backend.undoInterface;
import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagramObject.Element;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static ClassDiagram currentDiagram= null;
    private static Deque<undoInterface> undoStack = new ArrayDeque<>();
    private static boolean isSaved = true;
    private static Element selectedElement = null;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/com/ija/GUI/WorkbenchWindow.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * @return Currently opened diagram
     */
    public static ClassDiagram getDiagram() {

        return currentDiagram;
    }

    /**
     * @param newDiagram
     */
    public static void setDiagram(ClassDiagram newDiagram) {
        currentDiagram = newDiagram;
    }

    /**
     * @return Currently selected Element
     */
    public static Element getElement() {
        return selectedElement;
    }

    /**
     * @param newSelectedElement
     */
    public static void setElement(Element newSelectedElement) {
        selectedElement = newSelectedElement;
    }

    /**
     * @brief Undo last action from diagram
     */
    public static void undo() {

        if (undoStack.isEmpty())
            return;

        isSaved = false;
        undoStack.pop().undo();
    }

    /**
     * @brief Adds new action to undo stack
     * @param item
     */
    public static void addUndo(undoInterface item) {
        isSaved = false;
        undoStack.addFirst(item);
    }

    public static boolean isSaved() {
        return isSaved;
    }

    public static void save() {
        isSaved = true;
    }
}