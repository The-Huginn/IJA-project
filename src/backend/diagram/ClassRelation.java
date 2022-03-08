package backend.diagram;

import backend.diagram.Relation;
import backend.diagramObject.UMLClass;
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

    public ClassRelation(String name, Diagram parent) {
        
    }

    public ClassRelation(String name, Diagram parent, UMLClass firstClass, int firstInstance, UMLClass secondClass, int secondInstance) {

    }

    @Override
    public boolean checkCorrect() {
        return false;
    }

    public ClassRelEnum getType() {
        return null;
    }

    public boolean setType(ClassRelEnum newType) {
        return false;
    }
}
