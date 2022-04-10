package Application;
import java.util.ArrayDeque;
import java.util.Deque;

import backend.undoInterface;
import backend.diagram.ClassDiagram;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application {
    private static ClassDiagram currentDiagram= null;
    private static Deque<undoInterface> undoStack = new ArrayDeque<>();
    private static boolean isSaved = true;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../GUI/WorkbenchWindow.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static ClassDiagram getDiagram() {

        return currentDiagram;
    }

    public static void setDiagram(ClassDiagram newDiagram) {
        currentDiagram = newDiagram;
    }

    public static void undo() {
        
        if (undoStack.isEmpty())
            return;

        isSaved = false;
        undoStack.pop().undo();
    }

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