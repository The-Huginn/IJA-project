package backend.diagram;

import java.util.ArrayList;

import backend.diagramObject.UMLClass;
import javafx.util.Pair;

public class SeqDiagram extends Diagram {
    private final ClassDiagram parent;
    private ArrayList<Pair<UMLClass, Integer>> instances;

    /**
     * @param name
     * @param parent
     */
    public SeqDiagram(String name, ClassDiagram parent) {
        super();this.parent=null;
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
     * @note Checks, whether instance exists in parent and whether is it's instanceNumber unique
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

    /**
     * @return
     */
    public final ClassDiagram getParent() {
        return null;
    }
}
