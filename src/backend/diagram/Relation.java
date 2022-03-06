package backend.diagram;

import backend.diagramObject.Element;
import backend.diagramObject.UMLClass;
import backend.diagram.Diagram;
import javafx.util.Pair;

public abstract class Relation extends Element{
    private Pair<UMLClass, Integer> first;
    private Pair<UMLClass, Integer> second;
    protected final Diagram parent;

    /**
     * @param name
     * @param parent
     */
    public Relation(String name, Diagram parent) {

    }

    /**
     * @return If everything is ok
     */
    abstract boolean checkCorrect();

    /**
     * @return Start point of relation
     */
    public Pair<UMLClass, Integer> getFirst() {
        return null;
    }

    /**
     * @return End point of relation
     */
    public Pair<UMLClass, Integer> getSecond() {
        return null;
    }

    /**
     * @param instance
     * @param instanceNumber
     * @return Success of operation
     */
    public boolean setFirst(UMLClass instance, Integer instanceNumber) {
        return false;
    }

    /**
     * @param instance
     * @param instanceNumber
     * @return Success of operation
     */
    public boolean setSecond(UMLClass instance, Integer instanceNumber) {
        return false;
    }
}
