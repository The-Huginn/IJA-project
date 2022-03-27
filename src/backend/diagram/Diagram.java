package backend.diagram;

import java.util.ArrayList;

import backend.diagramObject.Element;

public abstract class Diagram extends Element{
    private ArrayList<Relation> relations;

    public Diagram(){};
    /**
     * @param name
     */
    public Diagram(String name) {
        super(name);
        this.relations = new ArrayList<>();
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
    public ArrayList<Relation> getRelations() {
        return this.relations;
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
