package backend.diagram;

import backend.diagram.Relation;

public class ClassRelation extends Relation{
    private ClassRelEnum type;

    public enum ClassRelEnum {
        ASSOCIATION,
        AGGREGATION,
        COMPOSITION,
        GENERALIZATION,
        IMPLEMENTS
    }

    public ClassRelation(String name) {
        
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
