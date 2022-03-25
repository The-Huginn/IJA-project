package backend.diagram;

import backend.diagram.Relation;
import backend.diagramObject.UMLObject;
import javafx.util.Pair;

public class ClassRelation extends Relation{
    private ClassRelEnum type;

    public enum ClassRelEnum {
        ASSOCIATION,
        AGGREGATION,
        COMPOSITION,
        GENERALIZATION,
        IMPLEMENTS
    }

    /**
     * @param name
     * @param parent Under which parent this ClassRelation lives
     */
    public ClassRelation(String name, Diagram parent) {
        super();
    }

    /**
     * @param name
     * @param parent Under which parent this ClassRelation lives
     * @param firstInstance ClassRelation starts from this UMLObject
     * @param firstInstanceNumber instance number of the first UMLObject instance
     * @param secondInstance ClassRelation ends in this UMLObject
     * @param secondInstanceNumber instance number of the second UMLObject instance
     */
    public ClassRelation(String name, Diagram parent, UMLObject firstClass, int firstInstance, UMLObject secondClass, int secondInstance) {
        super();
    }

    @Override
    public boolean checkCorrect() {
        return false;
    }

    /**
     * @note Checks, whether instance exists in parent and if it's correct combination
     */
    @Override
    public boolean setFirst(UMLObject instance, Integer instanceNumber) {
        return false;
    }

    /**
     * @note Checks, whether instance exists in parent and if it's correct combination
     */
    @Override
    public boolean setSecond(UMLObject instance, Integer instanceNumber) {
        return false;
    }

    /**
     * @return
     */
    public ClassRelEnum getType() {
        return null;
    }

    /**
     * @param newType
     * @return
     */
    public boolean setType(ClassRelEnum newType) {
        return false;
    }
}
