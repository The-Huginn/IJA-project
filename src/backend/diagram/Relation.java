package backend.diagram;

import backend.diagramObject.Element;
import backend.diagramObject.UMLClass;
import backend.diagramObject.UMLObject;
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
     * @param name
     * @param parent
     * @param pair
     */
    public Relation(String name, Diagram parent, UMLClass firstClass, int firstInstance, UMLClass secondClass, int secondInstance) {

    }

    /**
     * @return If everything is ok
     */
    abstract boolean checkCorrect();

    /**
     * @return Start point of relation
     */
    public Pair<UMLObject, Integer> getFirst() {
        return null;
    }

    /**
     * @return End point of relation
     */
    public Pair<UMLObject, Integer> getSecond() {
        return null;
    }

    /**
     * @param instance
     * @param instanceNumber
     * @return Success of operation
     */
    abstract boolean setFirst(UMLObject instance, Integer instanceNumber);

    /**
     * @param instance
     * @param instanceNumber
     * @return Success of operation
     */
    abstract boolean setSecond(UMLObject instance, Integer instanceNumber);
}
