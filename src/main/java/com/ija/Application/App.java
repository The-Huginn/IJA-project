/**
 * @file App.java
 * @author Rastislav Budinsky (xbudin05)
 * @brief This file contains main function
 */
package com.ija.Application;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;

import com.ija.backend.undoInterface;
import com.ija.backend.diagram.ClassDiagram;
import com.ija.backend.diagram.Diagram;
import com.ija.backend.diagramObject.Element;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static ClassDiagram classDiagram= null;
    private static Diagram currentDiagram = null;
    private static Element selectedElement = null;
    private static Deque<undoInterface> undoStack = new ArrayDeque<>();
    private static boolean isSaved = true;
    private static FXMLLoader loader;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        loader = new FXMLLoader(getClass().getResource("/com/ija/GUI/MainWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * @return Class diagram of current file
     */
    public static ClassDiagram getClassDiagram() {

        return classDiagram;
    }

    /**
     * @param newDiagram
     */
    public static void setClassDiagram(ClassDiagram newDiagram) {
        classDiagram = newDiagram;
        currentDiagram = newDiagram;
        selectedElement = newDiagram;
    }

    /**
     * @return Currently opened diagram
     */
    public static Diagram getCurrentDiagram() {
        return currentDiagram;
    }

    /**
     * @brief Switches to new diagram
     * @param newDiagram
     */
    public static void setCurrentDiagram(Diagram newDiagram) {
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
     * @brief Checks, whether selected Element contains method
     * @param methodName name of the method
     * @return true if contains
     */
    public static boolean containsMethod(String methodName) {
        for (Method method : selectedElement.getClass().getMethods())
            if (method.getName().equals(methodName))
                return true;

        return false;
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
     * @brief Adds new action to undo stack from selected Element
     */
    public static void addUndo() {
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
}