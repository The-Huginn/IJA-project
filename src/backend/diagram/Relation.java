package backend.diagram;

import backend.diagramObject.Element;
import backend.diagramObject.UMLObject;
import backend.diagram.Diagram;
import javafx.util.Pair;

public abstract class Relation extends Element{
    private Pair<UMLClass, Integer> first;
    private Pair<UMLClass, Integer> second;
    protected final Diagram parent;

    /**
     * @param name
     * @param parent Under which parent this Relation lives
     */
    public Relation(String name, Diagram parent) {

    }

    /**
     * @param name
     * @param parent Under which parent this Relation lives
     * @param firstInstance Relation starts from this UMLObject
     * @param firstInstanceNumber instance number of the first UMLObject instance
     * @param secondInstance Relation ends in this UMLObject
     * @param secondInstanceNumber instance number of the second UMLObject instance
     */
    public Relation(String name, Diagram parent, UMLObject firstInstance, int firstInstanceNumber, UMLObject secondInstance, int secondInstanceNumber) {

    }

    /**
     * @return Checks, whether everything is ok
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
