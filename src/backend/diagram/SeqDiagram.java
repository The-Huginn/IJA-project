package backend.diagram;

import java.util.ArrayList;

import backend.diagram.Diagram;
import backend.diagramObject.UMLClass;
import javafx.util.Pair;

public class SeqDiagram extends Diagram {
    private ClassDiagram parent;
    private ArrayList<Pair<UMLClass, Integer>> instances;

    /**
     * @param name
     * @param parent
     */
    public SeqDiagram(String name, ClassDiagram parent) {

    }

    @Override
    public boolean addRelation(Relation relation) {
        return false;
    }

    @Override
    public boolean checkCorrect() {
        return false;
    }

    /**
     * @param instance
     * @param instanceNumber
     * @return
     */
    public boolean addInstance(UMLClass instance, int instanceNumber) {
        return false;
    }

    /**
     * @param index
     */
    public void removeInstance(int index) {

    }

    /**
     * @return
     */
    public ArrayList<Pair<UMLClass, Integer>> getInstances() {
        return null;
    }
}
