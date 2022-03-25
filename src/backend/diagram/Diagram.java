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
        super();
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
        return null;
    }

    /**
     * @param index
     */
    public void removeRelation(int index) {

    }
}
