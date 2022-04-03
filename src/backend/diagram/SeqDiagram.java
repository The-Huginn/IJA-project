package backend.diagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import backend.diagramObject.UMLClass;
import javafx.util.Pair;

public class SeqDiagram extends Diagram {
    private final ClassDiagram parent;
    private List<Pair<UMLClass, Integer>> instances = new ArrayList<>();

    /**
     * @param name
     * @param parent
     */
    public SeqDiagram(String name, ClassDiagram parent) {
        super(name);
        this.parent = parent;
    }

    @Override
    public boolean setName(String newName) {

        for (SeqDiagram diagram : this.getParent().getDiagrams())
            if (diagram.getName().equals(newName))
                return false;

        super.setName(newName);

        return true;
    }

    @Override
    public boolean addRelation(Relation relation) {

        if (!(relation instanceof SeqRelation))
            return false;

        for (Relation relation2: this.getRelations())
            if (relation.equals(relation2))
                return false;

        this.relations.add(relation);

        return true;
    }

    @Override
    public boolean checkCorrect() {

        for (Pair<UMLClass, Integer> pair : this.instances)
            if (!this.getParent().getClasses().contains(pair.getKey()))
                return false;

        for (Relation relation : this.getRelations())
            if (!(((SeqRelation)relation).checkCorrect()))
                return false;

        return true;
    }

    /**
     * @note Checks, whether instance exists in parent and whether is it's instanceNumber unique
     * @param instance
     * @param instanceNumber
     * @return
     */
    public boolean addInstance(UMLClass instance, int instanceNumber) {

        if (!this.getParent().getClasses().contains(instance))
            return false;

        for (Pair<UMLClass, Integer> pair: this.instances)
            if (pair.getKey().equals(instance) && pair.getValue().equals(instanceNumber))
                return false;

        this.instances.add(new Pair<UMLClass, Integer> (instance, instanceNumber));

        return true;
    }

    /**
     * @param index
     */
    public void removeInstance(int index) {

        if (index < 0 || index >= this.instances.size())
            return;

        // checks if instance and instanceNumber occur as first or second in the relation
        this.relations.removeIf(relation -> (this.instances.get(index).getKey().equals(relation.getFirst().getKey()) && this.instances.get(index).getValue().equals(relation.getFirst().getValue())) ||
                                                (this.instances.get(index).getKey().equals(relation.getSecond().getKey()) && this.instances.get(index).getValue().equals(relation.getSecond().getValue())));

        this.instances.remove(index);
    }

    /**
     * @return
     */
    public List<Pair<UMLClass, Integer>> getInstances() {
        return Collections.unmodifiableList(this.instances);
    }

    /**
     * @return
     */
    public final ClassDiagram getParent() {
        return this.parent;
    }
}
