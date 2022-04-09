package backend.diagram;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import backend.diagramObject.Element;
import javafx.util.Pair;

public abstract class Diagram extends Element{
    protected List<Relation> relations = new ArrayList<>();

    // variables for undo operations
    private Deque<UndoType> undo_stack = new ArrayDeque<>();
    private Deque<Pair<Integer, Relation>> undo_relation = new ArrayDeque<>();

    private enum UndoType {
        removeRelation,
        others
    }

    public Diagram(){};
    /**
     * @param name
     */
    public Diagram(String name) {
        super(name);
    }

    @Override
    public boolean setName(String newName) {
        undo_stack.addFirst(UndoType.others);
        return super.setName(newName);
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

        undo_stack.addFirst(UndoType.removeRelation);
        undo_relation.addFirst(new Pair<Integer,Relation>(index, relations.get(index)));

        this.relations.remove(index);
    }

    @Override
    public void undo() {

        if (undo_stack.isEmpty())
            return;

        UndoType type = undo_stack.pop();

        if (type == UndoType.removeRelation) {
            Pair<Integer, Relation> top = undo_relation.pop();

            relations.add(top.getKey(), top.getValue());
        } else if (type == UndoType.others) {
            super.undo();
        }
    }

    @Override
    public JSONObject getJSON() {
        JSONObject json = super.getJSON();

        JSONArray array = new JSONArray();

        for (Relation relation : getRelations()) {
            array.put(relation.getJSON());
        }

        json.put("relations", array);

        return json;
    }

    // we cant set up relations here
    @Override
    public boolean setJSON(JSONObject json) {
        return super.setJSON(json);
    }
}
