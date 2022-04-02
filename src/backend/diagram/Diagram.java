package backend.diagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import backend.diagramObject.Element;

public abstract class Diagram extends Element{
    protected List<Relation> relations = new ArrayList<>();

    public Diagram(){};
    /**
     * @param name
     */
    public Diagram(String name) {
        super(name);
    }

    /**
     * @param relation
     * @return Success of operation
     */
    abstract boolean addRelation(Relation relation);

    /**
     * @return If everything is ok
     */
    abstract boolean checkCorrect();

    /**
     * @return
     */
    public List<Relation> getRelations() {
        return Collections.unmodifiableList(this.relations);
    }

    /**
     * @param index
     */
    public void removeRelation(int index) {
        
        if (index < 0 || this.getRelations().size() <= index)
            return;

        this.relations.remove(index);
    }
}
